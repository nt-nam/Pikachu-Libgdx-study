package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.model.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class PlayScreen implements Screen {
  Game game;
  AssetManager assetManager;
  Stage stage;
  TextureAtlas animals, ui;
  Image closeScreen;
  float centerX, centerY;
  Screen previousScreen;
  int level;
  static int ROWS = 6;
  static int COLUMNS = 6;
  public static HashMap<String, Animal> animalHashMap;
  public static Animal animalSelect;
  Group board;

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
    assetManager = new AssetManager();
    assetManager.load("textureAtlas/animals2.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui.atlas", TextureAtlas.class);
    assetManager.finishLoading();
  }

  @Override
  public void show() {
    animals = assetManager.get("textureAtlas/animals2.atlas");
    ui = assetManager.get("textureAtlas/ui.atlas");
    closeScreen = new Image(new TextureRegionDrawable(ui.findRegion("btn_red")));
    closeScreen.setBounds(centerX * 2 - 100, centerY * 2 - 100, 100, 100);
    closeScreen.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        // quay trở lại màn hình trước đó
        game.setScreen(previousScreen);
      }
    });
//        System.out.println("new level: " + level);
//        stage.setDebugAll(true);
    createBoard();
    stage.addActor(closeScreen);
    Gdx.input.setInputProcessor(stage);
  }

  private void createBoard() {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 1; i <= COLUMNS * ROWS / 2; i++) {
      numbers.add(i % 10);
      numbers.add(i % 10);
    }
    Collections.shuffle(numbers, new Random());
    int n = 0;
    for (int i = 0; i < COLUMNS; i++) {
      for (int j = 0; j < ROWS; j++) {
        TextureRegion ani = new TextureRegion(animals.findRegion("" + numbers.get(n++)));
        Animal animal = new Animal(ani, numbers.get(n - 1), i, j, 100);
//        Animal animal = new Animal(ani, numbers.get(n - 1), i, j, 100);
        createAnimal(animal);
//
//                System.out.print(i+","+j+":"+ numbers.get(n-1)+" - ");
      }
//            System.out.println("");
    }
//        board.setPosition(centerX - board.getWidth()/2,centerY - board.getHeight()/2);
//        board.setPosition(centerX,centerY);
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

  public static boolean checkEdible(Animal a1, Animal a2) {
    if (a1 == null || a2 == null) return false;

    int x1 = a1.getGridX();
    int y1 = a1.getGridY();
    int x2 = a2.getGridX();
    int y2 = a2.getGridY();

    if (isStraightPath(x1, y1, x2, y2)) {
//            drawLazeCol(a1, a2, x1);
      return true;
    }
    if (isOneCornerPath(x1, y1, x2, y2)) {
//            drawLazeCol(a1, a2, x1);
      return true;
    }
    if (isTwoCornerPath(x1, y1, x2, y2)) {
//            drawLazeCol(a1, a2, x1);
      return true;
    }
    return false;
  }

  public static boolean isTwoCornerPath(int x1, int y1, int x2, int y2) {
    for (int x = -1; x <= ROWS; x++) {
      if (isOneCornerPath(x1, y1, x, y1) && isOneCornerPath(x, y1, x2, y2)) {
        return true;
      }
    }
    for (int y = -1; y <= COLUMNS; y++) {
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
