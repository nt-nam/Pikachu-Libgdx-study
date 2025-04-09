package com.mygame.pikachu.data;

import com.badlogic.gdx.Preferences;

public interface PrefData {
  void save(Preferences prefs);
  void load(Preferences prefs);
  void reset();
}