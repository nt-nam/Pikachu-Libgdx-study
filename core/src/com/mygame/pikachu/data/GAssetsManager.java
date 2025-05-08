package com.mygame.pikachu.data;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygame.pikachu.util.GRes;

import java.util.Iterator;

public class GAssetsManager {
  private static AssetManager am;
  private static String textureAtlas;
  private static String texture;
  public Array<String> tempResLog;



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

  public void loadParticle(String name) {
    am.load(name, ParticleEffect.class);
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
    String path = GRes.getParticlePath(name);
    ParticleEffect effect = (ParticleEffect) getRes(path, ParticleEffect.class);
    if (effect == null) {
      loadParticleEffect(name);
      finishLoading();
      ParticleEffect effect2 = (ParticleEffect) getRes(path, ParticleEffect.class);
      if (effect2 != null) {
        addToLog(path + "---------" + "NParticleEffect.class");
        initParticle(effect2);
        return effect2;
      }
    }
    return effect;
  }
  public Object getRes(String fileName, Class type) {
    checkAddResName(fileName);
    return !am.isLoaded(fileName, type) ? null : am.get(fileName, type);
  }

  public void checkAddResName(String fileName) {
//    if (Gdx.app.getType() == Application.ApplicationType.WebGL || !LOGRES) return;
//
//    if (!resList.contains(fileName, false)) {
//      // Logger.log("new Res " + fileName);
//
//      resList.add(fileName);
//      exportResFile();
//      if (!fileName.contains("i18n") && !fileName.contains(".f3123nt")) {
//        Array<String> list = am.getDependencies(fileName);
//        if (list == null) {
//          this.finishLoading();
//          list = am.getDependencies(fileName);
//        }
//        if (list != null) {
//
//          for (int i = 0; i < list.size; i++) {
//
//            String name = list.get(i);
//
//            checkAddResName(name);
//
//            if (fileName.contains(".tmx")) {
//              String newTsxFile = name.replace(".png", ".tsx");
//              checkAddResName(newTsxFile);
//            }
//          }
//        }
//      }
//
//    }
  }


  public BitmapFont getBitmapFont(String name) {
    return am.get(name);
  }

  public void unload(Object effect) {

  }
  public boolean isLoaded(String filename) {
    return am.isLoaded(filename);
  }
  public void addToLog(String log) {
    //Debug.Log(var0);
//    tempResLog.add(log);

  }
  public String loadParticleEffectAsTextureAtlas(String name) {
    name = GRes.getParticlePath(name);
    ParticleEffectLoader.ParticleEffectParameter param = new ParticleEffectLoader.ParticleEffectParameter();
    param.atlasFile = name + "ack";
    load(name, ParticleEffect.class, param);
    return name;
  }

  public String loadParticleEffectAsTextureAtlas(String name, String atlasName) {
    name = GRes.getParticlePath(name);
    ParticleEffectLoader.ParticleEffectParameter param = new ParticleEffectLoader.ParticleEffectParameter();
    param.atlasFile = GRes.getTextureAtlasPath(atlasName);
    load(name, ParticleEffect.class, param);
    return name;
  }
  private void load(String fileName, Class type, AssetLoaderParameters parameter) {
    am.load(fileName, type, parameter);
  }
  public void initParticle(ParticleEffect particleEff) {
    Iterator<ParticleEmitter> iterator = particleEff.getEmitters().iterator();
    while (iterator.hasNext()) {
      Sprite var1 = iterator.next().getSprites().get(0);  //xxx
      GRes.setTextureFilter(var1.getTexture());
      if (!var1.isFlipY()) {
        // var1.flip(false, true);
      }
    }

  }
  public String loadParticleEffectAsImageDir(String name, FileHandle file) {
    name = GRes.getParticlePath(name);
    ParticleEffectLoader.ParticleEffectParameter param = new ParticleEffectLoader.ParticleEffectParameter();
    param.imagesDir = file;
    load(name, ParticleEffect.class, param);
    return name;
  }
}
