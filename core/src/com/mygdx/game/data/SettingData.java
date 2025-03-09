package com.mygdx.game.data;

import com.badlogic.gdx.Preferences;

public class SettingData implements PrefData {
  private boolean soundOn;
  private boolean musicOn;
  private boolean vibrateOn;

  public SettingData() {
    reset();
  }

  @Override
  public void save(Preferences prefs) {
    prefs.putBoolean("setting_soundOn", soundOn);
    prefs.putBoolean("setting_musicOn", musicOn);
    prefs.putBoolean("setting_vibrateOn", vibrateOn);
    prefs.flush();
  }

  @Override
  public void load(Preferences prefs) {
    soundOn = prefs.getBoolean("setting_soundOn", true);
    musicOn = prefs.getBoolean("setting_musicOn", true);
    vibrateOn = prefs.getBoolean("setting_vibrateOn", false);
  }

  @Override
  public void reset() {
    soundOn = true;
    musicOn = true;
    vibrateOn = false;
  }

  public boolean isSoundOn() { return soundOn; }
  public void setSoundOn(boolean soundOn) { this.soundOn = soundOn; }
  public boolean isMusicOn() { return musicOn; }
  public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }
  public boolean isVibrateOn() { return vibrateOn; }
  public void setVibrateOn(boolean vibrateOn) { this.vibrateOn = vibrateOn; }
}
