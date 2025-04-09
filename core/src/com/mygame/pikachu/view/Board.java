package com.mygame.pikachu.view;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygame.pikachu.utils.GConstants.DEFAULT_ATLAS_PLAY;
import static com.mygame.pikachu.utils.GConstants.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.model.Animal;
import com.mygame.pikachu.model.Level;
import com.mygame.pikachu.model.PathFinder;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.builders.IB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board extends Group {
  private Animal[][] animals;
  private Animal animalSelect;
  private PathFinder pathFinder;
  private Image lineSelect;

  private int ROWS, COLS;
  private int pairAni;
  private int matchedPairs;
  private int totalPairs;
  private int type;

  public Board() {
    pairAni = GConstants.ANIMAL_TYPES;
  }

  public void setNew(Level lv) {
    System.out.println("[Board]: set new board");
    clear();
    ROWS = lv.getRows();
    COLS = lv.getCols();
    pairAni = lv.getPairs();
    matchedPairs = 0;
    totalPairs = (ROWS * COLS) / 2;
    type = lv.getType();
    setSize(ROWS * TILE_SIZE, COLS * TILE_SIZE);
    animals = new Animal[ROWS][COLS];
    pathFinder = new PathFinder(this);
    initAnimals();
    createLineSelect();
  }

  private void initAnimals() {
    animalSelect = null;
    int totalTiles = ROWS * COLS;
    if (totalTiles % 2 != 0) {
      throw new IllegalArgumentException("Tổng số ô trong board phải là số chẵn!");
    }

    List<Integer> tileTypes = new ArrayList<>();
    for (int i = 0; i < totalTiles / 2; i++) {
      int type = i % pairAni + 1;
      tileTypes.add(type);
      tileTypes.add(type);
    }

    Collections.shuffle(tileTypes, random);
    TextureRegion cxl = GMain.getAssetHelper().getTextureRegion(GConstants.DEFAULT_ATLAS_ANIMALS, "cucxilau1");
    int index = 0;
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
//        TextureRegion ani = GMain.getAssetHelper().getTextureRegion(GConstants.DEFAULT_ATLAS_ANIMAL,tileTypes.get(index)+"");
//        TextureRegion ani = new TextureRegion(animalAtlas.findRegion("" + tileTypes.get(index)));
        TextureRegion ani = new TextureRegion(new Texture("atlas/animals/" + tileTypes.get(index) + ".png"));
        animals[row][col] = new Animal(ani, cxl, tileTypes.get(index), row, col, GConstants.TILE_SIZE);
        addAnimalListener(animals[row][col]);
        index++;
        addActor(animals[row][col]);
      }
    }
  }

  private void createLineSelect() {

    lineSelect = IB.New().texture(new Texture("atlas/animals/selected.png")).size(animals[0][0].getWidth(), animals[0][0].getHeight()).scale(1.2f).build();
    lineSelect.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        lineSelect.setVisible(false);
        animalSelect = null;
      }
    });
    lineSelect.setVisible(false);
    addActor(lineSelect);

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

    animal.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        System.out.println(animal.getGridX() + "  " + animal.getGridY());
        if (animalSelect == animals[animal.getGridX()][animal.getGridY()]) {
          setBorder(null);
          return;
        }

        if (animalSelect == null) {
          setBorder(animals[animal.getGridX()][animal.getGridY()]);
          return;
        }

        List<int[]> path = pathFinder.findPath(animalSelect, animals[animal.getGridX()][animal.getGridY()]);
        if (path != null) {
          createLineMatch(path);
          setVisibleAnimal(animalSelect, 0.2f);
          setVisibleAnimal(animals[animal.getGridX()][animal.getGridY()], 0.2f);
          setBorder(null);
          checkCanMatch();
        } else {
          setBorder(animals[animal.getGridX()][animal.getGridY()]);
        }

        checkCanMatch();
      }

      private void setVisibleAnimal(Animal animalA, float v) {
        final Animal a = animalA;
        a.addAction(
            Actions.sequence(
                Actions.fadeOut(v),
                Actions.run(() -> {
                  a.setVisible(false);
                })
            )
        );
      }
    });
  }

  public void checkCanMatch() {
    System.out.println("check can match");
    Timer.schedule(new Timer.Task() {
      @Override
      public void run() {
        while (findHint() == null) {
          System.out.println("loop shuffle");
          shuffle();
        }
      }
    }, 0.53f);

  }

  private void createLineMatch(List<int[]> path) {

    StringBuilder pathFull = new StringBuilder("[PathFinder] Path connect: ");

    for (int[] pair : path) {
      pathFull.append("- [").append(pair[0]).append(",").append(pair[1]).append("] ");
    }
    System.out.println(pathFull);

    for (int i = 0; i < path.size(); i++) {
      int[] pair1 = path.get(i);
      for (int j = i + 1; j < path.size(); j++) {
        int[] pair2 = path.get(j);

        boolean isVertical = pair1[0] == pair2[0] &&
            (Math.abs(pair1[1] - pair2[1]) == 1);
        boolean isHorizontal = pair1[1] == pair2[1] &&
            (Math.abs(pair1[0] - pair2[0]) == 1);
        GAssetsManager.setTextureAtlas(DEFAULT_ATLAS_PLAY);
        Image lineMath = null;
        float x = (Math.min(pair1[0], pair2[0]) + 0.5f) * TILE_SIZE ;
        float y = (Math.min(pair1[1], pair2[1]) + 0.5f) * TILE_SIZE ;

        if (isVertical) {
          lineMath = IB.New().drawable("laser1").size(25,TILE_SIZE).pos(x, y+12).build();
        }
        if (isHorizontal) {
          lineMath = IB.New().drawable("laser1_2").size(TILE_SIZE,25).pos(x+12, y).build();
        }

        if (isVertical || isHorizontal) {
          addActor(lineMath);
          Image finalLineMath = lineMath;
          lineMath.addAction(
              Actions.sequence(
                  Actions.fadeOut(0.4f),
                  Actions.run(() -> {
                    finalLineMath.remove();
                    removeActor(finalLineMath);
                  })
              )
          );
        }
      }
    }
    Timer.schedule(new Timer.Task() {
      @Override
      public void run() {
        moveWithLevel();
      }
    }, 0.53f);

  }

  public Animal getAnimal(int row, int col) {
    if (row < 0 || col < 0 || row >= ROWS || col >= COLS) return null;
    return animals[row][col];
  }

  public void undo() {
  }

  public void shuffle() {
    List<Animal> remainingTiles = new ArrayList<>();
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (animals[row][col].isVisible()) {
          remainingTiles.add(animals[row][col]);
        }
      }
    }

    Collections.shuffle(remainingTiles);

    int index = 0;
    for (int row = 0; row < ROWS && index < remainingTiles.size(); row++) {
      for (int col = 0; col < COLS && index < remainingTiles.size(); col++) {
        if (animals[row][col].isVisible()) {
          Animal tile = remainingTiles.get(index);
          tile.setPosition(row * TILE_SIZE, col * TILE_SIZE);
          tile.setGridX(row);
          tile.setGridY(col);
          animals[row][col] = tile;
          animals[row][col].clearListeners();
          addAnimalListener(animals[row][col]);
          index++;
        }
      }
    }
    pathFinder.setBoard(this);
  }

  public void showAnimationHint() {
    int[] grid = findHint();

    animals[grid[0]][grid[1]].addAction(Actions.sequence(
        Actions.color(Color.YELLOW, 0.5f),
        Actions.color(Color.WHITE, 0.5f)
    ));
    animals[grid[2]][grid[3]].addAction(Actions.sequence(
        Actions.color(Color.YELLOW, 0.5f),
        Actions.color(Color.WHITE, 0.5f)
    ));

    Gdx.app.log("ButtonFactory", "Hint used: (" + grid[0] + "," + grid[1] + ") - (" + grid[2] + "," + grid[3] + ")");
  }

  public int[] findHint() {
    for (int row1 = 0; row1 < ROWS; row1++) {
      for (int col1 = 0; col1 < COLS; col1++) {
        Animal tile1 = animals[row1][col1];
        if (tile1 != null && tile1.isVisible()) {
          for (int row2 = 0; row2 < ROWS; row2++) {
            for (int col2 = 0; col2 < COLS; col2++) {
              Animal tile2 = animals[row2][col2];
              if (tile2 != null && tile2.isVisible() && !(row1 == row2 && col1 == col2) && tile1.getId() == tile2.getId()) {
                if (canMatch(row1, col1, row2, col2)) {
                  return new int[]{row1, col1, row2, col2};
                }
              }
            }
          }
        }
      }
    }
    return null;
  }

  public boolean canMatch(int row1, int col1, int row2, int col2) {
    List<int[]> path = pathFinder.findPath(row1, col1, row2, col2);
    return path != null;
  }

  public PathFinder getPathFinder() {
    return pathFinder;
  }


  public int getROWS() {
    return ROWS;
  }

  public int getCOLS() {
    return COLS;
  }

  public void moveWithLevel() {
    switch (type) {
      case 1:
        moveLeft();
        break;
      case 2:
        moveRight();
        break;
      case 3:
        moveTop();
        break;
      case 4:
        moveBottom();
        break;
      default:

    }

  }

  public void moveTop() {
    for (int m = COLS - 1; m >= 0; m--) {
      for (int n = 0; n < ROWS; n++) {
        if (!animals[n][m].isVisible()) {
          boolean flag = true;
          for (int k = m - 1; k >= 0 && flag; k--) {
            if (animals[n][k].isVisible()) {
              moveAniUpdate(n, m, n, k);
              flag = false;
            }
          }
        }
      }
    }
    showIdAnimal();
  }

  public void moveBottom() {
    for (int m = 0; m < COLS; m++) {
      for (int n = 0; n < ROWS; n++) {
        if (!animals[n][m].isVisible()) {
          boolean flag = true;
          for (int k = m + 1; k < COLS && flag; k++) {
            if (animals[n][k].isVisible()) {
              moveAniUpdate(n, m, n, k);
              flag = false;
            }
          }
        }
      }
    }
    showIdAnimal();
  }

  public void moveLeft() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        if (!animals[i][j].isVisible()) {
          boolean flag = true;
          for (int k = i + 1; k < ROWS && flag; k++) {
            if (animals[k][j].isVisible()) {
              moveAniUpdate(i, j, k, j);
              flag = false;
            }
          }
        }
      }
    }
    showIdAnimal();
  }

  public void moveRight() {
    for (int i = ROWS - 1; i >= 0; i--) {
      for (int j = 0; j < COLS; j++) {
        if (!animals[i][j].isVisible()) {
          boolean flag = true;
          for (int k = i - 1; k >= 0 && flag; k--) {
            if (animals[k][j].isVisible()) {
              moveAniUpdate(i, j, k, j);
              flag = false;
            }
          }
        }
      }
    }
    showIdAnimal();
  }

  private void moveAniUpdate(int x1, int y1, int x2, int y2) {

    if (x1 < 0 || x1 >= animals.length || y1 < 0 || y1 >= animals[0].length ||
        x2 < 0 || x2 >= animals.length || y2 < 0 || y2 >= animals[0].length) {
      return;
    }

    Animal animal1 = animals[x1][y1];
    Animal animal2 = animals[x2][y2];

    animal1.setGridX(x2);
    animal1.setGridY(y2);

    animal2.setGridX(x1);
    animal2.setGridY(y1);

    animals[x1][y1] = animal2;
    animals[x2][y2] = animal1;

    animals[x1][y1].moveTo();
    animals[x2][y2].moveTo();
  }

  void showIdAnimal() {
    for (int i = 0; i < ROWS; i++) {
      System.out.println(" ");
      for (int j = 0; j < COLS; j++) {
        System.out.print((animals[i][j].getId()) + "-");
      }
    }
  }

  public void updateLineSelect() {
    if (animalSelect != null) {
      lineSelect.setPosition(animalSelect.getX(), animalSelect.getY());
    }
  }
}