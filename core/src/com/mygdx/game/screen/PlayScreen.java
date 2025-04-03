package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GMain;
import com.mygdx.game.data.GAssetsManager;
import com.mygdx.game.data.LevelManager;
import com.mygdx.game.model.Level;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.ButtonFactory;
import com.mygdx.game.utils.GConstants;
import com.mygdx.game.view.Board;
import com.mygdx.game.view.HUD;

public class PlayScreen implements Screen {
  // Game core components
  private final GMain game;
  private final GAssetsManager assetManager;
  private static Stage stage;
  private Player player;
  private Board board;
  private HUD hud;
  private LevelManager levelManager;
  private int level;

  // UI-related components
  private final ButtonFactory buttonFactory;
//  private static TextureAtlas ui;
  private UiPopup currentPopup;
  private UiPopup pausePopup;
  private Label.LabelStyle style;
//  private BitmapFont bitmapFont;

  // Game board dimensions
  private static int ROWS;
  private static int COLUMNS;
  private static int distance;

  // Screen positioning
  private final float centerX;
  private final float centerY;

  // Scoring constants
  private static final int POINTS_PER_PAIR = 100;
  private static final int TIME_BONUS_MULTIPLIER = 10;

  public PlayScreen(GMain game) {
    // Game core components initialization
    this.game = game;
    assetManager = game.getAssetHelper();
    stage = new Stage(game.getStage().getViewport());
    player = game.getPlayer();
    this.level = 2;
    levelManager = new LevelManager();
    board = new Board();
    board.setPlayScreen(this);

// UI-related components initialization
    buttonFactory = new ButtonFactory(game.getSkinManager(), game.getSoundManager());
//    ui = assetManager.get(DEFAULT_UI + LIST_SKIN_UI[0]);
    pausePopup = new UiPopup(game);
    hud = new HUD(game, stage, board);
//    bitmapFont = game.getAssetHelper().get("font/arial_uni_30.fnt");
    style = new Label.LabelStyle();
//    style.font = bitmapFont;


// Screen positioning initialization
    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;

// Final setup

  }

  public void showBoard() {
    System.out.println("[PlayScreen]: Show board");
    Level levelData = levelManager.getLevel(level);
    board.setNew(levelData);
    hud.setTime(levelData.getTime());
    board.setPosition(centerX - board.getWidth() / 2, centerY - board.getHeight() / 2);
    board.setOrigin(board.getWidth() / 2, board.getHeight() / 2);
    board.setScale(centerX / board.getWidth() * 1.2f);
    board.getPathFinder().setBoard(board);
    stage.addActor(board);

    Gdx.input.setInputProcessor(stage);
  }

  private void createBtn() {
    System.out.println("[PlayScreen]: create btn");
    ImageButton btnSetting = buttonFactory.createButtonWood("btn_setting", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if(!pausePopup.isPause()){

          pausePopup.setUiPause();
          pausePopup.setVisible(true);
          pausePopup.setPosition((stage.getWidth() * 0.1f), (stage.getHeight() *0.1f) );
        }
        stage.addActor(pausePopup);
        System.out.println("[PlayScreen]: click btnSetting");
      }
    });
    btnSetting.setSize(GConstants.TILE_SIZE * 2, GConstants.TILE_SIZE * 2);
    btnSetting.setPosition(centerX * 2f - btnSetting.getWidth(), centerY * 2f - btnSetting.getHeight());
    stage.addActor(btnSetting);
  }

  @Override
  public void show() {
    createBtn();
    hud.resetTime();
//    stage.clear();
    showBoard();
  }

  @Override
  public void render(float delta) {
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
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {
//    clearPopup();
  }

  @Override
  public void dispose() {
    clearPopup();
    stage.dispose();
//    assetManager.dispose();
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
//    currentPopup.setUiWin(level, calculateStars(timeLeft));
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
    currentPopup.setPosition(stage.getWidth()*0.1f,stage.getHeight()*0.1f);
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