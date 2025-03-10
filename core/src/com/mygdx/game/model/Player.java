package com.mygdx.game.model;

import com.mygdx.game.utils.GameConstants;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private int score;
  private int coins;
  private int hints;
  private int shuffles;
  private int undos;
  private int level;
  private int currentSkin;
  private List<Integer> unlockedSkins;

  public Player() {
    this.score = 0;
    this.coins = 0;
    this.hints = GameConstants.DEFAULT_HINTS;
    this.shuffles = GameConstants.DEFAULT_SHUFFLES;
    this.undos = GameConstants.DEFAULT_UNDOS;
    this.level = 1;
    this.currentSkin = GameConstants.DEFAULT_SKIN;
    this.unlockedSkins = new ArrayList<>();
    unlockedSkins.add(currentSkin);
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

  // Mua buffer
  public boolean buyHint() {
    if (spendCoins(GameConstants.HINT_COST)) {
      this.hints++;
      return true;
    }
    return false;
  }

  public boolean buyShuffle() {
    if (spendCoins(GameConstants.SHUFFLE_COST)) {
      this.shuffles++;
      return true;
    }
    return false;
  }

  public boolean buyUndo() {
    if (spendCoins(GameConstants.UNDO_COST)) {
      this.undos++;
      return true;
    }
    return false;
  }

  // Sử dụng buffer
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

  // Chuyển sang cấp tiếp theo
  public void nextLevel() {
    this.level++;
    this.coins += GameConstants.COIN_PER_LEVEL; // Thưởng xu khi hoàn thành cấp
  }

  // Mở khóa skin mới
  public boolean unlockSkin(int skinId, int cost) {
    if (!unlockedSkins.contains(skinId) && spendCoins(cost)) {
      unlockedSkins.add(skinId);
      return true;
    }
    return false;
  }

  // Đổi skin
  public boolean setSkin(int skinId) {
    if (unlockedSkins.contains(skinId)) {
      this.currentSkin = skinId;
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

  public int getCurrentSkin() {
    return currentSkin;
  }

  public List<Integer> getUnlockedSkins() {
    return new ArrayList<>(unlockedSkins); // Trả về bản sao để bảo vệ dữ liệu
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
}
