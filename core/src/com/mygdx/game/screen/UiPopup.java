package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GMain;
import com.mygdx.game.data.GAssetsManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.ButtonFactory;
import com.mygdx.game.utils.GConstants;
import com.mygdx.game.utils.SkinManager;
import com.mygdx.game.utils.SoundManager;
import com.mygdx.game.utils.hud.AL;
import com.mygdx.game.utils.hud.MapGroup;
import com.mygdx.game.utils.hud.builders.BB;
import com.mygdx.game.utils.hud.Button;
import com.mygdx.game.utils.hud.builders.IB;
import com.mygdx.game.utils.hud.builders.LB;
import com.mygdx.game.utils.hud.builders.MGB;
import com.mygdx.game.view.Board;

public class UiPopup extends MapGroup {
  public UiPopup uiPopup;
  GMain game;
  SkinManager skinManager;
  SoundManager soundManager;
  Player player;
  ButtonFactory buttonFactory;

  BitmapFont bitmapFont;
  float centerX, centerY;
  private int level;
  private Image overlay;
  private boolean isPause;
  private boolean isSetting;
  private boolean isWin;
  private boolean isLose;

  public UiPopup(GMain game) {
    super(game.getStage().getWidth(), game.getStage().getHeight() / 2);
    uiPopup = this;
    this.game = game;
    this.skinManager = game.getSkinManager();
    this.soundManager = game.getSoundManager();
    this.buttonFactory = game.getHomeScreen().getButtonFactory();
    this.player = game.getPlayer();
    debug();
    setScale(0.8f);
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

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_UI_WOOD);
    IB.New().drawable("popup_board").pos(0, 0, AL.c).parent(this).build();


  }

  public void updateOverlayImage() {
    overlay.setBounds(-getX(), -getY(), game.getStage().getWidth(), game.getStage().getHeight());
  }

  public void setUiWin(int level, int numStar) {
    this.level = level;

    MGB.New().size(getWidth(), getHeight()).childs(

    ).pos(0, 0, AL.c).parent(this).build();

    addStarEmpty();
    switch (numStar) {
      case 3:
        addStar1();
      case 2:
        addStar2();
      case 1:
        addStar3();
    }
    addBtnHome().addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        setVisible(false);
      }
    });;
    addBtnLeft();
    addBtnRight();
  }

  public void setUiLose(Board board) {
    LB.New().text("Lose").font(GConstants.BMF).fontScale(5).pos(0, 0, AL.ct).parent(this).build();
    addStarEmpty();
    addBtnHome().addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        setVisible(false);
      }
    });;
    addBtnLeft().removeListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        board.reset();
      }
    });
  }

  public void setUiSetting() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_BTN);
    MapGroup bgMG = MGB.New().size(getWidth(), getHeight()).childs(
        IB.New().drawable("close").pos(0, 0, AL.tr).idx("close"),
        LB.New().text("Setting").font(GConstants.BMF).fontScale(3.5f).pos(0, 0, AL.ct)
    ).pos(0, 0, AL.c).parent(this).build();

    bgMG.query("close", Image.class).addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        setVisible(false);
      }
    });

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_UI_WOOD);
    MapGroup musicTick = MGB.New().size(getWidth() * 0.5f, 100).childs(
        LB.New().text("Music").font(GConstants.BMF).fontScale(3f).pos(0, 0, AL.cl),
        BB.New().bg("check_square_grey").pos(0, 0, AL.cr).idx("").idx("unTick"),
        BB.New().bg("check_square_grey_checkmark").pos(0, 0, AL.cr).idx("Tick").visible(!soundManager.isMusicMuted())
    ).pos(0, getHeight() * 0.25f, AL.cb).parent(bgMG).build();

    MapGroup soundTick = MGB.New().size(getWidth() * 0.5f, 100).childs(
        LB.New().text("Sound").font(GConstants.BMF).fontScale(3).pos(0, 0, AL.cl),
        BB.New().bg("check_square_grey").pos(0, 0, AL.cr).idx("unTick"),
        BB.New().bg("check_square_grey_checkmark").pos(0, 0, AL.cr).idx("Tick").visible(!soundManager.isSoundMuted())
    ).pos(0, getHeight() * 0.25f + 100, AL.cb).parent(bgMG).build();


    musicTick.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = !soundManager.isMusicMuted();
        soundManager.setMusicMuted(newState);
        player.setMusicMuted(newState);
        musicTick.query("Tick", Button.class).setVisible(!newState);
      }
    });
    soundTick.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = !soundManager.isSoundMuted();
        soundManager.setSoundMuted(newState);
        player.setSoundMuted(newState);
        soundTick.query("Tick", Button.class).setVisible(!newState);
      }
    });
  }


  public void setUiPause() {

    LB.New().text("Pause").font(GConstants.BMF).fontScale(4).pos(0, 0, AL.ct).debug(true).parent(this).build();
    addBtnHome().addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        setVisible(false);
      }
    });;
    addBtnRight().addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        uiPopup.setVisible(false);
      }
    });
  }

  private Image addStarEmpty() {
    return IB.New().drawable("stroke").pos(0, -200, AL.ct).parent(this).build();
  }

  private Image addStar1() {
    return IB.New().drawable("star1").pos(0, -180, AL.ct).parent(this).build();
  }

  private Image addStar2() {
    return IB.New().drawable("star2").pos(160, -70, AL.ct).parent(this).build();
  }

  private Image addStar3() {
    return IB.New().drawable("star3").pos(-160, -70, AL.ct).parent(this).build();
  }

  private Button addBtnLeft() {
    return BB.New().bg("btn_left").pos(0, -80, AL.bl).parent(this).build();
  }

  private Button addBtnRight() {
    return BB.New().bg("btn_right").pos(0, -80, AL.br).parent(this).build();
  }

  private Button addBtnHome() {
    return BB.New().bg("btn_home").pos(0, -80, AL.cb).parent(this).build();
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
