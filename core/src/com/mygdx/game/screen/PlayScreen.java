package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.control.PathResult;
import com.mygdx.game.helper.AssetHelper;
import com.mygdx.game.model.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayScreen implements Screen {
  private static final int ROWS = 9;
  private static final int COLUMNS = 6;
  private final float centerX;
  private final float centerY;

  private final Game game;
  private final AssetManager assetManager;
  private final Stage stage;
  private final Screen previousScreen;

  private final Group board;
  public static HashMap<String, Animal> animalHashMap;

  private TextureAtlas animals, ui;
  private Image closeScreen, levelTitle;
  private int level;
  public static Animal animalSelect;
  private final int distance;

  public PlayScreen(Game game, Screen previousScreen, Viewport viewport, int level) {
    this.game = game;
    this.previousScreen = previousScreen;
    this.stage = new Stage();
    this.stage.setViewport(viewport);
    this.level = level;
    animalHashMap = new HashMap<>();
    animalSelect = null;
    board = new Group();

    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;
    distance = (int) stage.getWidth() / (COLUMNS + 1);
    assetManager = new AssetManager();
    assetManager.load("textureAtlas/animals2.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui.atlas", TextureAtlas.class);
    assetManager.finishLoading();
  }

  @Override
  public void show() {
    animals = assetManager.get("textureAtlas/animals2.atlas");
    AssetHelper assetHelper = AssetHelper.getInstance();
//    animals = assetHelper.get();
    ui = assetManager.get("textureAtlas/ui.atlas");
    createBoard();

    levelTitle = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    levelTitle.setSize(200,100);
    levelTitle.setPosition(centerX - 0.5f*levelTitle.getWidth(),centerY*2-levelTitle.getHeight());

    BitmapFont bitmapFont = assetHelper.get("font/arial_uni_30.fnt");
    Label.LabelStyle style = new Label.LabelStyle();
    style.font = bitmapFont;
    Label labelTitleLevel = new Label("Level: "+level,style);
    labelTitleLevel.setBounds(levelTitle.getX(), levelTitle.getY(), levelTitle.getWidth(),levelTitle.getHeight());
    labelTitleLevel.setAlignment(Align.center);

    closeScreen = new Image(new TextureRegionDrawable(ui.findRegion("btn_red")));
    closeScreen.setBounds(centerX * 2 - 100, centerY * 2 - 100, 100, 100);
    closeScreen.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(previousScreen);
      }
    });

    stage.addActor(closeScreen);
    stage.addActor(levelTitle);
    stage.addActor(labelTitleLevel);
    Gdx.input.setInputProcessor(stage);
  }

  private void createBoard() {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 1; i <= COLUMNS * ROWS / 2; i++) {
//      numbers.add(i % 10);
//      numbers.add(i % 10);
      numbers.add(26);
      numbers.add(26);
    }
    Collections.shuffle(numbers, new Random());
    int n = 0;
    for (int i = 0; i < COLUMNS; i++) {
      for (int j = 0; j < ROWS; j++) {
        TextureRegion ani = new TextureRegion(animals.findRegion("" + numbers.get(n++)));
        Animal animal = new Animal(ani, numbers.get(n - 1), i, j, distance);
        createAnimal(animal);
      }
    }
    board.setPosition(centerX - distance/2 * (COLUMNS), centerY - distance/2 * (ROWS));
    stage.addActor(board);
  }

  private void createAnimal(Animal animal) {
    String key = animal.getKey();
    animalHashMap.put(key, animal);
    board.addActor(animal);
  }

  public static void drawMatrix() {
    for (int j = ROWS; j >= -1; j--) {
      for (int i = -1; i <= COLUMNS; i++) {
        if (animalHashMap.get(getKey(i, j)) == null) {
          System.out.print(" -");
        } else {
          System.out.print(" " + animalHashMap.get(getKey(i, j)).getId());
        }
      }
      System.out.println("");
    }
    System.out.println("----------------------------------");
  }
//  public static boolean checkEdible(Animal a1, Animal a2) {
//    if (a1 == null || a2 == null) return false;
//
//    int x1 = a1.getGridX();
//    int y1 = a1.getGridY();
//    int x2 = a2.getGridX();
//    int y2 = a2.getGridY();
//
//    if (isStraightPath(x1, y1, x2, y2)) {
////      drawLazeCol(a1, a2, x1);
//      return true;
//    }
//    if (isOneCornerPath(x1, y1, x2, y2)) {
////      drawLazeCol(a1, a2, x1);
//      return true;
//    }
//    if (isTwoCornerPath(x1, y1, x2, y2)) {
////      drawLazeCol(a1, a2, x1);
//      return true;
//    }
//    return false;
//  }

  public static PathResult checkEdible(Animal a1, Animal a2) {
    if (a1 == null || a2 == null) return new PathResult(false, null, null);

    int x1 = a1.getGridX();
    int y1 = a1.getGridY();
    int x2 = a2.getGridX();
    int y2 = a2.getGridY();
    List<int[]> pathCoordinates = new ArrayList<>();

    if (isStraightPath(x1, y1, x2, y2)) {
      // Thêm tọa độ cho đường thẳng
      for (int y = Math.min(y1, y2) + 1; y < Math.max(y1, y2); y++) {
        pathCoordinates.add(new int[]{x1, y});
      }
      return new PathResult(true, "straight", pathCoordinates);
    }
    if (isOneCornerPath(x1, y1, x2, y2)) {
      // Thêm tọa độ cho đường một góc
      if (y1 != y2) {
        for (int y = Math.min(y1, y2) + 1; y < Math.max(y1, y2); y++) {
          pathCoordinates.add(new int[]{x1, y});
        }
        pathCoordinates.add(new int[]{x2, y1});
      } else {
        for (int x = Math.min(x1, x2) + 1; x < Math.max(x1, x2); x++) {
          pathCoordinates.add(new int[]{x, y1});
        }
        pathCoordinates.add(new int[]{x1, y2});
      }
      return new PathResult(true, "oneCorner", pathCoordinates);
    }
    if (isTwoCornerPath(x1, y1, x2, y2)) {
      // Thêm tọa độ cho đường hai góc
      for (int x = -1; x <= COLUMNS; x++) {
        if (isOneCornerPath(x1, y1, x, y1) && isOneCornerPath(x, y1, x2, y2)) {
          // Thêm tọa độ cho góc đầu tiên
          for (int y = Math.min(y1, y1) + 1; y < Math.max(y1, y1); y++) {
            pathCoordinates.add(new int[]{x1, y});
          }
          pathCoordinates.add(new int[]{x, y1});
          // Thêm tọa độ cho góc thứ hai
          for (int y = Math.min(y2, y2) + 1; y < Math.max(y2, y2); y++) {
            pathCoordinates.add(new int[]{x, y});
          }
          return new PathResult(true, "twoCorners", pathCoordinates);
        }
      }
    }
    return new PathResult(false, null, null);
  }

  public static boolean isTwoCornerPath(int x1, int y1, int x2, int y2) {
    for (int x = -1; x <= COLUMNS; x++) {
      if (isOneCornerPath(x1, y1, x, y1) && isOneCornerPath(x, y1, x2, y2)) {
        return true;
      }
    }
    for (int y = -1; y <= ROWS; y++) {
      if (isOneCornerPath(x1, y1, x1, y) && isOneCornerPath(x1, y, x2, y2)) {
        return true;
      }
    }
    return false;
  }
  public static boolean isOneCornerPath(int x1, int y1, int x2, int y2) {
    if (isStraightPath(x1, y1, x1, y2) && isStraightPath(x1, y2, x2, y2) && animalHashMap.get(getKey(x1, y2)) == null) {
      return true;
    }
    if (isStraightPath(x1, y1, x2, y1) && isStraightPath(x2, y1, x2, y2) && animalHashMap.get(getKey(x2, y1)) == null) {
      return true;
    }
    return false;
  }
  public static boolean isStraightPath(int x1, int y1, int x2, int y2) {
    if (x1 == x2) { // Cùng cột
      int minY = Math.min(y1, y2);
      int maxY = Math.max(y1, y2);
      for (int y = minY + 1; y < maxY; y++) {
        if (animalHashMap.get(getKey(x1, y)) != null) {
          return false; // Có vật cản
        }
      }
      return true;
    } else if (y1 == y2) { // Cùng hàng
      int minX = Math.min(x1, x2);
      int maxX = Math.max(x1, x2);
      for (int x = minX + 1; x < maxX; x++) {
        if (animalHashMap.get(getKey(x, y1)) != null) {
          return false; // Có vật cản
        }
      }
      return true;
    }
    return false;
  }

  private static String getKey(int x, int y) {
    return x + "," + y;
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f,0.6f,0.4f,1);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
  }

  public static void removeAnimalSelect() {
    animalHashMap.remove(PlayScreen.animalSelect.getKey());
    animalSelect.remove();
    animalSelect = null;
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
}
