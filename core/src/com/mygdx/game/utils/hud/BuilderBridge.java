package com.mygdx.game.utils.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GMain;
import com.mygdx.game.data.GAssetsManager;
import com.mygdx.game.utils.hud.external.BuilderAdapter;

@SuppressWarnings("unused")
public class BuilderBridge implements BuilderAdapter {
  Label debugMsg;
  HUD hud;
  Drawable nullTexture;
  Image overlay;
  boolean debug;

  public BuilderBridge(HUD hud) {
    this.hud = hud;
    this.debug = false;
    Pixmap tex = new Pixmap(40, 40, Pixmap.Format.RGB888);
    tex.setColor(Color.BROWN);
    tex.fill();
    nullTexture = new TextureRegionDrawable(new Texture(tex));
    setupDebug();
    tex.dispose();
  }

  @Override
  public Drawable getDrawable(String region) {
    TextureRegion res = GMain.getAssetHelper().getTextureRegion(GAssetsManager.getTextureAtlas(), region);
//    TextureRegion res = GMain.getAssetHelper().getTextureRegion(GameConstants.DEFAULT_ANIMAL_ATLAS_PATH);
    if (res == null) {
      return nullTexture;
    }
    return new TextureRegionDrawable(res);
  }

  @Override
  public TextureRegion getRegion(String region) {
    TextureRegion res = GMain.getAssetHelper().getTextureRegion(GAssetsManager.getTextureAtlas(), region);
    if (res == null) {
      Pixmap pix = new Pixmap(40, 40, Pixmap.Format.RGB888);
      pix.setColor(Color.BROWN);
      pix.fill();
      TextureRegion result = new TextureRegion(new Texture(pix));
      pix.dispose();
      return result;
    }
    return res;
  }

  public Texture getTexture(String texture) {
//    Texture res = GMain.getAssetHelper().getTexture(texture);
    Texture res = null;
    if (res == null) {
      Pixmap pix = new Pixmap(40, 40, Pixmap.Format.RGB888);
      pix.setColor(Color.BROWN);
      pix.fill();
      Texture result = new Texture(pix);
      pix.dispose();
      return result;
    }
    return res;
  }

  @Override
  public ShaderProgram getShaderProgram(String name) {
//    return GMain.getAssetHelper().getShaderProgram(name);
    return null;
  }

  @Override
  public NinePatch ninePatch(String key, int left, int right, int top, int bottom) {
    return new NinePatch(getRegion(key), left, right, top, bottom);
  }

  @Override
  public BitmapFont getBitmapFont(String key) {
    return GMain.getAssetHelper().getBitmapFont(key + ".fnt");
  }

  @Override
  public MapGroup query(String path) {
    return hud.query(path, MapGroup.class);
  }

  @Override
  public void debugMsg(String msg) {
    String old = debugMsg.getText().toString();
    if (!old.contains(msg)) {
      debugMsg.setText(msg);
//      GMain.console.log("cur path: " + msg);
    }
  }

  @Override
  public boolean debug() {
    return this.debug;
  }

  @Override
  public float worldWidth() {
    return hud.getWidth();
  }

  @Override
  public float worldHeight() {
    return hud.getHeight();
  }

  public void showDebug() {
    debugMsg.setPosition(0, 0);
    hud.addActor(overlay, AL.cb);
    hud.addActor(debugMsg, AL.cb);
  }

  private void setupDebug() {
    Label.LabelStyle ls = new Label.LabelStyle();
    ls.font = new BitmapFont();
    debugMsg = new Label("", ls);

    Pixmap labelColor = new Pixmap(720, 40, Pixmap.Format.RGB888);
    labelColor.setColor(Color.BROWN);
    labelColor.fill();
    overlay = new Image(new Texture(labelColor));
    overlay.setColor(0.5f, 0.5f, 0.5f, 0.5f);
    labelColor.dispose();
  }
}