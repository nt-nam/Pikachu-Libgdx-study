package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.model.Level;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
  private List<Level> levels;

  public LevelManager() {
    loadLevels();
  }

  private void loadLevels() {
    Json json = new Json();
    String jsonString = Gdx.files.internal("level/level.json").readString();
    levels = json.fromJson(ArrayList.class, Level.class, jsonString);
  }

  public Level getLevel(int levelId) {
    for (Level level : levels) {
      if (level.getLevelId() == levelId) {
        return level;
      }
    }
    Gdx.app.error("LevelManager", "Level not found: " + levelId);
    return levels.get(0); // Mặc định trả về cấp 1
  }

  public int getMaxLevel() {
    return levels.size();
  }
}
