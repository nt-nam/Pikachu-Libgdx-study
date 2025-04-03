package com.mygdx.game.utils.hud.external;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public interface BuilderAdapter {
  Drawable getDrawable(String region);
  TextureRegion getRegion(String region);
  Texture getTexture(String region);
  ShaderProgram getShaderProgram(String region);
  NinePatch ninePatch(String key, int left, int right, int top, int bottom);
  BitmapFont getBitmapFont(String key);
  Group query(String path);
  void debugMsg(String msg);
  boolean debug();
  float worldWidth();
  float worldHeight();
}