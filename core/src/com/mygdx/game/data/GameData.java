package com.mygdx.game.data;

import com.badlogic.gdx.Preferences;

public class GameData implements PrefData {
  private int core;
  private int level;

  public GameData() {
    reset();
  }

  @Override
  public void save(Preferences prefs) {
    prefs.putInteger("game_core", core);
    prefs.putInteger("game_level", level);
    prefs.flush();
  }

  @Override
  public void load(Preferences prefs) {
    core = prefs.getInteger("game_core", 0);
    level = prefs.getInteger("game_level", 1);
  }

  @Override
  public void reset() {
    core = 0;
    level = 1;
  }

  public int getCore() { return core; }
  public void setCore(int core) { this.core = core; }
  public int getLevel() { return level; }
  public void setLevel(int level) { this.level = level; }
}
