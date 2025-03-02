package com.mygdx.game.defaults.screen;

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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.defaults.PikachuGameA;
import com.mygdx.game.defaults.model.Animal;
import com.mygdx.game.defaults.model.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayScreen implements Screen {
  private static final int ROWS = 6;
  private static final int COLUMNS = 6;
  private final float centerX;
  private final float centerY;

  private final PikachuGameA game;
  private final AssetManager assetManager;
  private static Stage stage;

  private static Group board;
  public static HashMap<String, Animal> animalHashMap;

  private TextureAtlas animals;
  private static TextureAtlas ui;
  private Image closeScreen, levelTitle;
  private int level;
  public static Animal animalSelect;
  private static int distance;

  public PlayScreen(PikachuGameA game, Viewport viewport) {
    this.game = game;
    stage = new Stage();
    stage.setViewport(viewport);
    this.level = 0;
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

  public void resetScreen() {
//    stage.clear();
    System.out.println("size Stage: "+stage.getActors().size);

    stage = new Stage(stage.getViewport());
    animals = assetManager.get("textureAtlas/animals2.atlas");
    ui = assetManager.get("textureAtlas/ui.atlas");
    createBoard();

    levelTitle = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    levelTitle.setSize(200, 100);
    levelTitle.setPosition(centerX - 0.5f * levelTitle.getWidth(), centerY * 2 - levelTitle.getHeight());

    BitmapFont bitmapFont = game.getAssetManager().get("font/arial_uni_30.fnt");
    Label.LabelStyle style = new Label.LabelStyle();
    style.font = bitmapFont;
    Label labelTitleLevel = new Label("Level: " + level, style);
    labelTitleLevel.setBounds(levelTitle.getX(), levelTitle.getY(), levelTitle.getWidth(), levelTitle.getHeight());
    labelTitleLevel.setAlignment(Align.center);

    closeScreen = new Image(new TextureRegionDrawable(ui.findRegion("btn_red")));
    closeScreen.setBounds(centerX * 2 - 100, centerY * 2 - 100, 100, 100);
    closeScreen.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(game.getHomeScreen());
      }
    });

    stage.addActor(closeScreen);
    stage.addActor(levelTitle);
    stage.addActor(labelTitleLevel);
    System.out.println("size Stage: "+stage.getActors().size);

    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void show() {
    resetScreen();
  }

  private void createBoard() {
    List<Integer> numbers = new ArrayList<>();
    @SuppressWarnings("NewApi") int randomId = ThreadLocalRandom.current().nextInt(1, 35);
    for (int i = 1; i <= COLUMNS * ROWS / 2; i++) {
//      numbers.add(i % 35);
//      numbers.add(i % 35);
      numbers.add(randomId);
      numbers.add(randomId);
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
    board.setPosition(centerX - (float) distance / 2 * (COLUMNS), centerY - (float) distance / 2 * (ROWS));
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
      System.out.println(" ");
    }
    System.out.println("----------------------------------");
  }

  public static boolean checkEdible(Animal a1, Animal a2) {
    if (a1 == null || a2 == null) return false;

    int x1 = a1.getGridX();
    int y1 = a1.getGridY();
    int x2 = a2.getGridX();
    int y2 = a2.getGridY();
    List<Pair> pairList = new ArrayList<>();
    if (isStraightPath(x1, y1, x2, y2, pairList)) {
      drawLine(pairList);
      return true;
    }
    pairList.clear();
    if (isOneCornerPath(x1, y1, x2, y2, pairList)) {
      drawLine(pairList);
      return true;
    }
    pairList.clear();
    if (isTwoCornerPath(x1, y1, x2, y2, pairList)) {
      drawLine(pairList);
      return true;
    }
    return false;
  }

  private static void drawLine(List<Pair> pairList) {
    if (pairList != null) {
      for (Pair pair : pairList) {
        if (animalHashMap.get(getKey(pair.getX(), pair.getY())) == null) {
          Image coin = new Image(new TextureRegionDrawable(ui.findRegion("coin")));
          coin.setBounds(pair.getX() * distance + board.getX(), pair.getY() * distance + board.getY(), distance - 5, distance - 5);
          stage.addActor(coin);
          coin.addAction(Actions.sequence(
              Actions.delay(1f),    // Đợi 1 giây
              Actions.removeActor() // Xóa khỏi stage
          ));

        }
      }
    }
  }


  public static boolean isTwoCornerPath(int x1, int y1, int x2, int y2, List<Pair> pairList) {
    for (int x = -1; x <= COLUMNS; x++) {
      if (isOneCornerPath(x1, y1, x, y1, pairList) && isOneCornerPath(x, y1, x2, y2, pairList)) {
        return true;
      }
      pairList.clear();
    }
    for (int y = -1; y <= ROWS; y++) {
      if (isOneCornerPath(x1, y1, x1, y, pairList) && isOneCornerPath(x1, y, x2, y2, pairList)) {
        return true;
      }
      pairList.clear();
    }
    return false;
  }

  public static boolean isOneCornerPath(int x1, int y1, int x2, int y2, List<Pair> pairList) {
    if (isStraightPath(x1, y1, x1, y2, pairList) && isStraightPath(x1, y2, x2, y2, pairList) && animalHashMap.get(getKey(x1, y2)) == null) {
      return true;
    }
    if (isStraightPath(x1, y1, x2, y1, pairList) && isStraightPath(x2, y1, x2, y2, pairList) && animalHashMap.get(getKey(x2, y1)) == null) {
      return true;
    }
    return false;
  }

  public static boolean isStraightPath(int x1, int y1, int x2, int y2, List<Pair> pairList) {
    List<Pair> pairList2 = new ArrayList<>();
    if (x1 == x2) { // Cùng cột
      int minY = Math.min(y1, y2);
      int maxY = Math.max(y1, y2);
      pairList2.add(new Pair(x1, minY));
      pairList2.add(new Pair(x1, maxY));
      for (int y = minY + 1; y < maxY; y++) {
        if (animalHashMap.get(getKey(x1, y)) != null) {
          return false; // Có vật cản
        }
        pairList2.add(new Pair(x1, y));
      }
      pairList.addAll(pairList2);
      return true;
    } else if (y1 == y2) { // Cùng hàng
      int minX = Math.min(x1, x2);
      int maxX = Math.max(x1, x2);
      pairList2.add(new Pair(minX, y1));
      pairList2.add(new Pair(maxX, y1));
      for (int x = minX + 1; x < maxX; x++) {
        if (animalHashMap.get(getKey(x, y1)) != null) {
          return false; // Có vật cản
        }
        pairList2.add(new Pair(x, y1));
      }
      pairList.addAll(pairList2);
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
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
  }

  public static void removeAnimalSelect(Animal animal) {
    PlayScreen.animalHashMap.remove(animal.getKey());
    PlayScreen.animalHashMap.remove(animalSelect.getKey());
    animal.addAction(Actions.sequence(
        Actions.delay(1f),    // Đợi 1 giây
        Actions.removeActor() // Xóa khỏi stage
    ));
    animalSelect.addAction(Actions.sequence(
        Actions.delay(1f),    // Đợi 1 giây
        Actions.removeActor() // Xóa khỏi stage
    ));
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

