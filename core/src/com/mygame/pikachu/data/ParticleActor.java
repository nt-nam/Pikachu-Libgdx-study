package com.mygame.pikachu.data;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleActor extends Actor {
  private ParticleEffect effect;

  public ParticleActor(ParticleEffect effect) {
    this.effect = effect;
    effect.start();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    effect.setPosition(getX(), getY()); // Cập nhật vị trí theo Actor
    effect.update(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    effect.draw(batch);
  }

  @Override
  public boolean remove() {
    effect.dispose();
    super.remove();
    return false;
  }
}
