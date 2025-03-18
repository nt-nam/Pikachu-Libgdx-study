package com.mygdx.game.view;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.utils.GameConstants.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.data.AssetHelper;
import com.mygdx.game.model.Animal;
import com.mygdx.game.model.Pair;
import com.mygdx.game.model.PathFinder;
import com.mygdx.game.utils.GameConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board extends Group {
  private final int ROWS, COLS;
  private Animal[][] animals;
  private TextureAtlas animalAtlas, uiAtlas;
  private PathFinder pathFinder;
  private Animal animalSelect;
  private Image background;
  private static Group lineSelect;

  public Board(int rows, int cols) {
    ROWS = rows;
    COLS = cols;
    setSize(ROWS * TILE_SIZE, COLS * TILE_SIZE);
    debug();
    animals = new Animal[ROWS][COLS];
    pathFinder = new PathFinder(this);
    animalAtlas = AssetHelper.getAnimals();
    uiAtlas = AssetHelper.getUI();
    lineSelect = new Group();
    createBackground();
    initAnimals();
    createLineSelect();
  }

  private void createBackground() {
    background = new Image(new TextureRegion(new Texture("images/board/board3.png")));
    float scaleBG = 2f;
    background.setPosition((float) -(ROWS * TILE_SIZE) / 2, (float) -(COLS * TILE_SIZE) / 2);
    background.setSize((ROWS * TILE_SIZE) * scaleBG, (COLS * TILE_SIZE) * scaleBG);
    this.addActor(background);
  }

  private void initAnimals() {
    animalSelect = null;
    int totalTiles = ROWS * COLS;
    if (totalTiles % 2 != 0) {
      throw new IllegalArgumentException("Tổng số ô trong board phải là số chẵn!");
    }

    List<Integer> tileTypes = new ArrayList<>();
    for (int i = 0; i < totalTiles / 2; i++) {
      int type = i % GameConstants.ANIMAL_TYPES + 1;
      tileTypes.add(type);
      tileTypes.add(type);
    }

    Collections.shuffle(tileTypes, random);

    int index = 0;
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        TextureRegion ani = new TextureRegion(animalAtlas.findRegion("" + tileTypes.get(index)));
        animals[row][col] = new Animal(ani, tileTypes.get(index), row, col, GameConstants.TILE_SIZE);
        addAnimalListener(animals[row][col]);
        index++;
        addActor(animals[row][col]);
      }
    }
  }

  private void createLineSelect() {
    int distance = TILE_SIZE;
    TextureRegion lineRed = new TextureRegion(uiAtlas.findRegion("line_red"));
    Image line1 = new Image(lineRed);
    Image line2 = new Image(lineRed);
    Image line3 = new Image(lineRed);
    Image line4 = new Image(lineRed);
    int size = 10;
    line1.setBounds(0, 0, size, distance);
    line2.setBounds(distance - size, 0, size, distance);
    line3.setBounds(0, 0, distance, size);
    line4.setBounds(0, distance - size, distance, size);

    lineSelect.addActor(line1);
    lineSelect.addActor(line2);
    lineSelect.addActor(line3);
    lineSelect.addActor(line4);
    lineSelect.setSize(TILE_SIZE, TILE_SIZE);
    addActor(lineSelect);
    lineSelect.setVisible(false);
    lineSelect.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        lineSelect.setVisible(false);
        animalSelect = null;
      }
    });
  }

  private void setBorder(Animal animal) {
    animalSelect = animal;
    if (animalSelect != null) {
      lineSelect.setPosition(animalSelect.getX(), animalSelect.getY());
      lineSelect.setVisible(true);
      return;
    }
    lineSelect.setVisible(false);
  }

  private void addAnimalListener(Animal animal) {
     final int finalRow = animal.getGridX();
     final int finalCol = animal.getGridY();
    animals[finalRow][finalCol].addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        System.out.println(finalRow +"  " +finalCol);
        if (animalSelect == animals[finalRow][finalCol]) {
          setBorder(null);
          return;
        }
        if (animalSelect == null) {
          setBorder(animals[finalRow][finalCol]);
          return;
        }
        List<int[]> path = pathFinder.findPath(animalSelect, animals[finalRow][finalCol]);
        if (path != null) {
          createLineMatch(path);
          animalSelect.setVisible(false);
          animals[finalRow][finalCol].setVisible(false);
          setBorder(null);
        } else {
          setBorder(animals[finalRow][finalCol]);
        }
      }
    });
  }

  private void createLineMatch(List<int[]> path) {

    StringBuilder pathFull = new StringBuilder("path full: ");

    // Ghi lại toàn bộ danh sách để debug
    for (int[] pair : path) {
      pathFull.append("- [").append(pair[0]).append(",").append(pair[1]).append("] ");
    }
    System.out.println(pathFull);

    // Duyệt qua tất cả các cặp Pair trong danh sách
    for (int i = 0; i < path.size(); i++) {
      int[] pair1 = path.get(i);
      for (int j = i + 1; j < path.size(); j++) { // Bắt đầu từ i+1 để tránh lặp lại cặp
        int[] pair2 = path.get(j);

        // Kiểm tra nếu hai Pair cách nhau 1 đơn vị x hoặc y
        boolean isVertical = pair1[0] == pair2[0] &&
            (Math.abs(pair1[1] - pair2[1]) == 1);
        boolean isHorizontal = pair1[1] == pair2[1] &&
            (Math.abs(pair1[0] - pair2[0]) == 1);

        if (isVertical || isHorizontal) {
          // Tính toán chiều rộng và cao của đường kẻ
          float width = isVertical ? (float) TILE_SIZE / 10 : TILE_SIZE;
          float height = isVertical ? TILE_SIZE : (float) TILE_SIZE / 10;

          // Tính toán vị trí của đường kẻ
          float x = (Math.min(pair1[0], pair2[0]) + 0.5f) * TILE_SIZE + 0;
          float y = (Math.min(pair1[1], pair2[1]) + 0.5f) * TILE_SIZE + 0;

          // Tạo và thêm đường kẻ vào stage
//            Image line = new Image(new TextureRegionDrawable(ui.findRegion("line_red")));
          Image lineMath = new Image(new TextureRegion(uiAtlas.findRegion("line_red")));
          lineMath.setBounds(x, y, width, height);
          addActor(lineMath);
          lineMath.addAction(Actions.sequence(
              Actions.delay(1f),
              Actions.removeActor()
          ));
        }
      }
    }

  }

  private void createLineMatch1(List<int[]> path) {
    int[] after = null;
    for (int[] array : path) {
      if (after == null) {
        after = array;
        continue;
      }
      if (after[0] == array[0]) {
//        miniLine();
      }
    }
  }

  private void miniLine(int gridX, int gridY, int type) {
    Image lineMath = new Image(new TextureRegion(uiAtlas.findRegion("line_red")));
    lineMath.setPosition(animals[gridX][gridY].getX(), animals[gridX][gridY].getY());
    if (type == 0) {
      lineMath.setSize(5, TILE_SIZE);
    } else {
      lineMath.setSize(TILE_SIZE, 5);
    }

    addActor(lineMath);
  }

  public Animal getAnimal(int row, int col) {
    if (row == -1 || col == -1 || row == ROWS || col == COLS)
      return null;
    return animals[row][col];
  }

  // Xáo trộn toàn bộ bàn chơi
  public void shuffle() {
    // Lấy tất cả ô còn lại (visible) vào danh sách
    List<Animal> remainingTiles = new ArrayList<>();
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (animals[row][col].isVisible()) {
          remainingTiles.add(animals[row][col]);
        }
      }
    }

    // Xáo trộn danh sách
    Collections.shuffle(remainingTiles);

    // Đặt lại vào lưới và cập nhật vị trí
    int index = 0;
    for (int row = 0; row < ROWS && index < remainingTiles.size(); row++) {
      for (int col = 0; col < COLS && index < remainingTiles.size(); col++) {
        if (animals[row][col].isVisible()) { // Chỉ điền vào ô trống
          Animal tile = remainingTiles.get(index);
          tile.setPosition(row * TILE_SIZE, col * TILE_SIZE); // Cập nhật vị trí hiển thị
          tile.setGridX(row); // Cập nhật row logic (giả định Tile có setter)
          tile.setGridY(col); // Cập nhật col logic (giả định Tile có setter)
          animals[row][col] = tile;
          animals[row][col].clearListeners();
          addAnimalListener(animals[row][col]);
          index++;
        }
      }
    }
  }

  public int getROWS() {
    return ROWS;
  }

  public int getCOLS() {
    return COLS;
  }

  // Tìm một cặp ô có thể nối được (dùng cho hint)
  public int[] findHint() {
    for (int row1 = 0; row1 < ROWS; row1++) {
      for (int col1 = 0; col1 < COLS; col1++) {
        Animal tile1 = animals[row1][col1];
        if (tile1 != null && tile1.isVisible()) {
          for (int row2 = 0; row2 < ROWS; row2++) {
            for (int col2 = 0; col2 < COLS; col2++) {
              Animal tile2 = animals[row2][col2];
              if (tile2 != null && tile2.isVisible() && !(row1 == row2 && col1 == col2)) {
                if (canMatch(row1, col1, row2, col2)) {
                  return new int[]{row1, col1, row2, col2};
                }
              }
            }
          }
        }
      }
    }
    return null; // Không tìm thấy cặp nào
  }

  // Kiểm tra xem hai ô có thể nối được không
  public boolean canMatch(int row1, int col1, int row2, int col2) {
    List<int[]> path = pathFinder.findPath(row1, col1, row2, col2);
    return path != null;
  }
}
