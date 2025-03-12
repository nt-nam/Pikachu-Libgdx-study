package com.mygdx.game.view;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.data.AssetHelper;
import com.mygdx.game.model.Animal;
import com.mygdx.game.model.PathFinder;
import com.mygdx.game.utils.GameConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board extends Group {
  private final int ROWS, COLS;
  private Animal[][] animals;
  private TextureAtlas animalAtlas;
  private PathFinder pathFinder;
  private Animal animalSelect;
  public Board(int rows, int cols) {
    ROWS = rows;
    COLS = cols;
    animals = new Animal[ROWS][COLS];
    pathFinder = new PathFinder(this);
    animalAtlas = AssetHelper.getAnimals();
    initAnimals();
    addStage();
  }

  private void addStage() {

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
        animals[row][col] = new Animal(ani,tileTypes.get(index),row,col,GameConstants.TILE_SIZE);
        addAnimalListener(row,col);
        index++;
        addActor(animals[row][col]);
      }
    }
  }

  private void addAnimalListener( int row, int col) {
    final int finalRow = row;
    final int finalCol = col;
    animals[row][col].addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if(animalSelect == animals[finalRow][finalCol]){
          animalSelect = null;
          return;
        }
        if(animalSelect == null){
          animalSelect = animals[finalRow][finalCol];
          return;
        }
        List<int[]> path = pathFinder.findPath(animalSelect,animals[finalRow][finalCol]);
        if(path != null){
          animalSelect.setVisible(false);
          animals[finalRow][finalCol].setVisible(false);
          animalSelect = null;
        }else {
          animalSelect = animals[finalRow][finalCol];
        }
      }
    });
  }

  public Animal getAnimal(int row, int col){
    if(row == -1|| col == -1||row==ROWS||col==COLS)
      return null;
    return animals[row][col];
  }

  // Xáo trộn toàn bộ bàn chơi
  public void shuffle() {
    // Lấy tất cả ô còn lại (visible) vào danh sách
    List<Animal> remainingTiles = new ArrayList<>();
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (animals[row][col] != null && animals[row][col].isVisible()) {
          remainingTiles.add(animals[row][col]);
        }
      }
    }

    // Xáo trộn danh sách
    Collections.shuffle(remainingTiles, random);

    // Đặt lại vào lưới
    int index = 0;
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (animals[row][col] != null && animals[row][col].isVisible()) {
          if (index < remainingTiles.size()) {
            animals[row][col] = remainingTiles.get(index);
            index++;
          }
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
