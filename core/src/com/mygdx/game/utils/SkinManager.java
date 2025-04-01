package com.mygdx.game.utils;


import static com.mygdx.game.utils.GameConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.model.Player;

import java.util.HashMap;
import java.util.Map;

public class SkinManager {
  private int currentSkinAniId;
  private int currentSkinUiId;
  private Map<Integer, SkinResources> skinMap;   // Lưu tài nguyên theo skinId
  private Player player;                         // Tham chiếu đến người chơi
  private String idUI = "";

  public Image createImageUI(String name) {
    return new Image(new Texture(Gdx.files.internal("images/ui/" + name + ".png")));
  }

  // Class nội bộ để lưu tài nguyên của một skin
  private static class SkinResources {
    TextureAtlas animalAtlas;
    TextureAtlas uiAtlas;
    Texture background;

    SkinResources(int skinId) {
      if (skinId >= 0 && skinId < LIST_SKIN_ANIMAL.length) {
        this.animalAtlas = new TextureAtlas(Gdx.files.internal(DEFAULT_ANIMAL + LIST_SKIN_ANIMAL[skinId]));
        this.uiAtlas = new TextureAtlas(Gdx.files.internal(DEFAULT_UI + LIST_SKIN_UI[skinId]));
        this.background = new Texture(Gdx.files.internal(DEFAULT_BACKGROUND_PATH));
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
    this.currentSkinAniId = player.getCurrentSkinAniId();
    this.currentSkinUiId = player.getCurrentSkinUiId();
    this.skinMap = new HashMap<>();
    loadSkin(this.currentSkinAniId);
    loadSkin(this.currentSkinUiId);
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
    if (skinId >= 0 && skinId < LIST_SKIN_ANIMAL.length && player.setSkin(skinId)) {
      loadSkin(skinId);
      this.currentSkinAniId = skinId;
    }
  }

  // Lấy texture cho ô (tile)
  public TextureAtlas.AtlasRegion getAnimalTexture(int tileType) {
    SkinResources resources = skinMap.get(currentSkinAniId);
    if (resources != null) {
      return resources.animalAtlas.findRegion("tile_" + tileType);
    }
    return null;
  }

  // Lấy texture nền
  public Texture getBackgroundTexture() {
    SkinResources resources = skinMap.get(currentSkinAniId);
    if (resources != null) {
      return resources.background;
    }
    return null;
  }

  // Lấy texture cho nút
  public TextureAtlas.AtlasRegion getButtonTextureUIAtlas(String buttonName) {
    SkinResources resources = skinMap.get(currentSkinUiId);
    if (resources != null) {
      return resources.uiAtlas.findRegion(buttonName);
    }
    return null;
  }

  public TextureRegionDrawable getDrawable(String name) {
    Texture texture = new Texture(Gdx.files.internal("images/" + name + ".png"));
    return new TextureRegionDrawable(texture);
  }

  // Giải phóng tài nguyên
  public void dispose() {
    for (SkinResources resources : skinMap.values()) {
      resources.dispose();
    }
    skinMap.clear();
  }

  // Getter
  public int getCurrentSkinAniId() {
    return currentSkinAniId;
  }
}
