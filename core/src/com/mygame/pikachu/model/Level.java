package com.mygame.pikachu.model;

import com.badlogic.gdx.utils.JsonValue;

public class Level {
  private int levelId;
  private int rows;
  private int cols;
  private int pairs;
  private float time;
  private int type;
  public int animalTypes;
  public int obstacles;
  public JsonValue moveRules;
  public JsonValue connectionRules;

  public Level() {}

  public int getLevelId() { return levelId; }
  public int getRows() { return rows; }
  public int getCols() { return cols; }
  public int getPairs() { return pairs; }
  public float getTime() { return time; }
  public int getType(){return type;}

  public int getAnimalTypes() {return animalTypes;}

  public int getObstacles() {return obstacles;}

  public JsonValue getMoveRules() {return moveRules;}

  public JsonValue getConnectionRules() {
    return connectionRules;
  }
}
