package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.mygame.pikachu.utils.hud.ShaderImage;
import com.mygame.pikachu.utils.hud.external.ApplyUniform;

@SuppressWarnings("unused")
public class ShaderIB extends AbstractActorBuilder<ShaderImage> {
  public ShaderIB() {
  }

  public static ShaderIB New() {
    return new ShaderIB();
  }

  String region;
  NinePatch ninePatch;
  String shader;
  ApplyUniform uniform;
  boolean active;

  public ShaderIB setRegion(String region) {
    this.ninePatch = null;
    this.region = region;
    return this;
  }

  public ShaderIB setNPatch(String key, int left, int right, int top, int bottom) {
    this.ninePatch = adapter.ninePatch(key, left, right, top, bottom);
    this.region = null;
    return this;
  }

  public ShaderIB setShader(String shader) {
    this.shader = shader;
    return this;
  }

  public ShaderIB setActive(boolean active) {
    this.active = active;
    return this;
  }

  public ShaderIB setUniform(ApplyUniform uniform) {
    this.uniform = uniform;
    return this;
  }

  @Override
  public ShaderImage build() {
    ShaderImage img;
    if (region != null)
      img = new ShaderImage(adapter.getDrawable(region));
    else if (ninePatch != null) {
      img = new ShaderImage(ninePatch);
    } else
      img = new ShaderImage();
    if (w != 0)
      img.setWidth(w);
    if (h != 0)
      img.setHeight(h);
    img.shader = adapter.getShaderProgram(shader);
    img.active = active;
    img.uniform = uniform;
    applyCommonProps(img);
    return img;
  }
}