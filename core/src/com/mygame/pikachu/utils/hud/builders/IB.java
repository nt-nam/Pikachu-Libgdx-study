package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygame.pikachu.screen.UiPopup;

@SuppressWarnings("unused")
public class IB extends AbstractActorBuilder<Image> {
  public IB() {
  }

  public static IB New() {
    return new IB();
  }

  private String drawable;
  private Texture texture;
  private TextureRegion textureRegion;
  private NinePatch ninePatch;
  private Color solidColor;
  private float alpha;

  public IB drawable(String key) {
    this.drawable = key;
    return this;
  }

  public IB nPatch(String key, int left, int right, int top, int bottom) {
    this.ninePatch = adapter.ninePatch(key, left, right, top, bottom);
    this.drawable = null;
    return this;
  }

  public IB solid(String color, float alpha) {
    this.solidColor = Color.valueOf(color);
    this.alpha = alpha;
    this.drawable = null;
    this.ninePatch = null;
    return this;
  }

  public IB texture(String texture) {
    this.texture = adapter.getTexture(texture);
    return this;
  }

  public IB texture(Texture texture) {
    this.texture = texture;
    return this;
  }

  public IB textureRegion(TextureRegion textureRegion) {
    this.textureRegion = textureRegion;
    return this;
  }

  @Override
  public Image build() {
    Image res;
    if (drawable != null)
      res = new Image(adapter.getDrawable(this.drawable));
    else if (ninePatch != null) {
      res = new Image(ninePatch);
    } else if (solidColor != null) {
      res = UiPopup.createOverlay(w, h, alpha, solidColor);
    } else if (texture != null) {
      res = new Image(texture);
    } else if (textureRegion != null) {
      res = new Image(textureRegion);
    } else
      res = new Image();
    if (w != 0)
      res.setWidth(w);
    if (h != 0)
      res.setHeight(h);
    applyCommonProps(res);
    return res;
  }
}