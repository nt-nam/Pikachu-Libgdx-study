package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public interface PrefData {
  void save(Preferences prefs);
  void load(Preferences prefs);
  void reset();
}