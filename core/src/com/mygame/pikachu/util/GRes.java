package com.mygame.pikachu.util;

import com.badlogic.gdx.graphics.Texture;

public abstract class GRes {
  public static final String FILE_PATH_PARTICLE = "particle/";
  public static final String FILE_PATH_TEXTURE_ATLAS = "textureAtlas/";

  public static Texture.TextureFilter magFilter;
  public static Texture.TextureFilter minFilter;

  static {
    minFilter = Texture.TextureFilter.Linear;
    magFilter = Texture.TextureFilter.Linear;
  }

  public static String getParticlePath(String name) {
    return FILE_PATH_PARTICLE + name;//GStrRes.getResName(var0);
  }

  public static void setTextureFilter(Texture texture) {
    texture.setFilter(minFilter, magFilter);
  }

  public static String getTextureAtlasPath(String name) {
    return FILE_PATH_TEXTURE_ATLAS + name + ".atlas";//GStrRes.getResName(var0);
  }
}
