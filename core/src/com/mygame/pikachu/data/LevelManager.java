package com.mygame.pikachu.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygame.pikachu.model.Level;


public class LevelManager {
  private Array<Level> levels;

  public LevelManager() {
    loadLevels();
  }

  private void loadLevels() {
    Json json = new Json();
    String jsonString = Gdx.files.internal("level/level.json").readString();
    levels = json.fromJson(Array.class, Level.class, jsonString);
  }

  public Level getLevel(int levelId) {
    for (Level level : levels) {
      if (level.getLevelId() == levelId) {
        return level;
      }
    }
    Gdx.app.error("LevelManager", "Level not found: " + levelId);
    return levels.get(0);
  }

  public int getMaxLevel() {
    return levels.size;
  }
}
