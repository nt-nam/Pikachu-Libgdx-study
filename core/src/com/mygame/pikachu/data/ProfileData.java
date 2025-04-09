package com.mygame.pikachu.data;

import com.badlogic.gdx.Preferences;

public class ProfileData implements PrefData {
  private int id;
  private String character;
  private int skinBlock;
  private int skinBoard;

  public ProfileData() {
    reset();
  }

  @Override
  public void save(Preferences prefs) {
    prefs.putInteger("profile_id", id);
    prefs.putString("profile_character", character);
    prefs.putInteger("profile_skinBlock", skinBlock);
    prefs.putInteger("profile_skinBoard", skinBoard);
    prefs.flush();
  }

  @Override
  public void load(Preferences prefs) {
    skinBlock = prefs.getInteger("profile_id", 0);
    character = prefs.getString("profile_character", "default_char");
    skinBlock = prefs.getInteger("profile_skinBlock", 0);
    skinBlock = prefs.getInteger("profile_skinBoard", 0);
  }

  @Override
  public void reset() {
    id = 0;
    character = "default_char";
    skinBlock = 0;
    skinBoard = 0;
  }

  public String getCharacter() { return character; }
  public void setCharacter(String character) { this.character = character; }
  public int getSkinBlock() { return skinBlock; }
  public void setSkinBlock(int skinBlock) { this.skinBlock = skinBlock; }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSkinBoard() {
    return skinBoard;
  }

  public void setSkinBoard(int skinBoard) {
    this.skinBoard = skinBoard;
  }
}