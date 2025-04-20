package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.SoundManager;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.external.EventHandler;

public abstract class BaseUI extends MapGroup implements EventHandler {
  protected GMain game;
  protected SoundManager soundManager;
  protected float centerX, centerY;
  protected Image overlay;

  public BaseUI(GMain game) {
    super(GMain.stage().getWidth(), GMain.stage().getHeight());
    this.game = game;
    this.soundManager = game.getSoundManager();
    this.centerX = getWidth() * 0.5f;
    this.centerY = getHeight() * 0.5f;
    createOverlay();
  }

  protected void createOverlay() {
    overlay = createOverlayImage(getWidth(), getHeight(), 0.6f, Color.BLACK);
    overlay.setOrigin(overlay.getWidth() * 0.5f, overlay.getHeight() * 0.5f);
    overlay.setScale(2f);
    addActor(overlay);
  }

  public void updateOverlay() {
    overlay.setBounds(-getX(), -getY(), GMain.stage().getWidth(), GMain.stage().getHeight());
  }

  public static Image createOverlayImage(float width, float height, float alpha, Color color) {
    color.a = alpha;
    Texture texture = createSolidTexture(1, 1, color);
    Image image = new Image(texture);
    image.setSize(width, height);
    return image;
  }

  protected static Texture createSolidTexture(int width, int height, Color color) {
    Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    pixmap.setColor(color);
    pixmap.fillRectangle(0, 0, width, height);
    Texture texture = new Texture(pixmap);
    pixmap.dispose();
    return texture;
  }

  protected abstract void createUI();

  protected void addHomeButton() {
    BB.New().bg("btn_red").label("Home", GConstants.BMF, 0, 0, AL.c)
        .pos(0, 130, AL.cb).parent(this).build()
        .addListener(new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            game.setScreen(game.getHomeScreen());
            setVisible(false);
          }
        });
  }

  protected void addResetButton() {
    BB.New().bg("btn_red").label("Reset Level", GConstants.BMF, 0, 0, AL.c)
        .pos(200, 130, AL.bl).parent(this).build()
        .addListener(new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            game.getPlayScreen().showBoard();
            setVisible(false);
          }
        });
  }
  protected void addPanelBlue(){
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    IB.New().drawable("panel").pos(0,0,AL.c).parent(this).build();
  }

  public void show() {
    setVisible(true);
    updateOverlay();
  }

  public void hide() {
    setVisible(false);
  }
}