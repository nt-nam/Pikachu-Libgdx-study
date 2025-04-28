package com.mygame.pikachu.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GAssetsManager {
  private static AssetManager am;
  private static String textureAtlas;
  private static String texture;

  private ParticleEffect effect;

  public static String getTextureAtlas() {
    return textureAtlas;
  }

  public static void setTextureAtlas(String textureAtlas) {
    GAssetsManager.textureAtlas = textureAtlas;
  }

  public static String getTexture() {
    return texture;
  }

  public static void setTexture(String texture) {
    GAssetsManager.texture = texture;
  }

  public GAssetsManager() {
    am = new AssetManager();
  }

  public void finishLoading() {
    am.finishLoading();
  }

  public void loadTextureAtlas(String name) {
    am.load(name, TextureAtlas.class);
  }


  public void loadBitmapFont(String name) {
    am.load(name, BitmapFont.class);
  }

  public void loadParticleEffect(String name){
    am.load(name,ParticleEffect.class);
  }

  public void loadSound(String name) {
    am.load(name, Sound.class);
  }

  public static TextureAtlas getTextureAtlas(String name) {
    return am.get(name, TextureAtlas.class);
  }
  public TextureRegion getTextureRegion(String nameAtlas, String name) {
    return getTextureAtlas(nameAtlas).findRegion(name);
  }

  public ParticleEffect getParticleEffect(String name) {
    return am.get(name, ParticleEffect.class);
  }



  public BitmapFont getBitmapFont(String name) {
    return am.get(name);
  }

  public void unload(Object effect) {

  }
}
