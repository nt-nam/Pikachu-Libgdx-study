package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.utils.GameConstants;

public class PlayScreen implements Screen {
  private static int ROWS;
  private static int COLUMNS;
  private final float centerX;
  private final float centerY;

  private final PikachuGame game;
  private final AssetManager assetManager;
  private static Stage stage;

  private static Group board, lineSelect;

  private static TextureAtlas ui;
  private Image closeScreen, levelTitle;
  private int level;
  private static int distance;
  Label.LabelStyle style;
  BitmapFont bitmapFont;

  public PlayScreen(PikachuGame game) {
    this.game = game;
    stage = new Stage();
    stage.setViewport(game.getStage().getViewport());
    this.level = 0;
    board = new Group();
    lineSelect = new Group();
    ROWS = GameConstants.DEFAULT_ROWS;
    COLUMNS = GameConstants.DEFAULT_COLS;
    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;
    distance = (int) stage.getWidth() / (COLUMNS + 1);
    assetManager = new AssetManager();
    assetManager.load("textureAtlas/ani.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/btn.atlas", TextureAtlas.class);
    assetManager.finishLoading();

    bitmapFont = game.getAssetHelper().get("font/arial_uni_30.fnt");
    style = new Label.LabelStyle();
    style.font = bitmapFont;
  }

  public void resetScreen() {
    while (board.getChildren().notEmpty())
      board.getChildren().first().remove();
    board.clear();
    board.remove();

    while (lineSelect.getChildren().notEmpty())
      lineSelect.getChildren().first().remove();
    lineSelect.clear();
    lineSelect.remove();


    ui = assetManager.get("textureAtlas/ui.atlas");


    createLineSelect();
    createBtn();
    stage.addActor(levelTitle);
    Gdx.input.setInputProcessor(stage);
  }

  private void createBtn() {
    //TODO create title level
    levelTitle = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    levelTitle.setSize(200, 100);
    levelTitle.setPosition(centerX - 0.5f * levelTitle.getWidth(), centerY * 2 - levelTitle.getHeight());

    Label labelTitleLevel = new Label("Level: " + level, style);
    labelTitleLevel.setBounds(levelTitle.getX(), levelTitle.getY(), levelTitle.getWidth(), levelTitle.getHeight());
    labelTitleLevel.setAlignment(Align.center);

    stage.addActor(labelTitleLevel);


    closeScreen = new Image(new TextureRegionDrawable(ui.findRegion("line_red")));
    closeScreen.setBounds(centerX * 2 - 100, centerY * 2 - 100, 100, 100);
    closeScreen.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(game.getHomeScreen());
      }
    });
    stage.addActor(closeScreen);

    Image hind = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    hind.setBounds(distance + 20, distance, distance, distance);
    hind.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
      }

    });
    stage.addActor(hind);

    final Image shuffle = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    shuffle.setBounds((distance + 20) * 2, distance, distance, distance);
    shuffle.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
      }
    });
    stage.addActor(shuffle);

    Image Undo = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    Undo.setBounds((distance + 20) * 3, distance, distance, distance);
    Undo.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
      }
    });
    stage.addActor(Undo);

  }

  private void createLineSelect() {
    TextureRegion ani = new TextureRegion(ui.findRegion("line_red"));
    Image line1 = new Image(ani);
    Image line2 = new Image(ani);
    Image line3 = new Image(ani);
    Image line4 = new Image(ani);
    int size = 10;
    line1.setBounds(0, 0, size, distance);
    line2.setBounds(distance - size, 0, size, distance);
    line3.setBounds(0, 0, distance, size);
    line4.setBounds(0, distance - size, distance, size);

    lineSelect.addActor(line1);
    lineSelect.addActor(line2);
    lineSelect.addActor(line3);
    lineSelect.addActor(line4);
//    board.addActor(lineSelect);
  }


  @Override
  public void show() {
    resetScreen();
  }


//  public static void drawMatrix() {
//    for (int j = ROWS; j >= -1; j--) {
//      for (int i = -1; i <= COLUMNS; i++) {
//        if (animalHashMap.get(getKey(i, j)) == null) {
//          System.out.print(" -");
//        } else {
//          System.out.print(" " + animalHashMap.get(getKey(i, j)).getId());
//        }
//      }
//      System.out.println(" ");
//    }
//    System.out.println("----------------------------------");
//  }


  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
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

