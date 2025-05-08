package com.mygame.pikachu.exSprite.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
//import com.core.util.GLayer;
//import com.core.util.GRes;
//import com.core.util.GStage;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.util.GRes;

import java.util.Iterator;

public class GParticleSystem extends Pool implements Disposable {
  private static ObjectMap particleManagerTable = new ObjectMap();
  private boolean autoFree;
  private Array buff = new Array();
  private Group defaultGroup;
  public ParticleEffect effectSample;
  int freeMin;
  private boolean isAdditiveGroup;
  private boolean isLoop;
  private String particleName;

  static {
    registerParticleSystemUpdate();
  }

  public GParticleSystem(String name, int initialCapacity, int max) {
    super(initialCapacity, max);
//    this.defaultGroup = GLayer.ui.getGroup();
    this.defaultGroup = GMain.hud();
    this.isLoop = false;
    this.autoFree = true;
    this.isAdditiveGroup = false;
    this.init(name, max);
  }

  public GParticleSystem(String name, FileHandle file, int initialCapacity, int max) {
    super(initialCapacity, max);
//    this.defaultGroup = GLayer.ui.getGroup();
    this.defaultGroup = GMain.hud();
    this.isLoop = false;
    this.autoFree = true;
    this.isAdditiveGroup = false;
    if (!GMain.getAssetHelper().isLoaded(name)) {
      GMain.getAssetHelper().addToLog(GMain.getAssetHelper().loadParticleEffectAsImageDir(name, file) + "---------" + "ParticleEffect.class");
      GMain.getAssetHelper().finishLoading();
      GMain.getAssetHelper().initParticle(GMain.getAssetHelper().getParticleEffect(name));
    }

    this.init(name, max);
  }

  public GParticleSystem(String pname, String atlas, int initialCapacity, int max) {
    super(initialCapacity, max);
//    this.defaultGroup = GLayer.ui.getGroup();
    this.defaultGroup = GMain.hud();
    this.isLoop = false;
    this.autoFree = true;
    this.isAdditiveGroup = false;
    if (!GMain.getAssetHelper().isLoaded(GRes.getParticlePath(pname))) {
      GMain.getAssetHelper().addToLog(GMain.getAssetHelper().loadParticleEffectAsTextureAtlas(pname, atlas) + "---------" + "ParticleEffect.class");
      GMain.getAssetHelper().finishLoading();
      GMain.getAssetHelper().initParticle(GMain.getAssetHelper().getParticleEffect(pname));
    }

    this.init(pname, max);
  }

  public GParticleSystem(String name, boolean forceLoad, int initialCapacity, int max) {
    super(initialCapacity, max);
//    this.defaultGroup = GLayer.ui.getGroup();
    this.defaultGroup = GMain.hud();
    this.isLoop = false;
    this.autoFree = true;
    this.isAdditiveGroup = false;
    if (!GMain.getAssetHelper().isLoaded(GRes.getParticlePath(name)) && forceLoad) {
      GMain.getAssetHelper().addToLog(GMain.getAssetHelper().loadParticleEffectAsTextureAtlas(name) + "---------" + "ParticleEffect.class");
      GMain.getAssetHelper().finishLoading();
      GMain.getAssetHelper().initParticle(GMain.getAssetHelper().getParticleEffect(name));
    }

    this.init(name, max);
  }

  public static void disposeAll() {
    Iterator it = particleManagerTable.keys().iterator();

    while (it.hasNext()) {
      String name = (String) it.next();
      ((GParticleSystem) particleManagerTable.get(name)).dispose();
    }

    particleManagerTable.clear();
  }

  public static void freeAll() {
    Iterator it = particleManagerTable.values().iterator();

    while (it.hasNext()) {
      GParticleSystem particleSys = (GParticleSystem) it.next();
      if (particleSys.autoFree) {
        particleSys.clear();
      }
    }

  }

  public static GParticleSystem getGParticleSystem(String name) {
    GParticleSystem particleSystem = (GParticleSystem) particleManagerTable.get(name);
    if (particleSystem == null) {
//      GMain.platform().TrackCustomKey("particle_null", name);
    }
    return particleSystem;
  }

  private void init(String name, int freeMin) {
    this.particleName = name;
    this.effectSample = GMain.getAssetHelper().getParticleEffect(name);
    particleManagerTable.put(name, this);
    this.freeMin = freeMin;

    for (int i = 0; i < freeMin; ++i) {
      this.free(this.newObject());
    }

  }

  private static void registerParticleSystemUpdate() {
//    GStage.registerUpdateService("particleSystemUpdate", new GStage.GUpdateService() {
//      public boolean update(float delta) {
//        Iterator it = GParticleSystem.particleManagerTable.values().iterator();
//
//        while (it.hasNext()) {
//          ((GParticleSystem) it.next()).update();
//        }
//        return false;
//      }
//    });
  }

  public static void saveAllFreeMin() {
    FileHandle var0 = Gdx.files.local("GPoolInfo.txt");
    var0.writeString("\r\n_________GParticleSystem__________\r\n\r\n", true);
    Iterator it = particleManagerTable.values().iterator();

    while (it.hasNext()) {
      GParticleSystem particleSys = (GParticleSystem) it.next();
      var0.writeString(particleSys.particleName + "  ___  " + particleSys.freeMin + " / " + particleSys.max + "\r\n", true);
    }

  }

  private void update() {
    Iterator it = this.buff.iterator();

    while (it.hasNext()) {
      GParticleSprite particle = (GParticleSprite) it.next();
      if (particle.isComplete()) {
        if (this.isLoop) {
          particle.reset();
        } else {
          this.free(particle);
        }
      }
    }

  }

  public void clear() {
    Iterator it = this.buff.iterator();

    while (it.hasNext()) {
      GParticleSprite particleSprite = (GParticleSprite) it.next();
      particleSprite.remove();
      this.free(particleSprite);
    }

    this.buff.clear();
    super.clear();
  }

  public GParticleSprite create(float x, float y) {
    return this.create(this.defaultGroup, x, y);
  }

  public GParticleSprite create(Group group, float x, float y) {
    GParticleSprite var4 = this.obtain();
    if (var4 == null)
      return null;
    var4.setName(this.particleName);
    var4.setPool(this);
    if (group != null)
      group.addActor(var4);
    this.buff.add(var4);
    var4.setPosition(x, y);
    var4.reset();
    return var4;
  }

  public GParticleSprite create(Group group, Actor actorBefore, float x, float y) {
    GParticleSprite var4 = this.obtain();
    if (var4 == null)
      return null;
    var4.setName(this.particleName);
    var4.setPool(this);
    if (group != null)
      group.addActorBefore(actorBefore, var4);
    this.buff.add(var4);
    var4.setPosition(x, y);
    var4.reset();
    return var4;
  }

  public void dispose() {
    particleManagerTable.remove(this.particleName);
    Iterator it = this.buff.iterator();

    while (it.hasNext()) {
      GParticleSprite particleSprite = (GParticleSprite) it.next();
      particleSprite.remove();
      this.free(particleSprite);
      particleSprite.dispose();
    }

    this.buff.clear();
    this.effectSample.dispose();
    this.effectSample = null;
  }

  public void free(GParticleSprite particle) {
    if (particle != null) {
      particle.setName((String) null);
      particle.remove();
      particle.setScale(1.0F, 1.0F);
      particle.setRotation(0.0F);
      particle.setPosition(0.0F, 0.0F);
      particle.setEmittersPosition(0.0F, 0.0F);
      particle.setTransform(true);
      particle.clearActions();
      particle.clearListeners();
      this.buff.removeValue(particle, true);
      particle.setPool(null);
      super.free(particle);
    }

  }

  public boolean isAdditiveGroup() {
    return this.isAdditiveGroup;
  }

  protected GParticleSprite newObject() {
    return new GParticleSprite(this.effectSample);
  }

  public GParticleSprite obtain() {
    int freeCount = this.getFree();
    if (freeCount == 0) {
      System.err.println(this.max + " : Particle obtain  _______  " + this.particleName + " : " + this.getFree());
    }
    this.freeMin = Math.min(freeCount, this.freeMin);
    return (GParticleSprite) super.obtain();
  }

  public void setAutoFree(boolean autoFree) {
    this.autoFree = autoFree;
  }

  public void setDefaultGroup(Group group) {
    this.defaultGroup = group;
  }

  public void setLoop(boolean isLoop) {
    Iterator it = this.effectSample.getEmitters().iterator();

    while (it.hasNext()) {
      ((ParticleEmitter) it.next()).setContinuous(isLoop);
    }

  }

  public void setToAdditiveGroup(boolean isAdditiveGroup) {
    this.isAdditiveGroup = isAdditiveGroup;
  }
}
