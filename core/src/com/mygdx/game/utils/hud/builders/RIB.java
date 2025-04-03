package com.mygdx.game.utils.hud.builders;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.utils.hud.RepeatingImage;

@SuppressWarnings("unused")
public class RIB extends AbstractActorBuilder<RepeatingImage> {
  public RIB() {
  }

  public static RIB New() {
    return new RIB();
  }

  private Texture texture;
  private Texture.TextureWrap uWrap;
  private Texture.TextureWrap vWrap;

  public RIB texture(String texture) {
    this.texture = adapter.getTexture(texture);
    return this;
  }

  public RIB wrap(Texture.TextureWrap uWrap, Texture.TextureWrap vWrap) {
    this.uWrap = uWrap;
    this.vWrap = vWrap;
    return this;
  }

  @Override
  public RepeatingImage build() {
    RepeatingImage res;
    if (uWrap != null && vWrap != null)
      res = new RepeatingImage(texture, uWrap, vWrap);
    else if (texture != null)
      res = new RepeatingImage(texture);
    else
      res = new RepeatingImage();

    if (w != 0)
      res.setWidth(w);
    if (h != 0)
      res.setHeight(h);
    applyCommonProps(res);
    return res;
  }
}