package com.mygame.pikachu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.data.LevelManager;
import com.mygame.pikachu.model.Level;
import com.mygame.pikachu.model.Player;
import com.mygame.pikachu.utils.ButtonFactory;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.view.Board;
import com.mygame.pikachu.view.HUD;

public class PlayScreen implements Screen {
  private final GMain game;
  private static Stage stage;
  private Player player;
  private Board board;
  private HUD hud;
  private LevelManager levelManager;
  private int level;

  private UiPopup currentPopup;
  private UiPopup pausePopup;

  private static int ROWS;
  private static int COLUMNS;
  private static int distance;

  private final float centerX;
  private final float centerY;

  private static final int POINTS_PER_PAIR = 100;
  private static final int TIME_BONUS_MULTIPLIER = 10;

  public PlayScreen(GMain game) {
    this.game = game;
    stage = new Stage(game.getStage().getViewport());
    player = game.getPlayer();
    this.level = 2;
    levelManager = new LevelManager();
    board = new Board();

    pausePopup = new UiPopup(game);
    pausePopup.setVisible(false);
    hud = new HUD(game, stage, board);

    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;
  }

  public void showBoard() {
    System.out.println("[PlayScreen]: Show board");
    Level levelData = levelManager.getLevel(level);
    board.setNew(levelData);
    hud.setTime(levelData.getTime());
    board.setPosition(centerX - board.getWidth() / 2, centerY - board.getHeight() / 2);
    board.setOrigin(board.getWidth() / 2, board.getHeight() / 2);
//    board.setScale(centerX / board.getWidth() * 1.2f);
    board.setScale(centerY / board.getHeight() * 1.2f);
    board.getPathFinder().setBoard(board);
    stage.addActor(board);

    Gdx.input.setInputProcessor(stage);
  }

  private void createBtn() {
    System.out.println("[PlayScreen]: create btn");

    Image btnSetting = IB.New().textureRegion(new TextureRegion(new Texture("atlas/play/btn_pause.png"))).scale(2).pos(100,100).build();
    btnSetting.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if(!pausePopup.isPause()){

          pausePopup.setUiPause();
          pausePopup.setVisible(true);
          pausePopup.setOrigin(Align.center);
          pausePopup.setPosition(stage.getWidth()/2,stage.getHeight()/2,Align.center);
//          hud.setStop(true);
        }
        stage.addActor(pausePopup);
        System.out.println("[PlayScreen]: click btnSetting");
      }
    });
    stage.addActor(btnSetting);
  }

  @Override
  public void show() {
    createBtn();
    hud.resetTime();
    showBoard();
//    hud.setStop(false);
  }

  @Override
  public void render(float delta) {
    hud.setStop(pausePopup.isVisible());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    hud.update(delta);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
    board.updateLineSelect();
    if (hud.isTimeUp()) {
      System.out.println("[PlayScreen]: Time end.");
      handleTimeUp();
    }
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
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
    stage.dispose();
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

    if(currentPopup == null){
      currentPopup = new UiPopup(game);
      currentPopup.setPosition((stage.getWidth() - currentPopup.getWidth() * 0.8f) * 0.5f, (stage.getHeight() - currentPopup.getHeight() * 0.8f) * 0.5f);
    }
    currentPopup.setUiWin(level, 3);
    currentPopup.setLabelWin(player.getScore(), (int) timeLeft);
    stage.addActor(currentPopup);

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
    if (currentPopup == null) {
      currentPopup = new UiPopup(game);
      stage.addActor(currentPopup);
//      currentPopup.addAction(Actions.sequence(
//          Actions.delay(3f),
//          Actions.run(() -> {
//            game.setScreen(game.getHomeScreen());
////            clearPopup();
//          })
//      ));
    }
    currentPopup.setUiLose(board);
    currentPopup.setOrigin(Align.center);
    currentPopup.setPosition(stage.getWidth()/2,stage.getHeight()/2,Align.center);
    currentPopup.setVisible(true);
  }

  private int calculateStars(float timeLeft) {
    float maxTime = GConstants.LEVEL_TIME_SECONDS;
    if (timeLeft > maxTime * 0.75f) return 3;
    else if (timeLeft > maxTime * 0.25f) return 2;
    else return 1;
  }

  private void clearPopup() {
    if (currentPopup != null) {
      currentPopup.remove();
      currentPopup = null;
    }
    if (pausePopup != null) {
      pausePopup.remove();
      pausePopup = null;
    }
  }

  public float getTimeLeft() {
    return hud != null ? hud.getTimeLeft() : 0;
  }
}