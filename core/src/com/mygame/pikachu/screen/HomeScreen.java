package com.mygame.pikachu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.exSprite.particle.GParticleSprite;
import com.mygame.pikachu.model.Player;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.Button;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.MGB;
import com.mygame.pikachu.utils.hud.external.EventHandler;
import com.mygame.pikachu.view.ui.PopupUI;


public class HomeScreen implements Screen, EventHandler {
  private GMain game;
  private Player player;

  private MapGroup homeMG;
  private PopupUI popup;
  private GParticleSprite particleSprite;
  private ParticleEffect effect;

  private float centerX, centerY;
  int levelCompleted;

  public HomeScreen(GMain game) {
    this.game = game;
    this.player = game.getPlayer();
    levelCompleted = 4;
    centerX = GMain.stage().getWidth() / 2;
    centerY = GMain.stage().getHeight() / 2;
    homeMG = new MapGroup(centerX * 2, centerY * 2);
    effect = new ParticleEffect();
    effect.load(Gdx.files.internal("univer.p"), Gdx.files.internal(""));
    effect.setPosition(400, 300);
    effect.start();
    particleSprite = new GParticleSprite(effect);
//    homeMG.addActor(particleSprite);
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    Image bg = IB.New().drawable("bg").pos(0,0,AL.c).scale(0.84f).parent(homeMG).build();
    player.setLevel(levelCompleted);
    popup = new PopupUI(game);

    createAssetHome();
    GMain.hud().addActor(homeMG);
    addHandler();
  }

  private void createAssetHome() {
    createTitle();
    createBtnPlay();
    createListLevel();
    createMoreGame();
  }

  private void createMoreGame() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    Button other = BB.New().bg("btnOther").pos(0, 0, AL.br).idx("btnOther").parent(homeMG).build();
  }

  private void createTitle() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_MENU);
    IB.New().drawable("logo_vi").pos(0, 100, AL.ct).parent(homeMG).build().addAction(
        Actions.forever(Actions.sequence(
            Actions.scaleTo(1.1f,1f,2),
            Actions.scaleTo(1,1,2)
        )));


    createLight(0, 0, -30);
    createLight(1, -150, -30);
    createLight(2, 100, -30);
  }

  private void createLight(int id, int x, int y) {
    String path = "light";
    MapGroup line = MGB.New().size(121,179).childs(
        IB.New().drawable(path +"off" + id).align(AL.ct),
        IB.New().drawable(path +"on" + id).idx("on").align(AL.ct)
    ).pos(x, y, AL.ct).origin(AL.ct).parent(homeMG).build();
    line.query("on", Image.class).addAction(
        Actions.forever(Actions.sequence(Actions.fadeOut(1), Actions.fadeIn(1)))
    );

    line.addAction(Actions.forever(Actions.sequence(
        Actions.rotateBy(15, 1),
        Actions.rotateBy(-30, 2),
        Actions.rotateBy(15, 1)
    )));
  }

  private void createListLevel() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    Button tag = BB.New().bg("tag").label("Menu", GConstants.BMF, 0, 10, AL.c).pos(20, 10, AL.bl).idx("menu").parent(homeMG).build();
  }

  private void createBtnRank() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    Button btnRank = BB.New().transform(true).bg("btn_leaderboard").scale(1).pos(centerX * 2 - 100, centerY * 2 - 200).build();
    btnRank.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        System.out.println("click btn rank");
      }
    });
    homeMG.addActor(btnRank);
  }

  private void createBtnSetting() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    Button setting = BB.New().transform(true).bg("btn_pause").scale(2).pos(centerX * 2 - 90, centerY * 2 - 90).parent(homeMG).build();
    setting.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {

      }
    });
  }

  private void createBtnPlay() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    Button play = BB.New().bg("btn_yellow").label("Bắt đầu", GConstants.BMF, 0, 0, AL.c).origin(AL.c).pos(0, 0, AL.c).idx("btnPlay").parent(homeMG).build();
  }

  private void clearScrollPane() {
    for (Actor a : homeMG.getStage().getActors()) {
      if (a instanceof ScrollPane) {
        a.remove();
      }
    }
  }

  private void addHandler() {
    //TODO Bước 1 dành cho Handler
    GMain.hud().index("mgHome", homeMG);
    GMain.hud().regisHandler("homeHandler", this);
    GMain.hud().clickConnect("mgHome/btnOther", "homeHandler", "showOtherGame", 3, player);
    GMain.hud().clickConnect("mgHome/btnPlay", "homeHandler", "playGame");
    GMain.hud().clickConnect("mgHome/menu", "homeHandler", "showMenu");
  }

  @Override
  public void show() {
    GMain.hud().addActor(homeMG);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.5f, 0.4f, 1);
    GMain.stage().draw();
    GMain.stage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
    homeMG.remove();
  }

  @Override
  public void dispose() {
    homeMG.clear();
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {
    switch (action) {
      case "showOtherGame":
        System.out.println("show other game - " + intParam + " - " + ((Player) objParam).getLevel());
        break;
      case "playGame":
        System.out.println("click play game");
        game.getPlayScreen().setLevel(player.getLevel());
        game.setScreen(game.getPlayScreen());
        break;
      case "showMenu":
        System.out.println("click menu");
        popup.showMenuUI();
        break;
      default:

    }
  }
}
