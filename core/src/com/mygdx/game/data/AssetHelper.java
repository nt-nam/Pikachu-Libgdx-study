package com.mygdx.game.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetHelper extends AssetManager {
  private static AssetManager am;
  static TextureAtlas UI;
  static TextureAtlas animals;
  static TextureAtlas buttons;
  static String uiPath = "textureAtlas/ui.atlas";
  static String animalsPath = "textureAtlas/animals2.atlas";
  static String buttonsPath = "textureAtlas/btn.atlas";
  public AssetHelper(){
    super();
    if(am == null)
      am = new AssetManager();
    UI = new TextureAtlas();
    animals = new TextureAtlas();
    buttons = new TextureAtlas();
    loadAS();
  }

  private void loadAS() {
    am.load(uiPath, TextureAtlas.class);
    am.load(animalsPath, TextureAtlas.class);
    am.load(buttonsPath, TextureAtlas.class);
    am.finishLoading();
  }

  public static TextureAtlas getUI() {
    if(UI == null){
      UI = am.get(uiPath);
    }
    return UI;
  }

  public static TextureAtlas getAnimals() {
    am.load(animalsPath, TextureAtlas.class);
    am.finishLoading();
    animals = new TextureAtlas();
    animals = am.get(animalsPath);
//    if(animals == null){
//    }
    return animals;
  }

  public static TextureAtlas getButtons() {
    if(buttons == null){
      buttons = am.get(buttonsPath);
    }
    if(buttons.findRegion("1") == null){
      int size = buttons.getRegions().size;
      System.out.println("null region:" + size);
    }
    return buttons;
  }
}
