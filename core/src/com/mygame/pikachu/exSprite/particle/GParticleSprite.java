package com.mygame.pikachu.exSprite.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.mygame.pikachu.GMain;

import java.util.Iterator;

public class GParticleSprite extends Group implements Pool.Poolable, Disposable {
  public static boolean isDebug = false;
  private GParticle particle;
  private GParticleSystem pool;

  public GParticleSprite(ParticleEffect particleEffect) {
    this.particle = new GParticle(particleEffect, (GParticle) null);
    this.addActor(this.particle);
  }

  public GParticleSprite(String name) {
    this.particle = new GParticle(name, (GParticle) null);
    this.addActor(this.particle);
  }

  public void dispose() {
    this.particle.dispose();
  }

  public void free() {
    if (this.pool != null) {
      this.pool.free(this);
    }
  }

  public BoundingBox getBoundingBox() {
    return this.particle.getBoundingBox();
  }

  public ParticleEffect getEffect() {
    return this.particle.getEffect();
  }

  public boolean isComplete() {
    return this.particle.isComplete();
  }

  public void reset() {
    this.particle.reset();
  }

  public void setAdditive(boolean isAdditive) {
    Iterator it = this.particle.getEffect().getEmitters().iterator();

    while (it.hasNext()) {
      ((ParticleEmitter) it.next()).setAdditive(isAdditive);
    }

  }

  public void setAttached(boolean isAttached) {
    Iterator it = this.particle.getEffect().getEmitters().iterator();

    while (it.hasNext()) {
      ((ParticleEmitter) it.next()).setAttached(isAttached);
    }

  }

  public void setEmittersPosition(float x, float y) {
    this.particle.setEmittersPosition(x, y);
  }

  public void setLoop(boolean isLoop) {
    Iterator it = this.particle.getEffect().getEmitters().iterator();

    while (it.hasNext()) {
      ((ParticleEmitter) it.next()).setContinuous(isLoop);
    }

  }

  public void setPool(GParticleSystem particleSys) {
    this.pool = particleSys;
  }

  public void setScale(float scale) {
    this.particle.setScale(scale);
  }


  public void setScaleX(float scale) {
    this.particle.setScaleX(scale);
  }

  public void setScaleY(float scale) {
    this.particle.setScaleY(scale);
  }

//  public void delayStop(float delay) {
//    this.addAction(Actions.delay(delay, GSimpleAction.simpleAction((var1, var2) -> {
//      setStop(true);
//      return true;
//    })));
//  }

  public void setStop(boolean value) {
    isStop = value;
  }

  boolean isStop = false;

  @Override
  public void act(float delta) {
    if (!isStop)
      super.act(delta);
  }

  private class GParticle extends Actor implements Pool.Poolable, Disposable {
    float delta;
    private ParticleEffect effect;

    private GParticle(ParticleEffect particleEffect) {
      this.effect = new ParticleEffect(particleEffect);
    }

    GParticle(ParticleEffect particleEffect, GParticle var3) {
      this.effect = new ParticleEffect(particleEffect);
    }

    private GParticle(String name) {
      this.effect = GMain.getAssetHelper().getParticleEffect(name);
    }

    GParticle(String name, GParticle gParticle) {
      this.effect = GMain.getAssetHelper().getParticleEffect(name);
    }

    private BoundingBox getBoundingBox() {
      return this.effect.getBoundingBox();
    }

    private ParticleEffect getEffect() {
      return this.effect;
    }

    private boolean isComplete() {
      return this.effect.isComplete();
    }

    public void act(float delta) {
      this.delta = delta;
    }

    public void dispose() {
      this.effect.dispose();
      GMain.getAssetHelper().unload((Object) this.effect);
    }

    public void draw(Batch batch, float parentAlpha) {
      if (GParticleSprite.this.pool.isAdditiveGroup()) {
        GParticleSprite.this.setAdditive(true);
        this.effect.setPosition(this.getX(), this.getY());
      }
      this.effect.draw(batch, this.delta);
      this.delta = 0.0F;
    }

    public void reset() {
      this.effect.reset();
    }

    public void setEmittersPosition(float x, float y) {
      this.effect.setPosition(x, y);
    }

    public void setScale(float scale) {
      super.setScale(scale);
      this.effect.scaleEffect(scale);
    }

    public void setScale(float scalex, float scaley) {
      super.setScale(scalex, scaley);
      this.effect.scaleEffect(scalex, scaley, 1f);
    }

  }
}
