package com.mygdx.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.model.Player;

import java.util.HashMap;
import java.util.Map;

public class SkinManager {
  private int currentSkinId;                     // ID của skin hiện tại
  private Map<Integer, SkinResources> skinMap;   // Lưu tài nguyên theo skinId
  private Player player;                         // Tham chiếu đến người chơi

  // Class nội bộ để lưu tài nguyên của một skin
  private static class SkinResources {
    TextureAtlas animalAtlas;   // Atlas cho hình thú
    TextureAtlas uiAtlas;       // Atlas cho giao diện (nút)
    Texture background;         // Hình nền

    SkinResources(int skinId) {
      if (skinId >= 0 && skinId < GameConstants.LIST_SKIN_ANIMAL.length) {
        // Tải atlas cho hình thú
        this.animalAtlas = new TextureAtlas(Gdx.files.internal("skins/" + GameConstants.LIST_SKIN_ANIMAL[skinId]));
        // Tải atlas cho UI
        this.uiAtlas = new TextureAtlas(Gdx.files.internal("skins/" + GameConstants.LIST_SKIN_UI[skinId]));
        // Tải background (giả định tên file đồng bộ với skinId)
        this.background = new Texture(Gdx.files.internal("skins/background_" + skinId + ".png"));
      } else {
        throw new IllegalArgumentException("Invalid skinId: " + skinId);
      }
    }

    void dispose() {
      animalAtlas.dispose();
      uiAtlas.dispose();
      background.dispose();
    }
  }

  // Constructor
  public SkinManager(Player player) {
    this.player = player;
    this.currentSkinId = player.getCurrentSkinId(); // Giả định Player dùng skinId
    this.skinMap = new HashMap<>();
    loadSkin(this.currentSkinId); // Tải skin mặc định
  }

  // Tải tài nguyên cho một skin
  private void loadSkin(int skinId) {
    if (!skinMap.containsKey(skinId)) {
      SkinResources resources = new SkinResources(skinId);
      skinMap.put(skinId, resources);
    }
  }

  // Đổi skin
  public void setSkin(int skinId) {
    if (skinId >= 0 && skinId < GameConstants.LIST_SKIN_ANIMAL.length && player.setSkin(skinId)) {
      loadSkin(skinId);
      this.currentSkinId = skinId;
    }
  }

  // Lấy texture cho ô (tile)
  public TextureAtlas.AtlasRegion getTileTexture(int tileType) {
    SkinResources resources = skinMap.get(currentSkinId);
    if (resources != null) {
      return resources.animalAtlas.findRegion("tile_" + tileType);
    }
    return null;
  }

  // Lấy texture nền
  public Texture getBackgroundTexture() {
    SkinResources resources = skinMap.get(currentSkinId);
    if (resources != null) {
      return resources.background;
    }
    return null;
  }

  // Lấy texture cho nút
  public TextureAtlas.AtlasRegion getButtonTexture(String buttonName) {
    SkinResources resources = skinMap.get(currentSkinId);
    if (resources != null) {
      return resources.uiAtlas.findRegion(buttonName);
    }
    return null;
  }

  // Giải phóng tài nguyên
  public void dispose() {
    for (SkinResources resources : skinMap.values()) {
      resources.dispose();
    }
    skinMap.clear();
  }

  // Getter
  public int getCurrentSkinId() {
    return currentSkinId;
  }
}
