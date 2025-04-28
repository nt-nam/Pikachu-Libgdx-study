package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.SoundManager;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.BB;
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
    createUI();
    addHandle();
//    debugAll();
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
  protected static Pixmap pixmap(int width, int height, float cornerRadius, int color){
    Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    pixmap.setColor(color); // Màu của hình chữ nhật (ví dụ: màu trắng)

    // Vẽ hình chữ nhật bo góc
    // Vẽ 4 góc (hình tròn)
    pixmap.fillCircle((int) cornerRadius, (int) cornerRadius, (int) cornerRadius); // Góc trên-trái
    pixmap.fillCircle((int) (width - cornerRadius), (int) cornerRadius, (int) cornerRadius); // Góc trên-phải
    pixmap.fillCircle((int) cornerRadius, (int) (height - cornerRadius), (int) cornerRadius); // Góc dưới-trái
    pixmap.fillCircle((int) (width - cornerRadius), (int) (height - cornerRadius), (int) cornerRadius); // Góc dưới-phải

    // Vẽ các cạnh và phần giữa
    pixmap.fillRectangle((int) cornerRadius, 0, width - 2 * (int) cornerRadius, height); // Cạnh trên và dưới
    pixmap.fillRectangle(0, (int) cornerRadius, width, height - 2 * (int) cornerRadius); // Cạnh trái và phải

    return pixmap;
  }

  public Image createRoundedImage(int width, int height, float cornerRadius, int color) {
    // Tạo Pixmap
    Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    pixmap.setColor(color); // Màu của hình chữ nhật (ví dụ: màu trắng)

    // Vẽ hình chữ nhật bo góc
    // Vẽ 4 góc (hình tròn)
    pixmap.fillCircle((int) cornerRadius, (int) cornerRadius, (int) cornerRadius); // Góc trên-trái
    pixmap.fillCircle((int) (width - cornerRadius), (int) cornerRadius, (int) cornerRadius); // Góc trên-phải
    pixmap.fillCircle((int) cornerRadius, (int) (height - cornerRadius), (int) cornerRadius); // Góc dưới-trái
    pixmap.fillCircle((int) (width - cornerRadius), (int) (height - cornerRadius), (int) cornerRadius); // Góc dưới-phải

    // Vẽ các cạnh và phần giữa
    pixmap.fillRectangle((int) cornerRadius, 0, width - 2 * (int) cornerRadius, height); // Cạnh trên và dưới
    pixmap.fillRectangle(0, (int) cornerRadius, width, height - 2 * (int) cornerRadius); // Cạnh trái và phải

    // Tạo Texture từ Pixmap
    Texture texture = new Texture(pixmap);
    pixmap.dispose(); // Giải phóng Pixmap sau khi sử dụng

    // Tạo Image từ Texture
    TextureRegion region = new TextureRegion(texture);
    Image image = new Image(new TextureRegionDrawable(region));

    return image;
  }

  protected abstract void createUI();

  protected abstract void addHandle();

  protected void miniUI() {
    setSize(centerX * 1.8f, centerY);
    setPosition(centerX * 0.1f, centerY * 0.5f, AL.bl);
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().nPatch("panel",100,100,100,100).size(centerX * 1.8f, centerY * 1f).pos(0, 0, AL.c).parent(this).build();
  }

  protected void addPanelBlue() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    IB.New().drawable("panel").pos(0, 0, AL.c).parent(this).build();
  }
  protected void addBtnClose(float x, float y, int al, Group parent) {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    BB.New().bg("btn_exit").pos(x,y,al).idx("btnClose").parent(parent).build();
  }

  protected void addPanel() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().drawable("panel").size(centerX * 1.8f, centerY * 1.8f).pos(0, 0, AL.c).parent(this).build();
  }

  public void show() {
    setVisible(true);
    updateOverlay();
  }

  public void hide() {
    setVisible(false);
  }
}