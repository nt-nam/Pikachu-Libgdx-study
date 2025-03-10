package com.mygdx.game.view;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.model.Animal;
import com.mygdx.game.utils.GameConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board extends Group {
  private final int ROWS, COLS;
  private Animal[][] animals;
  public Board(int rows, int cols) {
    ROWS = rows;
    COLS = cols;
    animals = new Animal[ROWS][COLS];
    initAnimals();
    addStage();
  }

  private void addStage() {

  }

  private void initAnimals() {
    int totalTiles = ROWS * COLS;
    if (totalTiles % 2 != 0) {
      throw new IllegalArgumentException("Tổng số ô phải là số chẵn!");
    }

    // Tạo danh sách các loại hình thú (mỗi loại xuất hiện 2 lần)
    List<Integer> tileTypes = new ArrayList<>();
    for (int i = 0; i < totalTiles / 2; i++) {
      int type = i % GameConstants.ANIMAL_TYPES + 1; // Từ 1 đến ANIMAL_TYPES
      tileTypes.add(type);
      tileTypes.add(type); // Thêm mỗi loại 2 lần
    }

    // Xáo trộn danh sách
    Collections.shuffle(tileTypes, random);

    // Đặt các ô vào lưới
    int index = 0;
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        animals[row][col] = new Animal(tileTypes.get(index));
        index++;
      }
    }
  }
  public Animal getAnimal(int row, int col){

    return animals[row][col];
  }
  public int getROWS() {
    return ROWS;
  }

  public int getCOLS() {
    return COLS;
  }

}
