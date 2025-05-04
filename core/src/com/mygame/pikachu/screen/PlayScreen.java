package com.mygame.pikachu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.data.LevelManager;
import com.mygame.pikachu.model.Level;
import com.mygame.pikachu.screen.widget.BorderPM;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.actions.RandomPathAction;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.LB;
import com.mygame.pikachu.utils.hud.builders.MGB;
import com.mygame.pikachu.utils.hud.external.EventHandler;
import com.mygame.pikachu.view.Board;
import com.mygame.pikachu.view.ui.PopupUI;

public class PlayScreen implements Screen, EventHandler {
  private final Board board;
  private final MapGroup playMG;
  private final LevelManager levelManager;
  private Level levelData;
  private PopupUI popup;
  private Pixmap pixmap;

  private int level;
  private float timePB;
  private final float centerX;
  private final float centerY;
  private boolean isPause = false;

  public PlayScreen(GMain game) {
    this.level = 1;
    levelManager = new LevelManager();
    board = new Board();
    popup = new PopupUI(game);
    playMG = MGB.New().size(GMain.stage().getWidth(), GMain.stage().getHeight()).pos(0, 0, AL.tr).idx("playMG").parent(GMain.hud()).build();
    centerX = playMG.getWidth() / 2;
    centerY = playMG.getHeight() / 2;
    init();
  }


  private void init() {
    pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.GRAY);
    pixmap.fillCircle(0, 0, 20);
    initConstant();
    createBtn();
    addHandle();
    playMG.addActor(board);
  }

  private void initConstant() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    IB.New().drawable("bg").size(centerX * 2, centerY * 2).pos(0, 0, AL.c).parent(playMG).build();

    MGB.New().size(196, 96).childs(
        IB.New().texture(new Texture(new BorderPM().get(196, 64, 30, 0x696969FF))).origin(AL.cl).pos(0, 0, AL.cl),
        IB.New().drawable("coin").idx("coinOrigin").pos(0, 0, AL.cr).scale(0.9f),
        LB.New().font(GConstants.BMF).text(GMain.player().getCoins() + "").fontScale(1f).pos(25, 0, AL.cl).idx("coinLabel")
    ).origin(AL.ct).pos(0, 20, AL.ct).idx("coinT").parent(playMG).debug(false).build();

    MGB.New().size(196, 96).childs(
        IB.New().texture(new Texture(new BorderPM().get(196, 64, 30, 0x696969FF))).origin(AL.cl).pos(0, 0, AL.cl),
        BB.New().bg("star6").idx("starOrigin").pos(0, 0, AL.cr).scale(0.9f),
        LB.New().font(GConstants.BMF).text(GMain.player().getScore() + "").fontScale(1f).pos(25, 0, AL.cl).idx("scoreLabel")
    ).origin(AL.tr).pos(20, 20, AL.tr).idx("score").parent(playMG).debug(false).build();
  }

  private void initFlexible() {
    levelData = levelManager.getLevel(level);
    board.setNew(levelData);
    board.setPosition(centerX - board.getWidth() / 2, centerY - board.getHeight() / 2);
    board.setOrigin(board.getWidth() / 2, board.getHeight() / 2);
    if (board.getWidth() > centerX * 1.6f) {
      board.setScale(centerX / board.getWidth() * 1.6f);
    }
    if (board.getHeight() > centerY * 1.25f) {
      board.setScale(centerY / board.getHeight() * 1.25f);
    }
  }

  public void completeLevel() {
    popup.showWinUI();
  }

  private void createProgressBar() {
    pixmap.fill();
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    MGB.New().size(561, 41).childs(
        IB.New().drawable("barframe").pos(0, 0, AL.c),
        IB.New().drawable("bar").pos(0, 0, AL.c),
        IB.New().texture(new Texture(pixmap)).size(547, 30).origin(AL.cr).pos(6, 0, AL.cr).scale(0.5f, 1).idx("barOv")
    ).pos(0, centerY * 0.75f, AL.c).idx("progress").parent(playMG).build();
    timePB = 0;
  }

  private boolean updateProgressBar(float delta) {
    timePB += delta;
    Image barOv = playMG.query("progress/barOv", Image.class);
    float scale = barOv.getScaleX();
    if (scale > 1) {
      return true;
    } else {
      barOv.setScale((timePB) / levelData.getTime(), 1);
    }
    return false;
  }

  private void createLabel() {
    LB.New().font(GConstants.BMF).text("Level " + level).pos(150, 50, AL.tl).parent(playMG).build();
  }

  private void createBtn() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    BB.New().bg("btn_pause").transform(true).pos(20, 25, AL.tl).scale(1.3f).origin(AL.tl).idx("btnPause").parent(playMG).build();

    MapGroup hint = MGB.New().size(100, 100).childs(
        IB.New().texture(new Texture(new BorderPM().get(100, 50, 20, 0x696969FF))).pos(0, -40, AL.cb),
        LB.New().font(GConstants.BMF).text(GMain.player().getHints() + "").pos(0, -35, AL.cb).idx("label"),
        BB.New().bg("hint").transform(true).pos(0, 0, AL.c).debug(false).scale(1.5f)
    ).pos(100, 100, AL.bl).idx("btnHint").parent(playMG).debug(false).build();

    MapGroup MGShuffle = MGB.New().size(100, 100).childs(
        IB.New().texture(new Texture(new BorderPM().get(100, 50, 20, 0x696969FF))).pos(0, -40, AL.cb),
        LB.New().font(GConstants.BMF).text(GMain.player().getShuffles() + "").pos(0, -35, AL.cb).idx("label"),
        BB.New().bg("shuffle").transform(true).pos(0, 0, AL.c).scale(1.5f)
    ).pos(0, 100, AL.cb).idx("btnShuffle").parent(playMG).build();

    MapGroup rocket = MGB.New().size(100, 100).childs(
        IB.New().texture(new Texture(new BorderPM().get(100, 50, 20, 0x696969FF))).pos(0, -40, AL.cb),
        LB.New().font(GConstants.BMF).text(GMain.player().getRockets() + "").pos(0, -35, AL.cb).idx("label"),
        BB.New().bg("boom").transform(true).pos(0, 0, AL.c).scale(1.5f)
    ).pos(100, 100, AL.br).idx("btnRocket").parent(playMG).build();

  }

  private void rocketLaunch() {
    for (int i = 0; i < 2; i++) {
      Vector2[] vt = board.getRandomVisibleActor();
      if (vt == null) return;
      IB.New().drawable("rocket").size(40, 120).pos(0, centerY * 0.2f * (i + 1), AL.bl).parent(playMG).build().addAction(
          Actions.sequence(
              new RandomPathAction(MathUtils.random(centerX, centerX * 3f), centerY * 2, 0.5f, 20),
              Actions.moveTo(centerX * 1.5f, centerY * 3),
              new RandomPathAction(vt[0].x, vt[0].y, 0.8f, 20),
              Actions.removeActor()
          )
      );

      IB.New().drawable("rocket").size(40, 120).pos(centerX * 2, centerY * 0.2f * (i + 1), AL.bl).parent(playMG).build().addAction(
          Actions.sequence(
              new RandomPathAction(MathUtils.random(-centerX, centerX), centerY * 2, 0.5f, 20),
              Actions.moveTo(centerX / 2, centerY * 3),
              new RandomPathAction(vt[1].x, vt[1].y, 0.8f, 20),
              Actions.removeActor()
          )
      );
    }
  }

  public void setPause(boolean p) {
    isPause = p;
  }

  public void restart(){
    // TODO caanf hoan thien lai phan nay
    GMain.player().save();
//    board.restart();
    initFlexible();
    playMG.query("progress/barOv", Image.class).setScale(0, 1);
  }
  public void nextLevel(){
    GMain.player().save();
    level++;
    initFlexible();
    playMG.query("progress/barOv", Image.class).setScale(0, 1);
  }

  @Override
  public void show() {
    System.out.println("[PlayScreen]: Show");
    GMain.hud().addActor(playMG);
    createLabel();
    createProgressBar();
    initFlexible();
    board.debugAll();
    isPause = false;
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    if (!isPause) {
      if (updateProgressBar(delta)) {
        popup.showFailUI();
      }
    }

    playMG.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    board.updateLineSelect();
  }

  @Override
  public void resize(int width, int height) {
//    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
    GMain.player().save();
    isPause = true;
  }

  @Override
  public void resume() {
    isPause = false;
  }

  @Override
  public void hide() {
    playMG.remove();
  }

  @Override
  public void dispose() {
    clearPopup();
    playMG.clear();
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  private void clearPopup() {
    if (popup != null) {
      popup = null;
    }
  }

  private void addHandle() {
    GMain.hud().index("mgPlay", playMG);
    GMain.hud().regisHandler("playHandler", this);
    GMain.hud().clickConnect("mgPlay/score/starOrigin", "playHandler", "StarOriginAction");
    GMain.hud().clickConnect("mgPlay/btnPause", "playHandler", "PauseAction");
    GMain.hud().clickConnect("mgPlay/btnHint", "playHandler", "HintAction");
    GMain.hud().clickConnect("mgPlay/btnShuffle", "playHandler", "ShuffleAction");
    GMain.hud().clickConnect("mgPlay/btnRocket", "playHandler", "RocketAction");

  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {
    switch (action) {
      case "StarOriginAction":
        // TODO show rank
        break;
      case "PauseAction":
        System.out.println("[PlayScreen]: click pause");
        popup.showPauseUI();
        isPause = true;
        break;
      case "HintAction":
        if (GMain.player().useHint()) {
          board.showAnimationHint();
          playMG.query("btnHint/label", Label.class).setText(GMain.player().getHints());
        }
        break;
      case "ShuffleAction":
        if (GMain.player().useShuffle()) {
          board.shuffle();
          playMG.query("btnShuffle/label", Label.class).setText(GMain.player().getShuffles());
          Gdx.app.log("ButtonFactory", "Board shuffled");
        }
        break;
      case "RocketAction":
        if (GMain.player().useRocket()) {
          System.out.println("rocket use");
          playMG.query("btnRocket/label", Label.class).setText(GMain.player().getRockets());
          rocketLaunch();
          if (board.isComplete()) {
            completeLevel();
          }
        }
        break;

    }
  }
}