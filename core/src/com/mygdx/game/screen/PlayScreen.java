package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.data.AssetHelper;
import com.mygdx.game.model.Animal;
import com.mygdx.game.model.Pair;
import com.mygdx.game.PikachuGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayScreen implements Screen {
  private static final int ROWS = 5;
  private static final int COLUMNS = 6;
  private final float centerX;
  private final float centerY;

  private final PikachuGame game;
  private final AssetManager assetManager;
  private static Stage stage;

  private static Group board, lineSelect;
  public static HashMap<String, Animal> animalHashMap;
  Array<Animal> animalArray = new Array<>();

  private TextureAtlas animals;
  private static TextureAtlas ui;
  private static TextureAtlas btn;
  private Image closeScreen, levelTitle;
  private int level;
  public static Animal animalSelect;
  private static int distance;
  Label.LabelStyle style;
  BitmapFont bitmapFont;

  //  private HUD
//  public MapGroup HUD = new MapGroup();
  public PlayScreen(PikachuGame game, Viewport viewport) {
    this.game = game;
    stage = new Stage();
    stage.setViewport(viewport);
    this.level = 0;
    animalHashMap = new HashMap<>();
    animalSelect = null;
    board = new Group();
    lineSelect = new Group();
    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;
    distance = (int) stage.getWidth() / (COLUMNS + 1);
    assetManager = new AssetManager();
    assetManager.load("textureAtlas/animals2.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/btn.atlas", TextureAtlas.class);
    assetManager.finishLoading();

    bitmapFont = game.getAssetHelper().get("font/arial_uni_30.fnt");
    style = new Label.LabelStyle();
    style.font = bitmapFont;
  }

  public void resetScreen() {
    // Xóa dữ liệu trong animalHashMap để không còn animal cũ
    while (board.getChildren().notEmpty())
      board.getChildren().first().remove();
    board.clear();
    board.remove();

    while (lineSelect.getChildren().notEmpty())
      lineSelect.getChildren().first().remove();
    lineSelect.clear();
    lineSelect.remove();

    while (animalArray.notEmpty()) {
      animalArray.pop().remove();
    }
    animalHashMap.clear();

//    System.out.println("size Stage: "+stage.getActors().size);
    animals = assetManager.get("textureAtlas/animals2.atlas");
    ui = assetManager.get("textureAtlas/ui.atlas");
    btn = assetManager.get("textureAtlas/btn.atlas");
    createBoard(); // Tạo lại các animal mới


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
//        for (Actor a: stage.getActors()) {
//          if(a instanceof Animal){
//            a.remove();  // Xóa tất cả animal khi đóng màn hình
//          }
//        }

        game.setScreen(game.getHomeScreen());
      }
    });
    stage.addActor(closeScreen);

    //TODO create button buffer
    Image hind = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    hind.setBounds(distance + 20, distance, distance, distance);
    hind.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        hindControl();
      }

    });
    stage.addActor(hind);

    final Image shuffle = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    shuffle.setBounds((distance + 20) * 2, distance, distance, distance);
    shuffle.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        shuffleControl();
      }


    });
    stage.addActor(shuffle);

    Image Undo = new Image(new TextureRegionDrawable(ui.findRegion("btn_blue")));
    Undo.setBounds((distance + 20) * 3, distance, distance, distance);
    Undo.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        undoControl();
      }


    });
    stage.addActor(Undo);

  }

  private void hindControl() {
    // Find and highlight a valid pair of matching animals
    for (int i = 0; i < animalArray.size - 1; i++) {
      Animal first = animalArray.get(i);
      if (first == null || !first.getParent().equals(board)) continue; // Skip if already removed

      for (int j = i + 1; j < animalArray.size; j++) {
        Animal second = animalArray.get(j);
        if (second == null || !second.getParent().equals(board)) continue; // Skip if already removed

        if (first.getId() == second.getId()) {
          // Found a matching pair, highlight them temporarily
          first.addAction(Actions.sequence(
              Actions.color(Color.YELLOW, 0.5f),
              Actions.color(Color.WHITE, 0.5f)
          ));
          second.addAction(Actions.sequence(
              Actions.color(Color.YELLOW, 0.5f),
              Actions.color(Color.WHITE, 0.5f)
          ));
          return; // Exit after finding first valid pair
        }
      }
    }
  }

  private void shuffleControl() {
    // Collect all remaining animals
    ArrayList<Animal> remainingAnimals = new ArrayList<>();
    for (Animal animal : animalArray) {
      // Check if animal exists and is still on the board
      if (animal != null && animal.getParent() != null && animal.getParent().equals(board)) {
        remainingAnimals.add(animal);
        animal.remove();  // Remove from current position
      }
    }

    // Shuffle their positions
    Collections.shuffle(remainingAnimals);

    // Reassign new positions and update the board
    animalHashMap.clear();
    animalArray.clear();

    int index = 0;
    for (int i = 0; i < COLUMNS && index < remainingAnimals.size(); i++) {
      for (int j = 0; j < ROWS && index < remainingAnimals.size(); j++) {
        Animal animal = remainingAnimals.get(index);
        animal.setGridX(i);
        animal.setGridY(j);
        animal.setPosition(i * distance, j * distance);
        createAnimal(animal);  // Re-add to board with new position
        index++;
      }
    }
  }

  private void undoControl() {

  }

  public static void setAnimalSelect(Animal animal) {
    animalSelect = animal;
    if (animal != null) {
      board.addActor(lineSelect);
    }
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

  private void createBoard() {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 1; i <= COLUMNS * ROWS / 2; i++) {
      @SuppressWarnings("NewApi") int randomId = ThreadLocalRandom.current().nextInt(1, 35);
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
    animalArray.add(animal);
    board.addActor(animal);
  }
//
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
    if (pairList != null && !pairList.isEmpty()) {
      StringBuilder pathFull = new StringBuilder("path full: ");

      // Ghi lại toàn bộ danh sách để debug
      for (Pair pair : pairList) {
        pathFull.append("- [").append(pair.getX()).append(",").append(pair.getY()).append("] ");
      }
      System.out.println(pathFull);

      // Duyệt qua tất cả các cặp Pair trong danh sách
      for (int i = 0; i < pairList.size(); i++) {
        Pair pair1 = pairList.get(i);
        for (int j = i + 1; j < pairList.size(); j++) { // Bắt đầu từ i+1 để tránh lặp lại cặp
          Pair pair2 = pairList.get(j);

          // Kiểm tra nếu hai Pair cách nhau 1 đơn vị x hoặc y
          boolean isVertical = pair1.getX() == pair2.getX() &&
              (Math.abs(pair1.getY() - pair2.getY()) == 1);
          boolean isHorizontal = pair1.getY() == pair2.getY() &&
              (Math.abs(pair1.getX() - pair2.getX()) == 1);

          if (isVertical || isHorizontal) {
            // Tính toán chiều rộng và cao của đường kẻ
            float width = isVertical ? (float) distance / 10 : distance;
            float height = isVertical ? distance : (float) distance / 10;

            // Tính toán vị trí của đường kẻ
            float x = (Math.min(pair1.getX(), pair2.getX()) + 0.5f) * distance + board.getX();
            float y = (Math.min(pair1.getY(), pair2.getY()) + 0.5f) * distance + board.getY();

            // Tạo và thêm đường kẻ vào stage
            Image line = new Image(new TextureRegionDrawable(ui.findRegion("line_red")));
            line.setBounds(x, y, width, height);
            stage.addActor(line);
            line.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.removeActor()
            ));
          }
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
    if (animalSelect != null) {
      lineSelect.setPosition(animalSelect.getX(), animalSelect.getY());
      lineSelect.setSize(distance, distance);
      lineSelect.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          animalSelect.setSelected(false);
          setAnimalSelect(null);
          lineSelect.remove();
          super.clicked(event, x, y);
        }
      });
    }

    if (animalHashMap.isEmpty()) {
      Preferences pref = Gdx.app.getPreferences("Pika vip");
      if (pref.getInteger("levelCompleted") <= level)
        pref.putInteger("levelCompleted", level + 1);
      pref.flush();
      Timer.schedule(new Timer.Task() {
        @Override
        public void run() {
          game.setScreen(game.getHomeScreen());
        }
      }, 2f);
    }
  }

  public static void removeAnimalSelect(Animal animal) {
    PlayScreen.animalHashMap.remove(animal.getKey());
    PlayScreen.animalHashMap.remove(animalSelect.getKey());
    animal.remove();
    animalSelect.remove();
    lineSelect.remove();
//    animal.addAction(Actions.sequence(
//        Actions.delay(0.3f),    // Đợi 0.3 giây
//        Actions.removeActor() // Xóa khỏi stage
//    ));
//    animalSelect.addAction(Actions.sequence(
//        Actions.delay(0.3f),    // Đợi 0.3 giây
//        Actions.removeActor() // Xóa khỏi stage
//    ));
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
    animalHashMap.clear();
  }

  public void setLevel(int level) {
    this.level = level;
  }
}

