package com.mygdx.game.utils.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RepeatingImage extends Actor {
  private Texture texture;

  public RepeatingImage() {

  }

  public RepeatingImage(Texture texture) {
    this.texture = texture;
    texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    setSize(texture.getWidth(), texture.getHeight());
  }

  public RepeatingImage(Texture texture, Texture.TextureWrap wrapX, Texture.TextureWrap wrapY) {
    this.texture = texture;
    texture.setWrap(wrapX, wrapY);
    setSize(texture.getWidth(), texture.getHeight());
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.setColor(Color.WHITE);
    if (texture != null) {
      // Get the actor's width and height
      float width = getWidth();
      float height = getHeight();

      // Draw the texture repeatedly
      float u = width / texture.getWidth();
      float v = height / texture.getHeight();

      batch.draw(texture,
          getX(), getY(),
          getOriginX(), getOriginY(),
          width, height,
          getScaleX(), getScaleY(),
          getRotation(),
          0, 0,
          (int) (u * texture.getWidth()), (int) (v * texture.getHeight()),
          false, false);
    }
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);
  }
}