package com.mygame.pikachu.exSprite.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.exSprite.particle.GParticleSystem;

import java.util.Iterator;

public class GParticleSprite extends Group implements Pool.Poolable, Disposable {
   public static boolean isDebug = false;
   private GParticle particle;
   private GParticleSystem pool;

   public GParticleSprite(ParticleEffect var1) {
      this.particle = new GParticle(var1, (GParticle)null);
      this.addActor(this.particle);
   }

   public GParticleSprite(String var1) {
      this.particle = new GParticle(var1, (GParticle)null);
      this.addActor(this.particle);
   }

   public void dispose() {
      this.particle.dispose();
   }

   public void free() {
      if(this.pool != null) {
         this.pool.free(this);
      }
   }

//   public void delayStop(float delay){
//      this.addAction(Actions.delay(delay, GSimpleAction.simpleAction((var1, var2) -> {
//         setStop(true);
//         return true;
//      })));
//   }

   public void setStop(boolean value){
      isStop = value;
   }

   boolean isStop = false;
   public void act (float delta) {
      if(isStop==false)
         super.act(delta);
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

   public void setAdditive(boolean var1) {
      Iterator var2 = this.particle.getEffect().getEmitters().iterator();

      while(var2.hasNext()) {
         ((ParticleEmitter)var2.next()).setAdditive(var1);
      }

   }

   public void setAttached(boolean var1) {
      Iterator var2 = this.particle.getEffect().getEmitters().iterator();

      while(var2.hasNext()) {
         ((ParticleEmitter)var2.next()).setAttached(var1);
      }

   }

   public void setEmittersPosition(float var1, float var2) {
      this.particle.setEmittersPosition(var1, var2);
   }

   public void setLoop(boolean var1) {
      Iterator var2 = this.particle.getEffect().getEmitters().iterator();

      while(var2.hasNext()) {
         ((ParticleEmitter)var2.next()).setContinuous(var1);
      }

   }

   public void setPool(GParticleSystem var1) {
      this.pool = var1;
   }

   public void setScale(float var1) {
      this.particle.setScale(var1);
   }

   private class GParticle extends Actor implements Pool.Poolable, Disposable {
      float delta;
      private ParticleEffect effect;

      private GParticle(ParticleEffect var2) {
         this.effect = new ParticleEffect(var2);
      }

      // $FF: synthetic method
      GParticle(ParticleEffect var2, GParticle var3) {
    	  this.effect = new ParticleEffect(var2);

      }

      private GParticle(String var2) {
         this.effect = GMain.getAssetHelper().getParticleEffect(var2);
      }

      // $FF: synthetic method
      GParticle(String var2, GParticle var3) {
    	  this.effect = GMain.getAssetHelper().getParticleEffect(var2);
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

      public void act(float var1) {
         this.delta = var1;
      }

      public void dispose() {
         this.effect.dispose();
         GMain.getAssetHelper().unload((Object)this.effect);
      }


      public void draw(Batch var1, float var2) {
         if(GParticleSprite.this.pool.isAdditiveGroup()) {
            GParticleSprite.this.setAdditive(true);
            this.effect.setPosition(this.getX(), this.getY());
         }

         this.effect.draw(var1, this.delta);
         this.delta = 0.0F;
      }

      public void reset() {
         this.effect.reset();
      }

      public void setEmittersPosition(float var1, float var2) {
         this.effect.setPosition(var1, var2);
      }
   }
}
