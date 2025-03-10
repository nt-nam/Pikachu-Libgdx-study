package com.mygdx.game.view;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.assets.AssetManager;
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
        TextureRegion ani = new TextureRegion(animalAtlas.findRegion("" + tileTypes.get(index)));
        animals[row][col] = new Animal(ani,tileTypes.get(index),row,col,GameConstants.TILE_SIZE);
        final int finalRow = row;
        final int finalCol = col;
        animals[row][col].addListener(new ClickListener(){
          @Override
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("co click"+finalRow+"-"+finalCol);
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
              for (int[] array : path) {
                for (int element : array) {
                  System.out.print(element + " ");
                }
                System.out.println(); // Dòng mới cho mỗi mảng
              }
              animalSelect.setVisible(false);
              animals[finalRow][finalCol].setVisible(false);
              animalSelect = null;
            }else {
              System.out.println("khong path");
              animalSelect = animals[finalRow][finalCol];
            }
          }
        });
        index++;
        addActor(animals[row][col]);
      }
    }
  }
  public Animal getAnimal(int row, int col){
    if(row == -1|| col == -1||row==ROWS||col==COLS)
      return null;
    return animals[row][col];
  }
  public int getROWS() {
    return ROWS;
  }

  public int getCOLS() {
    return COLS;
  }

}
