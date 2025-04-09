package com.mygame.pikachu.model;

public class Level {
  private int levelId;
  private int rows;
  private int cols;
  private int pairs;
  private float time;
  private int type;

  public Level() {}

  public int getLevelId() { return levelId; }
  public int getRows() { return rows; }
  public int getCols() { return cols; }
  public int getPairs() { return pairs; }
  public float getTime() { return time; }
  public int getType(){return type;}
}
