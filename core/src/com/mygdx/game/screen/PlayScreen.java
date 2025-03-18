package com.mygdx.game.screen;

import static com.mygdx.game.utils.GameConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.data.AssetHelper;
import com.mygdx.game.data.LevelManager;
import com.mygdx.game.model.Level;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.GameConstants;
import com.mygdx.game.view.Board;
import com.mygdx.game.utils.ButtonFactory;
import com.mygdx.game.view.HUD;

public class PlayScreen implements Screen {
  private final PikachuGame game;
  private static Stage stage;

  private final AssetHelper assetManager;
  private Player player;
  private HUD hud;
  private Board board;
  private final ButtonFactory buttonFactory;
  private LevelManager levelManager;

  private static int ROWS;
  private static int COLUMNS;
  private final float centerX;
  private final float centerY;

  private static TextureAtlas ui;
  private Image levelTitle;
  private int level;
  private static int distance;
  Label.LabelStyle style;
  BitmapFont bitmapFont;

  public PlayScreen(PikachuGame game) {
    this.game = game;
    stage = new Stage(game.getStage().getViewport());
    this.level = 1;
    assetManager = game.getAssetHelper();
    buttonFactory = new ButtonFactory(game.getSkinManager(),game.getSoundManager());
    levelManager = new LevelManager();
    ui = assetManager.get(DEFAULT_UI+LIST_SKIN_UI[0]);
    player = game.getPlayer();
    ROWS = GameConstants.DEFAULT_ROWS;
    COLUMNS = GameConstants.DEFAULT_COLS;
    board = new Board(ROWS,COLUMNS);
    hud = new HUD(player,game.getSkinManager());
    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;
    distance = (int) stage.getWidth() / (COLUMNS + 1);


    bitmapFont = game.getAssetHelper().get("font/arial_uni_30.fnt");
    style = new Label.LabelStyle();
    style.font = bitmapFont;
    showBoard();
    createBtn();
    showHUD();
  }

  private void showHUD() {
    ImageButton hintButton = buttonFactory.createHintButton(player, board);
    hintButton.setBounds(centerX*0.4f, centerY*0.08f,centerX*0.3f, centerX*0.3f);
    stage.addActor(hintButton);

    ImageButton shuffleButton = buttonFactory.createShuffleButton(player, board);
    shuffleButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        board.shuffle();
        super.clicked(event, x, y);
      }
    });
    shuffleButton.setBounds(centerX*0.85f, centerY*0.08f, centerX*0.3f,centerX*0.3f);
    stage.addActor(shuffleButton);

    ImageButton undoButton = buttonFactory.createUndoButton(player, board);
    undoButton.setBounds(centerX*1.3f, centerY*0.08f, centerX*0.3f, centerX*0.3f);
    stage.addActor(undoButton);

    ImageButton closeButton = buttonFactory.createCloseButton(player,game);
    closeButton.setBounds(centerX * 2 - 100, centerY * 2 - 100, 100, 100);

    stage.addActor(closeButton);
  }

  public void showBoard() {
    createBtn();
    Level levelData = levelManager.getLevel(level);
    board.setNew(levelData);
    hud.setTime(levelData.getTime());
    board.setPosition(centerX -board.getWidth()/2,centerY-board.getHeight()/2);
    board.setOrigin(board.getWidth()/2,board.getHeight()/2);
    board.setScale(centerX/board.getWidth()*1.2f);
    board.getPathFinder().setBoard(board);
    stage.addActor(board);

    stage.addActor(levelTitle);
    Gdx.input.setInputProcessor(stage);
  }

  private void createBtn() {

    levelTitle = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    levelTitle.setSize(200, 100);
    levelTitle.setPosition(centerX - 0.5f * levelTitle.getWidth(), centerY * 2 - levelTitle.getHeight());

    Label labelTitleLevel = new Label("Level: " + level, style);
    labelTitleLevel.setBounds(levelTitle.getX(), levelTitle.getY(), levelTitle.getWidth(), levelTitle.getHeight());
    labelTitleLevel.setAlignment(Align.center);

    stage.addActor(labelTitleLevel);
  }




  @Override
  public void show() {
    hud.resetTime();
    showBoard();
  }


  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    hud.update(delta);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
    hud.render();
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
    stage.dispose();
    assetManager.dispose();
  }

  public void setLevel(int level) {
    this.level = level;
  }
}

