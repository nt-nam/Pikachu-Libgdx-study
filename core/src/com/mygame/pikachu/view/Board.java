package com.mygame.pikachu.view;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygame.pikachu.utils.GConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.model.Animal;
import com.mygame.pikachu.model.Level;
import com.mygame.pikachu.model.PathFinder;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.actions.FallingAction;
import com.mygame.pikachu.utils.actions.RandomPathAction;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.Button;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.view.ui.BaseUI;
import com.mygame.pikachu.view.ui.PopupUI;
import com.mygame.pikachu.view.ui.WinUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Board extends Group {
  private Animal[][] animals;
  private Animal animalSelect;
  private PathFinder pathFinder;
  private Image lineSelect;
  private Array<Animal> visibleActors = new Array<>();

  private int ROWS, COLS;
  private int pairAni;
  private int matchedPairs;
  private int totalPairs;
  private int type;

  public Board() {
    pairAni = GConstants.ANIMAL_TYPES;
  }

  public void setNew(Level lv) {
    System.out.println("[Board]: Set new board: " + lv.getLevelId());
    clear();
    boolean f = !(GMain.stage().getWidth() > GMain.stage().getHeight());
    ROWS = f ? lv.getRows() : lv.getCols();
    COLS = f ? lv.getCols() : lv.getRows();
    pairAni = lv.getPairs();
    matchedPairs = 0;
    totalPairs = (ROWS * COLS) / 2;
    type = lv.getType();
    setSize(ROWS * TILE_SIZE, COLS * TILE_SIZE);
    animals = new Animal[ROWS][COLS];
    pathFinder = new PathFinder(this);
    initAnimals();
    createLineSelect();
    updateListAnimal();
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
    int index = 0;
    for (int row = 0; row < ROWS; row++) {
      for (int col = COLS - 1; col >= 0; col--) {
        animals[row][col] = new Animal(tileTypes.get(index), row, col, GConstants.TILE_SIZE);
        addAnimalListener(animals[row][col]);
        index++;
        addActor(animals[row][col]);
      }
    }
  }

  private void createLineSelect() {
    GAssetsManager.setTextureAtlas(DEFAULT_ATLAS_CROSS);
    lineSelect = IB.New().drawable("boder").origin(AL.c).size(animals[0][0].getWidth(), animals[0][0].getHeight()).debug(false).build();

    lineSelect.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        lineSelect.remove();
        animalSelect = null;
      }
    });
  }

  private void setLineSelect(Animal animal) {
    animalSelect = animal;
    if (animalSelect != null) {
      lineSelect.setPosition(animalSelect.getX(), animalSelect.getY());
      addActor(lineSelect);
      return;
    }
    lineSelect.remove();
  }

  public void softAni() {
    getChildren().sort(new Comparator<Actor>() {
      @Override
      public int compare(Actor o1, Actor o2) {
        if (o1 instanceof Animal && o2 instanceof Animal) {
          if (o1.getX() < o2.getX() && o1.getY() < o2.getY()) {
            return 1;
          } else if (o1.getX() > o2.getX() && o1.getY() > o2.getY()) {
            return -1;
          }
          if (o1.getX() < o2.getX()) {
            return -1;
          } else if (o1.getX() > o2.getX()) {
            return 1;
          }
          if (o1.getY() > o2.getY()) {
            return -1;
          } else if (o1.getY() < o2.getY()) {
            return 1;
          }
        }
        return 0;
      }
    });
  }

  private void addAnimalListener(Animal animal) {
    animal.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        handleAnimalClick(animal);
      }
    });
  }

  private void handleAnimalClick(Animal animal) {
    System.out.println("Debug grid animal: [" + animal.getGridX() + ", " + animal.getGridY() + "]");

    if (animalSelect == animal) {
      deselectAnimal();
      return;
    }

    if (animalSelect == null) {
      selectAnimal(animal);
      return;
    }

    List<int[]> path = pathFinder.findPath(animalSelect, animal);
    if (path != null) {
      handleSuccessfulMatch(path, animal);
    } else {
      selectAnimal(animal);
    }

    checkCanMatch();
  }

  public void checkCanMatch() {
    Timer.schedule(new Timer.Task() {
      @Override
      public void run() {
        while (findHint() == null && !isComplete()) {
          System.out.println("loop shuffle");
          shuffle();

        }
      }
    }, 0.53f);

  }

  private void selectAnimal(Animal animal) {
    animalSelect = animal;
    lineSelect.setPosition(animal.getX(), animal.getY());
    addActor(lineSelect);
  }

 void deselectAnimal() {
    animalSelect = null;
    lineSelect.remove();
  }

  private void handleSuccessfulMatch(List<int[]> path, Animal secondAnimal) {
    debugPathMatch(path);
    createLineMatch(path);

    addScoreStars(path);

    fadeOutAnimal(animalSelect, 0.1f);
    fadeOutAnimal(secondAnimal, 0.1f);

    matchedPairs++;
    deselectAnimal();

    if (matchedPairs >= totalPairs) {
      handleGameCompletion();
    }
  }

  private void handleGameCompletion() {
    System.out.println("Game Completed! All pairs matched.");
    BaseUI baseUI;
    baseUI = new WinUI(GMain.instance());
    baseUI.show();
    GMain.hud().query("playMG", MapGroup.class).addActor(baseUI);
  }

  /**
   * Fades out an animal and sets it to invisible.
   */
  private void fadeOutAnimal(Animal animal, float duration) {
    animal.addAction(
        Actions.sequence(
            Actions.fadeOut(duration),
            Actions.run(() -> animal.setVisible(false))
        )
    );
  }

  /**
   * Debugs the path by printing it to the console.
   */
  private void debugPathMatch(List<int[]> path) {
    StringBuilder pathFull = new StringBuilder("[PathFinder] Path connect: ");
    for (int[] pair : path) {
      pathFull.append("[").append(pair[0]).append(",").append(pair[1]).append("] ");
    }
    System.out.println(pathFull);
  }

  /**
   * Creates visual lines to show the connection path between matched animals.
   */
  private void createLineMatch(List<int[]> path) {
    GAssetsManager.setTextureAtlas(DEFAULT_ATLAS_PLAY);

    for (int i = 0; i < path.size() - 1; i++) {
      int[] pair1 = path.get(i);
      int[] pair2 = path.get(i + 1);

      // Determine if the line is vertical or horizontal
      boolean isVertical = pair1[0] == pair2[0] && Math.abs(pair1[1] - pair2[1]) == 1;
      boolean isHorizontal = pair1[1] == pair2[1] && Math.abs(pair1[0] - pair2[0]) == 1;

      // Skip if not a valid line segment
      if (!isVertical && !isHorizontal) {
        continue;
      }

      // Calculate position for the line
      float x = (Math.min(pair1[0], pair2[0]) + 0.5f) * TILE_SIZE;
      float y = (Math.min(pair1[1], pair2[1]) + 0.5f) * TILE_SIZE;

      // Create the line image
      Image lineMatch = isVertical
          ? IB.New().drawable("laser1").size(25, TILE_SIZE).pos(x, y + 12).build()
          : IB.New().drawable("laser1_2").size(TILE_SIZE, 25).pos(x + 12, y).build();

      // Add the line to the stage and animate its fade-out
      addActor(lineMatch);
      lineMatch.addAction(
          Actions.sequence(
              Actions.fadeOut(0.3f),
              Actions.run(() -> lineMatch.remove())
          )
      );
    }

    // Schedule level progression after animations
    Timer.schedule(new Timer.Task() {
      @Override
      public void run() {
        moveWithLevel();
      }
    }, 0.23f);
  }

  private void addScoreStars(List<int[]> path) {
    for (int i = 0; i < path.size(); i++) {
      int[] pair = path.get(i);
      starAt(pair[0] * TILE_SIZE, pair[1] * TILE_SIZE);
    }
  }

  private void starAt(float x, float y) {
    GAssetsManager.setTextureAtlas(DEFAULT_ATLAS_NEWPIKA);
    Image star6 = IB.New().drawable("star6").size(TILE_SIZE, TILE_SIZE).parent(this).pos(x, y, AL.bl).build();
    Button origin = GMain.hud().query("playMG/score/starOrigin", Button.class);
    Label scoreLabel = GMain.hud().query("playMG/score/scoreLabel", Label.class);
    Vector2 posMoveBy = absPos(origin, -40, -55, AL.c);

    star6.addAction(
        Actions.parallel(
            Actions.rotateBy(360, 1.3f),
            Actions.sequence(

                Actions.delay(0.3f),
                Actions.moveTo(posMoveBy.x, posMoveBy.y, 0.5f),
                Actions.fadeOut(0.5f),
                Actions.run(() -> {
                  GMain.player().plusScore(1);
                  scoreLabel.setText(GMain.player().getScore() + "");
                }),
                Actions.removeActor()
            )
        )
    );
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
//          tile.setPosition(row * TILE_SIZE, col * TILE_SIZE);
          tile.addAction(Actions.sequence(Actions.parallel(
//              Actions.moveTo(ROWS/2 * TILE_SIZE, COLS/2 * TILE_SIZE, 1),
              Actions.rotateTo(360,1f),
              Actions.moveTo(row * TILE_SIZE, col * TILE_SIZE, 1f)
//              ,new RandomPathAction(row * TILE_SIZE, col * TILE_SIZE, 1, 20)
//              , Actions.rotateTo(0)
          ),Actions.rotateTo(0)));
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
    softAni();
  }

  public void boom() {

  }

  public void showAnimationHint() {
    int[] grid = findHint();
    Color color = Color.RED;
    animals[grid[0]][grid[1]].addAction(Actions.sequence(
        Actions.color(color, 0.5f),
        Actions.color(Color.WHITE, 0.5f),
        Actions.color(color, 0.5f),
        Actions.color(Color.WHITE, 0.5f),
        Actions.color(color, 0.5f),
        Actions.color(Color.WHITE, 0.5f)
    ));
    animals[grid[2]][grid[3]].addAction(Actions.sequence(
        Actions.color(color, 0.5f),
        Actions.color(Color.WHITE, 0.5f),
        Actions.color(color, 0.5f),
        Actions.color(Color.WHITE, 0.5f),
        Actions.color(color, 0.5f),
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
    Timer.schedule(new Timer.Task() {
      @Override
      public void run() {
        softAni();
      }
    }, 0f, 0.001f);
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
    softAni();
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
    softAni();
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
    softAni();
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
    softAni();
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

  void debugMatrixIdAnimal() {
    for (int i = 0; i < ROWS; i++) {
      System.out.println(" ");
      for (int j = 0; j < COLS; j++) {
        System.out.print((animals[i][j].getId()) + "-");
      }
    }
    for (Actor actor : this.getStage().getActors()) {
      if (actor instanceof Animal) {
        actor.setZIndex(((Animal) actor).getGridX() + ((Animal) actor).getGridY() + 1);
      }
    }
  }

  public void updateLineSelect() {
    if (animalSelect != null) {
      lineSelect.setPosition(animalSelect.getX(), animalSelect.getY());
    }
  }

  public boolean levelCompleted() {
    return matchedPairs == totalPairs;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    updateLineSelect();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
  }

  public static Vector2 absPos(Actor c, float x, float y, int align) {
    return c.localToStageCoordinates(new Vector2(c.getX(align) + x - c.getX(), c.getY(align) + y - c.getY()));
  }

  public static Vector2 absPos(Actor c, int align) {
    return c.localToStageCoordinates(new Vector2(c.getX(align) - c.getX(), c.getY(align) - c.getY()));
  }

  public void updateListAnimal() {
    visibleActors.clear();
    // Duyệt qua mảng 2 chiều
    for (int i = 0; i < animals.length; i++) {
      for (int j = 0; j < animals[i].length; j++) {
        Animal actor = animals[i][j];
        if (actor != null && actor.isVisible()) {
          visibleActors.add(actor);
        }
      }
    }
  }

  public Vector2[] getRandomVisibleActor() {
    // Tạo danh sách để lưu các Actor thỏa mãn điều kiện
    Vector2[] vt = new Vector2[2];
    // Nếu không có Actor nào thỏa mãn, trả về null
    if (visibleActors.size == 0) {
      return null;
    }
    int removeIndex = MathUtils.random(0, visibleActors.size - 1);
    Animal a0 = visibleActors.get(removeIndex);
    visibleActors.removeIndex(removeIndex);
    Animal a1 = animals[0][0];
    for (int i = 0; i < animals.length; i++) {
      for (int j = 0; j < animals[i].length; j++) {
        Animal actor = animals[i][j];
        if (actor != null && actor.isVisible() && actor.getId() == a0.getId() && actor != a0) {
          a1 = animals[i][j];
        }
      }
    }
    a0.addAction(Actions.sequence(
        Actions.delay(1.3f)
        , Actions.run(() -> {
          starAt(a0.getX(), a0.getY());
        })
//        ,new FallingAction(-100, 500,false)
        , Actions.scaleTo(0, 0, 0.5f)
        , Actions.run(() -> {
          a0.setVisible(false);
          System.out.println(a0.getGridX() + "_" + a0.getGridY() + " ");
        })
        , Actions.scaleTo(1, 1)
    ));
    Animal finalA = a1;
    assert a1 != null;
    a1.addAction(Actions.sequence(
        Actions.delay(1.3f)
        , Actions.run(() -> {
          starAt(finalA.getX(), finalA.getY());
        })
//        ,new FallingAction(-100, 500,false)
        , Actions.scaleTo(0, 0, 0.5f)
        , Actions.run(() -> {
          finalA.setVisible(false);
          System.out.println(finalA.getGridX() + "_" + finalA.getGridY() + "; ");
        })
        , Actions.scaleTo(1, 1)
    ));
    vt[0] = absPos(a0, AL.c);
    vt[1] = absPos(a1, AL.c);

    return vt;
  }

  public boolean isComplete() {
    return matchedPairs == totalPairs;
  }
}