package com.mygame.pikachu.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.model.Player;
import com.mygame.pikachu.utils.ButtonFactory;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.SkinManager;
import com.mygame.pikachu.utils.SoundManager;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.Button;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.LB;
import com.mygame.pikachu.utils.hud.builders.MGB;
import com.mygame.pikachu.view.Board;

public class UiPopup extends MapGroup {
  public UiPopup uiPopup;
  GMain game;
  SoundManager soundManager;
  Player player;

  BitmapFont bitmapFont;
  float centerX, centerY;
  private int level;
  private Image overlay, ribbon;
  private boolean isPause;
  private boolean isSetting;
  private boolean isWin;
  private boolean isLose;

  public UiPopup(GMain game) {
    super(game.getStage().getWidth(), game.getStage().getHeight());
    uiPopup = this;
    this.game = game;
    this.soundManager = game.getSoundManager();
    this.player = game.getPlayer();
    centerX = getWidth() * 0.5f;
    centerY = getHeight() * 0.5f;
    createUiDefault();
  }

  private void createUiDefault() {
    bitmapFont = GMain.getAssetHelper().getBitmapFont(GConstants.BMF + ".fnt");

    overlay = createOverlay(game.getStage().getWidth(), game.getStage().getHeight(), 0.6f, Color.BLACK);
    overlay.setOrigin(overlay.getWidth() * 0.5f, overlay.getHeight() * 0.5f);
    overlay.setScale(2f);
    addActor(overlay);

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().drawable("panel").pos(0, 0, AL.c).parent(this).build();
    ribbon = IB.New().drawable("ribbon").pos(0, 0, AL.ct).parent(this).build();
  }

  public void updateOverlayImage() {
    overlay.setBounds(-getX(), -getY(), game.getStage().getWidth(), game.getStage().getHeight());
  }

  public void setUiWin(int level, int numStar) {
    this.level = level;

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().texture("topscore").pos(0,0,AL.ct).parent(this).build();
    Button reset  = BB.New().bg("btn_red").label("Reset Level",GConstants.BMF,0,0,AL.c).pos(200,130,AL.bl).parent(this).build();
    Button home = BB.New().bg("btn_red").label("Home",GConstants.BMF,0,0,AL.c).pos(0,130,AL.cb).parent(this).build();
    home.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(game.getHomeScreen());
        uiPopup.setVisible(false);
      }
    });

    reset.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getPlayScreen().showBoard();
        uiPopup.setVisible(false);
      }
    });
  }

  public void setUiLose(Board board) {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    LB.New().text("Lose").font(GConstants.BMF).fontScale(5).pos(0, 0, AL.ct).parent(this).build();
    Button next = BB.New().bg("btn_red").label("Next Level",GConstants.BMF,0,0,AL.c).pos(0,130,AL.cb).parent(this).build();
    Button reset  = BB.New().bg("btn_red").label("Reset Level",GConstants.BMF,0,0,AL.c).pos(200,130,AL.bl).parent(this).build();
    Button home = BB.New().bg("btn_red").label("Home",GConstants.BMF,0,0,AL.c).pos(0,130,AL.cb).parent(this).build();

    next.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        uiPopup.setVisible(false);
      }
    });

    home.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(game.getHomeScreen());
        uiPopup.setVisible(false);
      }
    });

    reset.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getPlayScreen().showBoard();
        uiPopup.setVisible(false);
      }
    });
  }

  public void setUiSetting() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    MapGroup bgMG = MGB.New().size(getWidth(), getHeight()).childs(
        IB.New().texture(new Texture("atlas/common/btn_exit.png")).pos(0, 0, AL.tr).idx("close"),
        BB.New().bg("btn_exit").pos(0, 0, AL.tr).idx("close"),
        LB.New().text("Setting").font(GConstants.BMF).fontScale(3.5f).pos(0, 0, AL.ct)
    ).pos(0, 0, AL.c).parent(this).build();

    bgMG.query("close", Button.class).addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        setVisible(false);
      }
    });

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    MapGroup musicTick = MGB.New().size(getWidth() * 0.5f, 100).childs(
        BB.New().bg("Music2").pos(0, 0, AL.c).idx("unTick").visible(soundManager.isMusicMuted()),
        BB.New().bg("Music1").pos(0, 0, AL.c).idx("Tick").visible(!soundManager.isMusicMuted())
    ).pos(0, getHeight() * 0.25f, AL.cb).parent(bgMG).build();

    MapGroup soundTick = MGB.New().size(getWidth() * 0.5f, 100).childs(
        BB.New().bg("sound2").pos(0, 0, AL.c).idx("unTick").visible(soundManager.isSoundMuted()),
        BB.New().bg("sound1").pos(0, 0, AL.c).idx("Tick").visible(!soundManager.isSoundMuted())
    ).pos(0, getHeight() * 0.25f + 120, AL.cb).parent(bgMG).build();

    MapGroup vibrate = MGB.New().size(getWidth() * 0.5f, 100).childs(
        BB.New().bg("vibrate2").pos(0, 0, AL.c).idx("unTick").visible(true)/*.visible(soundManager.isSoundMuted())*/,
        BB.New().bg("vibrate1").pos(0, 0, AL.c).idx("Tick").visible(false)/*.visible(!soundManager.isSoundMuted())*/
    ).pos(0, getHeight() * 0.25f + 240, AL.cb).parent(bgMG).build();



    musicTick.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = !soundManager.isMusicMuted();
        soundManager.setMusicMuted(newState);
        player.setMusicMuted(newState);
        musicTick.query("Tick", Button.class).setVisible(!newState);
        musicTick.query("unTick", Button.class).setVisible(newState);

      }
    });
    soundTick.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = !soundManager.isSoundMuted();
        soundManager.setSoundMuted(newState);
        player.setSoundMuted(newState);
        soundTick.query("Tick", Button.class).setVisible(!newState);
        soundTick.query("unTick", Button.class).setVisible(newState);
      }
    });
    vibrate.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = vibrate.query("Tick", Button.class).isVisible();
        vibrate.query("Tick", Button.class).setVisible(!newState);
        vibrate.query("unTick", Button.class).setVisible(newState);
      }
    });
  }


  public void setUiPause() {
    ribbon.setVisible(false);
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    IB.New().drawable("tittle_pausing").pos(0,20,AL.ct).parent(this).debug(true).build();
    Button reset  = BB.New().bg("btn_red").label("Reset Level",GConstants.BMF,0,0,AL.c).pos(200,130,AL.bl).parent(this).build();
    Button resume = BB.New().bg("btn_red").label("Resume",GConstants.BMF,0,0,AL.c).pos(0,130,AL.cb).parent(this).build();
    Button home = BB.New().bg("btn_red").label("Home",GConstants.BMF,0,0,AL.c).pos(200,130,AL.br).parent(this).build();

    resume.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        uiPopup.setVisible(false);
      }
    });

    home.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(game.getHomeScreen());
        uiPopup.setVisible(false);
      }
    });

    reset.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getPlayScreen().showBoard();
        uiPopup.setVisible(false);
      }
    });
  }

  public void setLabelWin(int core, int time) {
    LB.New().text("Level" + level).font(GConstants.BMF).fontScale(2.5f).pos(0, 150, AL.c).parent(this).build();
    LB.New().text("Core:   " + core).font(GConstants.BMF).fontScale(2f).pos(0, 40, AL.c).parent(this).build();
    LB.New().text("Time:   " + time).font(GConstants.BMF).fontScale(2f).pos(0, -40, AL.c).parent(this).build();
  }

  public static Image createOverlay(float w, float h, float alpha, Color c) {
    c.a = alpha;
    Texture ol = createSolid(c);
    Image res = new Image(ol);
    res.setSize(w, h);
    return res;
  }

  public static Texture createSolid(Color color) {
    return createSolid(1, 1, color);
  }

  public static Texture createSolid(int w, int h, Color color) {
    Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
    pixmap.setColor(color);
    pixmap.fillRectangle(0, 0, w, h);
    Texture texture = new Texture(pixmap);
    pixmap.dispose();
    return texture;
  }

  public boolean isPause() {
    return isPause;
  }

  public boolean isLose() {
    return isLose;
  }

  public boolean isWin() {
    return isWin;
  }

  public boolean isSetting() {
    return isSetting;
  }


}
