package com.mygdx.game.data;

import static com.mygdx.game.utils.GameConstants.DEFAULT_UI;
import static com.mygdx.game.utils.GameConstants.LIST_SKIN_UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetHelper extends AssetManager {
  private static AssetManager am;
  static TextureAtlas UI;
  static TextureAtlas animals;
  static TextureAtlas buttons;
  static BitmapFont bmf;

  static String uiPath = DEFAULT_UI + LIST_SKIN_UI[0];
  static String animalsPath = "textureAtlas/ani/ani0.atlas";
  static String buttonsPath = "textureAtlas/ui/btn0.atlas";

  public AssetHelper(){
    super();
    if(am == null){
      am = new AssetManager();
    }
    UI = new TextureAtlas();
    animals = new TextureAtlas();
    buttons = new TextureAtlas();
    bmf = new BitmapFont(Gdx.files.internal("font/arial_uni_30.fnt"));
    loadAS();
  }

  private static void loadAS() {
    am.load(uiPath, TextureAtlas.class);
    am.load(animalsPath, TextureAtlas.class);
    am.load(buttonsPath, TextureAtlas.class);
    am.finishLoading();
  }

  public static TextureAtlas getUI() {
    am.load(uiPath, TextureAtlas.class);
    am.finishLoading();
    UI = new TextureAtlas();
    UI = am.get(uiPath);
    return UI;
  }

  public static TextureAtlas getAnimals() {
    am.load(animalsPath, TextureAtlas.class);
    am.finishLoading();
    animals = new TextureAtlas();
    animals = am.get(animalsPath);
    return animals;
  }

  public static BitmapFont getBmf() {
    return bmf;
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
