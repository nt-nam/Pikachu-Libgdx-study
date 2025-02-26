  package com.mygdx.game.helper;

  import com.badlogic.gdx.assets.AssetManager;
  import com.badlogic.gdx.audio.Sound;
  import com.badlogic.gdx.graphics.g2d.BitmapFont;
  import com.badlogic.gdx.graphics.g2d.TextureAtlas;
  import com.badlogic.gdx.graphics.g2d.TextureRegion;

  public class AssetHelper extends AssetManager {
    private AssetHelper() {
      super();
      loadAssetAll();
    }

    private void loadAssetAll() {
      load("textureAtlas/animals2.atlas", TextureAtlas.class);
      load("textureAtlas/ui.atlas", TextureAtlas.class);
      load("sound/bubble_fall.mp3", Sound.class);
      load("font/arial_uni_30.fnt", BitmapFont.class);
      finishLoading();
    }

    public AssetHelper getInstance() {
      return this;
    }
    public TextureRegion getTextureRegion(String name){
      return get(name);
    }
  }
