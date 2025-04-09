package com.mygame.pikachu.model;

import static com.mygame.pikachu.utils.GConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private int score;
  private int coins;
  private int hints;
  private int shuffles;
  private int undos;
  private int level;
  private int currentSkinAniId;
  private int currentSkinUiId;
  private List<Integer> unlockedSkins;
  private String pathAni, pathUi;
  private boolean musicMuted;
  private boolean soundMuted;

  public Player() {
    this.score = 0;
    this.coins = 0;
    this.hints = DEFAULT_HINTS;
    this.shuffles = DEFAULT_SHUFFLES;
    this.undos = DEFAULT_UNDOS;
    this.level = 1;
    this.currentSkinAniId = DEFAULT_SKIN;
    this.currentSkinUiId = DEFAULT_SKIN;
    this.unlockedSkins = new ArrayList<>();
    this.unlockedSkins.add(DEFAULT_SKIN);
    this.musicMuted = false;
    this.soundMuted = false;
  }

  // Lưu dữ liệu vào Preferences
  public void save() {
    Preferences prefs = Gdx.app.getPreferences("PikachuPlayerData");
    prefs.putInteger("score", score);
    prefs.putInteger("coins", coins);
    prefs.putInteger("hints", hints);
    prefs.putInteger("shuffles", shuffles);
    prefs.putInteger("undos", undos);
    prefs.putInteger("level", level);
    prefs.putInteger("currentSkinAniId", currentSkinAniId);
    prefs.putInteger("currentSkinUiId", currentSkinUiId);
    prefs.putBoolean("musicMuted", musicMuted);
    prefs.putBoolean("soundMuted", soundMuted);

    StringBuilder skins = new StringBuilder();
    for (int skinId : unlockedSkins) {
      skins.append(skinId).append(",");
    }
    if (skins.length() > 0) {
      skins.setLength(skins.length() - 1); // Xóa dấu "," cuối cùng
    }
    prefs.putString("unlockedSkins", skins.toString());

    prefs.flush();
  }

  // Tải dữ liệu từ Preferences
  public void load() {
    Preferences prefs = Gdx.app.getPreferences("PikachuPlayerData");
    this.score = prefs.getInteger("score", 0);
    this.coins = prefs.getInteger("coins", 0);
    this.hints = prefs.getInteger("hints", DEFAULT_HINTS);
    this.shuffles = 200+prefs.getInteger("shuffles", DEFAULT_SHUFFLES);
    this.undos = prefs.getInteger("undos", DEFAULT_UNDOS);
    this.level = prefs.getInteger("level", 1);
    this.currentSkinAniId = prefs.getInteger("currentSkinId", DEFAULT_SKIN);
    this.currentSkinUiId = prefs.getInteger("currentUiId", DEFAULT_SKIN);
    this.musicMuted = prefs.getBoolean("musicMuted", false); // Tải trạng thái Music
    this.soundMuted = prefs.getBoolean("soundMuted", false);

    // Tải danh sách unlockedSkins
    this.unlockedSkins = new ArrayList<>();
    String skinsString = prefs.getString("unlockedSkins", String.valueOf(DEFAULT_SKIN));
    if (!skinsString.isEmpty()) {
      String[] skinIds = skinsString.split(",");
      for (String skinId : skinIds) {
        try {
          unlockedSkins.add(Integer.parseInt(skinId));
        } catch (NumberFormatException e) {
          Gdx.app.error("Player", "Error parsing skinId: " + skinId);
        }
      }
    }
    if (unlockedSkins.isEmpty()) {
      unlockedSkins.add(DEFAULT_SKIN);
    }
    pathAni = DEFAULT_ANIMAL + LIST_SKIN_ANIMAL[currentSkinAniId];
    pathUi = DEFAULT_UI + LIST_SKIN_UI[currentSkinUiId];
  }

  // Thêm điểm khi nối thành công
  public void addPoints(int points) {
    this.score += points;
  }

  // Thêm xu khi hoàn thành cấp
  public void addCoins(int coins) {
    this.coins += coins;
  }

  // Tiêu xu
  public boolean spendCoins(int amount) {
    if (this.coins >= amount) {
      this.coins -= amount;
      return true;
    }
    return false;
  }

  public boolean buyHint() {
    if (spendCoins(HINT_COST)) {
      this.hints++;
      return true;
    }
    return false;
  }

  public boolean buyShuffle() {
    if (spendCoins(SHUFFLE_COST)) {
      this.shuffles++;
      return true;
    }
    return false;
  }

  public boolean buyUndo() {
    if (spendCoins(UNDO_COST)) {
      this.undos++;
      return true;
    }
    return false;
  }

  public boolean useHint() {
    if (this.hints > 0) {
      this.hints--;
      return true;
    }
    return false;
  }

  public boolean useShuffle() {
    if (this.shuffles > 0) {
      this.shuffles--;
      return true;
    }
    return false;
  }

  public boolean useUndo() {
    if (this.undos > 0) {
      this.undos--;
      return true;
    }
    return false;
  }

  public void nextLevel() {
    this.level++;
    this.coins += COIN_PER_LEVEL; // Thưởng xu khi hoàn thành cấp
  }

  public boolean unlockSkin(int skinId, int cost) {
    if (!unlockedSkins.contains(skinId) && spendCoins(cost)) {
      unlockedSkins.add(skinId);
      return true;
    }
    return false;
  }

  public boolean setSkin(int skinId) {
    if (unlockedSkins.contains(skinId)) {
      this.currentSkinAniId = skinId;
      return true;
    }
    return false;
  }

  // Getter methods
  public int getScore() {
    return score;
  }

  public int getCoins() {
    return coins;
  }

  public int getHints() {
    return hints;
  }

  public int getShuffles() {
    return shuffles;
  }

  public int getUndos() {
    return undos;
  }

  public int getLevel() {
    return level;
  }

  public int getCurrentSkinAniId() {
    return currentSkinAniId;
  }

  public int getCurrentSkinUiId() {
    return currentSkinUiId;
  }

  public String getPathAni() {
    return pathAni;
  }

  public String getPathUi() {
    return pathUi;
  }

  public List<Integer> getUnlockedSkins() {
    return new ArrayList<>(unlockedSkins);
  }

  public boolean isMusicMuted() {
    return musicMuted;
  }

  public boolean isSoundMuted() {
    return soundMuted;
  }



  // Setter methods (nếu cần thiết)
  public void setScore(int score) {
    this.score = score;
  }

  public void setCoins(int coins) {
    this.coins = coins;
  }

  public void setHints(int hints) {
    this.hints = hints;
  }

  public void setShuffles(int shuffles) {
    this.shuffles = shuffles;
  }

  public void setUndos(int undos) {
    this.undos = undos;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setMusicMuted(boolean muted) {
    this.musicMuted = muted;
    save();
  }

  public void setSoundMuted(boolean muted) {
    this.soundMuted = muted;
    save();
  }
}
