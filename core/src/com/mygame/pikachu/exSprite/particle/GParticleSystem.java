package com.mygame.pikachu.exSprite.particle;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.mygame.pikachu.GMain;

import java.util.Iterator;

public class GParticleSystem extends Pool implements Disposable {
   private static ObjectMap particleManagerTable = new ObjectMap();
   private boolean autoFree;
   private Array buff = new Array();
   private Group defaultGroup;
   private ParticleEffect effectSample;
   int freeMin;
   private boolean isAdditiveGroup;
   private boolean isLoop;
   private String particleName;

   static {
      registerParticleSystemUpdate();
   }

   public GParticleSystem(String name, int initialCapacity, int max) {
      super(initialCapacity, max);
      this.defaultGroup = GMain.hud();
      this.isLoop = false;
      this.autoFree = true;
      this.isAdditiveGroup = false;
      this.init(name, max);
   }

//   public GParticleSystem(String name, FileHandle var2, int initialCapacity, int max) {
//      super(initialCapacity, max);
//      this.defaultGroup = GMain.hud();
//      this.isLoop = false;
//      this.autoFree = true;
//      this.isAdditiveGroup = false;
//      if(!GMain.getAssetHelper().isLoaded(GRes.getParticlePath(name))) {
//         GMain.getAssetHelper().addToLog(GMain.getAssetHelper().loadParticleEffectAsImageDir(name, var2) + "---------" + "ParticleEffect.class");
//         GMain.getAssetHelper().finishLoading();
//         GMain.getAssetHelper().initParticle(GMain.getAssetHelper().getParticleEffect(name));
//      }
//
//      this.init(name, max);
//   }
//
//   public GParticleSystem(String name, String atlasname, int initialCapacity, int max) {
//      super(initialCapacity, max);
//      this.defaultGroup = GLayer.ui.getGroup();
//      this.isLoop = false;
//      this.autoFree = true;
//      this.isAdditiveGroup = false;
//      if(!GMain.getAssetHelper().isLoaded(GRes.getParticlePath(name))) {
//         GMain.getAssetHelper().addToLog(GMain.getAssetHelper().loadParticleEffectAsTextureAtlas(name, atlasname) + "---------" + "ParticleEffect.class");
//         GMain.getAssetHelper().finishLoading();
//         GMain.getAssetHelper().initParticle(GMain.getAssetHelper().getParticleEffect(name));
//      }
//
//      this.init(name, max);
//   }
//
//   public GParticleSystem(String name, boolean var2, int initialCapacity, int max) {
//      super(initialCapacity, max);
//      this.defaultGroup = GLayer.ui.getGroup();
//      this.isLoop = false;
//      this.autoFree = true;
//      this.isAdditiveGroup = false;
//      if(!GMain.getAssetHelper().isLoaded(GRes.getParticlePath(name)) && var2) {
//         GMain.getAssetHelper().addToLog(GMain.getAssetHelper().loadParticleEffectAsTextureAtlas(name) + "---------" + "ParticleEffect.class");
//         GMain.getAssetHelper().finishLoading();
//         GMain.getAssetHelper().initParticle(GMain.getAssetHelper().getParticleEffect(name));
//      }
//
//      this.init(name, max);
//   }

   public static void disposeAll() {
      Iterator var0 = particleManagerTable.keys().iterator();

      while(var0.hasNext()) {
         String var1 = (String)var0.next();
         ((GParticleSystem)particleManagerTable.get(var1)).dispose();
      }

      particleManagerTable.clear();
   }

   public static void freeAll() {
      Iterator var0 = particleManagerTable.values().iterator();

      while(var0.hasNext()) {
         GParticleSystem var1 = (GParticleSystem)var0.next();
         if(var1.autoFree) {
            var1.clear();
         }
      }

   }

   public static GParticleSystem getGParticleSystem(String var0) {
      return (GParticleSystem)particleManagerTable.get(var0);
   }

   private void init(String var1, int var2) {
      this.particleName = var1;
      this.effectSample = GMain.getAssetHelper().getParticleEffect(var1);
      particleManagerTable.put(var1, this);
      this.freeMin = var2;

      for(int var3 = 0; var3 < var2; ++var3) {
         this.free(this.newObject());
      }

   }

   private static void registerParticleSystemUpdate() {
//      GStage.registerUpda

   }

   public static void saveAllFreeMin() {
//      FileHandle var0 = Gdx.files.local("GPoolInfo.txt");
//      var0.writeString("\r\n_________GParticleSystem__________\r\n\r\n", true);
//      Iterator var1 = particleManagerTable.values().iterator();
//
//      while(var1.hasNext()) {
//         GParticleSystem var2 = (GParticleSystem)var1.next();
//         var0.writeString(var2.particleName + "  ___  " + var2.freeMin + " / " + var2.max + "\r\n", true);
//      }

   }

   private void update() {
      Iterator var1 = this.buff.iterator();

      while(var1.hasNext()) {
         GParticleSprite var2 = (GParticleSprite)var1.next();
         if(var2.isComplete()) {
            if(this.isLoop) {
               var2.reset();
            } else {
               this.free(var2);
            }
         }
      }

   }

   public void clear() {
      Iterator var1 = this.buff.iterator();

      while(var1.hasNext()) {
         GParticleSprite var2 = (GParticleSprite)var1.next();
         var2.remove();
         this.free(var2);
      }

      this.buff.clear();
      super.clear();
   }

   public GParticleSprite create(float x, float y) {
      return this.create(this.defaultGroup, x, y);
   }

   public GParticleSprite create(Group group, float x, float y) {
      GParticleSprite particle = this.obtain();
      particle.setName(this.particleName);
      particle.setPool(this);
      if(group != null)
         group.addActor(particle);
      this.buff.add(particle);
      particle.setPosition(x, y);
      particle.reset();
      return particle;
   }


   public void dispose() {
      particleManagerTable.remove(this.particleName);
      Iterator var1 = this.buff.iterator();

      while(var1.hasNext()) {
         GParticleSprite var2 = (GParticleSprite)var1.next();
         var2.remove();
         this.free(var2);
         var2.dispose();
      }

      this.buff.clear();
      this.effectSample.dispose();
      this.effectSample = null;
   }

   public void free(GParticleSprite var1) {
      if(var1 != null) {

         var1.setName((String)null);
         var1.remove();
         var1.setScale(1.0F, 1.0F);
         var1.setRotation(0.0F);
         var1.setPosition(0.0F, 0.0F);
         var1.setEmittersPosition(0.0F, 0.0F);
         var1.setTransform(true);
         var1.clearActions();
         var1.clearListeners();
         this.buff.removeValue(var1, true);
         var1.setPool((GParticleSystem)null);
         super.free(var1);
         //System.err.println(this.max + " : Particle free  _______  " + this.particleName + " : " + this.getFree());
      }

   }

   public boolean isAdditiveGroup() {
      return this.isAdditiveGroup;
   }

   protected GParticleSprite newObject() {
      return new GParticleSprite(this.effectSample);
   }

   public GParticleSprite obtain() {
      int var1 = this.getFree();
      if(var1 == 0) {
         System.err.println(this.max + " : Particle obtain  _______  " + this.particleName + " : " + this.getFree());
      }
//      else{
//         System.err.println(this.max + " : Particle get  _______  " + this.particleName + " : " + this.getFree());
//      }

      this.freeMin = Math.min(var1, this.freeMin);
      return (GParticleSprite)super.obtain();
   }

   public void setAutoFree(boolean var1) {
      this.autoFree = var1;
   }

   public void setDefaultGroup(Group var1) {
      this.defaultGroup = var1;
   }

   public void setLoop(boolean var1) {
      Iterator var2 = this.effectSample.getEmitters().iterator();

      while(var2.hasNext()) {
         ((ParticleEmitter)var2.next()).setContinuous(var1);
      }

   }

   public void setToAdditiveGroup(boolean var1) {
      this.isAdditiveGroup = var1;
   }
}
