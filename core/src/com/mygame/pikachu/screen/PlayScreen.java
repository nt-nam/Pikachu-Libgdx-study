package com.mygame.pikachu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.data.LevelManager;
import com.mygame.pikachu.model.Level;
import com.mygame.pikachu.model.Player;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.Button;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.MGB;
import com.mygame.pikachu.utils.hud.external.EventHandler;
import com.mygame.pikachu.view.Board;
import com.mygame.pikachu.view.HUD;
import com.mygame.pikachu.view.ui.PopupUI;

public class PlayScreen implements Screen, EventHandler {
  private Player player;
  private Board board;
  private HUD hud;
  private MapGroup playMG;
  private LevelManager levelManager;
  private int level;

  private PopupUI popup;

  private static int ROWS;
  private static int COLUMNS;
  private static int distance;

  private final float centerX;
  private final float centerY;

  private static final int POINTS_PER_PAIR = 100;
  private static final int TIME_BONUS_MULTIPLIER = 10;

  public PlayScreen(GMain game) {
    player = game.getPlayer();
    this.level = 2;
    levelManager = new LevelManager();
    board = new Board();

    playMG = MGB.New().size(GMain.stage().getWidth(), GMain.stage().getHeight()).pos(0, 0, AL.tr).parent(GMain.hud()).build();
    init();
    popup = new PopupUI(game);
    hud = new HUD(game, playMG, board);
    playMG.debug();

    centerX = playMG.getWidth() / 2;
    centerY = playMG.getHeight() / 2;
    addHandle();
  }

  private void addHandle() {

  }
  private void init(){
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    IB.New().drawable("bg").pos(0,0,AL.c).scale(0.82f).parent(playMG).build();
  }

  public void showBoard() {
    System.out.println("[PlayScreen]: Show board");
    Level levelData = levelManager.getLevel(level);
    board.setNew(levelData);
    hud.setTime(levelData.getTime());
    board.setPosition(centerX - board.getWidth() / 2, centerY - board.getHeight() / 2);
    board.setOrigin(board.getWidth() / 2, board.getHeight() / 2);
    board.setScale(centerY / board.getHeight() * 1.2f);
    board.getPathFinder().setBoard(board);
    playMG.addActor(board);

  }

  private void createBtn() {
    System.out.println("[PlayScreen]: create btn");
    Button pause = BB.New().bg("btn_pause").transform(true).pos(0,0,AL.tr).scale(0.5f).parent(playMG).build();
    pause.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
//        if (!pausePopup.isPause()) {
        System.out.println("[PlayScreen]: click pause");
        popup.showPauseUI();
      }
    });
  }

  @Override
  public void show() {
    createBtn();
    hud.resetTime();
    showBoard();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    hud.update(delta);
    playMG.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    board.updateLineSelect();
    if (hud.isTimeUp()) {
      System.out.println("[PlayScreen]: Time end.");
      handleTimeUp();
    }
  }

  @Override
  public void resize(int width, int height) {
//    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    clearPopup();
    playMG.clear();
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void onPairMatched() {
    int currentScore = player.getScore();
    player.setScore(currentScore + POINTS_PER_PAIR);
    hud.update(Gdx.graphics.getDeltaTime());
  }

  public void onLevelCompleted() {
    float timeLeft = hud.isTimeUp() ? 0 : hud.getTimeLeft();
    int timeBonus = (int) (timeLeft * TIME_BONUS_MULTIPLIER);
    int currentScore = player.getScore();
    player.setScore(currentScore + timeBonus);

//    if (currentPopup == null) {
//      currentPopup = new UiPopup(game);
//      currentPopup.setPosition((playMG.getWidth() - currentPopup.getWidth() * 0.8f) * 0.5f, (playMG.getHeight() - currentPopup.getHeight() * 0.8f) * 0.5f);
//    }
//    currentPopup.setUiWin(level, 3);
//    currentPopup.setLabelWin(player.getScore(), (int) timeLeft);
//    playMG.addActor(currentPopup);

//    currentPopup.addAction(Actions.sequence(
//        Actions.delay(3f),
//        Actions.run(() -> {
//          game.setScreen(game.getHomeScreen());
//          clearPopup();
//        })
//    ));

    level++;
    hud.setLevelLabel(level);
    Level nextLevel = levelManager.getLevel(level);
    board.setNew(nextLevel);
    hud.setTime(nextLevel.getTime());
  }

  private void handleTimeUp() {
//    if (currentPopup == null) {
//      currentPopup = new UiPopup(game);
//      playMG.addActor(currentPopup);
////      currentPopup.addAction(Actions.sequence(
////          Actions.delay(3f),
////          Actions.run(() -> {
////            game.setScreen(game.getHomeScreen());
//////            clearPopup();
////          })
////      ));
//    }
//    currentPopup.setUiLose(board);
//    currentPopup.setOrigin(Align.center);
//    currentPopup.setPosition(playMG.getWidth() / 2, playMG.getHeight() / 2, Align.center);
//    currentPopup.setVisible(true);
  }

  private int calculateStars(float timeLeft) {
    float maxTime = GConstants.LEVEL_TIME_SECONDS;
    if (timeLeft > maxTime * 0.75f) return 3;
    else if (timeLeft > maxTime * 0.25f) return 2;
    else return 1;
  }

  private void clearPopup() {
    if (popup != null) {
      popup = null;
    }
  }

  public float getTimeLeft() {
    return hud != null ? hud.getTimeLeft() : 0;
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {

  }
}