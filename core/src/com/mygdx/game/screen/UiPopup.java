package com.mygdx.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.ButtonFactory;
import com.mygdx.game.utils.SkinManager;
import com.mygdx.game.utils.SoundManager;
import com.mygdx.game.view.Board;

public class UiPopup extends Group {
  public UiPopup uiPopup;
  PikachuGame game;
  AssetManager assetManager;
  SkinManager skinManager;
  SoundManager soundManager;
  Player player;
  ButtonFactory buttonFactory;

  Image boardImage, star1, star2, star3, starEmpty;
  ImageButton btnLeft, btnRight, btnHome;
  BitmapFont font;
  private Label.LabelStyle labelStyle;
  float centerX, centerY;
  float x, y, w, h;
  private int level;
  private Image overlay;
  private Button musicTick, soundTick;
  private boolean isPause;
  private boolean isSetting;
  private boolean isWin;
  private boolean isLose;

  public UiPopup(PikachuGame game) {
    uiPopup = this;
    this.game = game;
    setSize(game.getStage().getWidth(), game.getStage().getHeight());
    this.assetManager = game.getAssetHelper();
    this.skinManager = game.getSkinManager();
    this.soundManager = game.getSoundManager();
    this.buttonFactory = game.getHomeScreen().getButtonFactory();
    this.player = game.getPlayer();

    setScale(0.8f);
    centerX = getWidth() * 0.5f;
    centerY = getHeight() * 0.5f;
    createUiDefault();
    setPosition(centerX - getWidth() * 0.5f, centerY - getHeight() * 0.5f);
  }

  private void createUiDefault() {
    font = assetManager.get("font/arial_uni_30.fnt");
    labelStyle = new Label.LabelStyle(font, Color.WHITE);

    overlay = createOverlay(game.getStage().getWidth(), game.getStage().getHeight(), 0.6f, Color.BLACK);
    overlay.setOrigin(overlay.getWidth() * 0.5f, overlay.getHeight() * 0.5f);
    overlay.setScale(2f);
    addActor(overlay);

    addUiBoard();
  }

  public void updateOverlayImage() {
    overlay.setBounds(-getX(), -getY(), game.getStage().getWidth(), game.getStage().getHeight());
  }

  public void setUiWin(int level, int numStar) {
    this.level = level;
    addStarEmpty();
    switch (numStar) {
      case 3:
        addStar1();
      case 2:
        addStar2();
      case 1:
        addStar3();
    }
    addBtnHome();
    addBtnLeft();
    addBtnRight();
  }

  public void setUiLose(Board board) {
    Label loseLabel = createLabel("Lost", x, y, w, h, Align.center, 5f);
    addStarEmpty();
    addBtnHome();
    addBtnLeft();
    btnLeft.removeListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        board.reset();
      }
    });
    float x = starEmpty.getX(), y = starEmpty.getY() - starEmpty.getHeight() * 1.15f, w = starEmpty.getWidth(), h = starEmpty.getHeight();
  }

  public void setUiSetting() {
    Label titleLabel = createLabel("Setting", x + w * 0.3f, y + h * 0.8f, w * 0.4f, h * 0.2f, Align.center, 4f);
    createLabelSetting();

    ImageButton btnClose = buttonFactory.createButtonBtn("close", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        remove();
        super.clicked(event, x, y);
      }
    });
    btnClose.setPosition(x + boardImage.getWidth() - btnClose.getWidth(), y + boardImage.getHeight() - btnClose.getHeight());
    addActor(btnClose);
  }

  private void createLabelSetting() {
    Label musicLabel = createLabel("Music", x + w * 0.25f, y + h * 0.25f, w * 0.5f, h * 0.2f, Align.left, 3f);
    Label soundLabel = createLabel("Sound", x + w * 0.25f, y + h * 0.5f, w * 0.5f, h * 0.2f, Align.left, 3f);

    musicTick = buttonFactory.createButtonTick(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = !soundManager.isMusicMuted();
        soundManager.setMusicMuted(newState);
        player.setMusicMuted(newState); // Cập nhật và lưu vào Player
        musicTick.setChecked(!newState);
      }
    });
    musicTick.setChecked(!soundManager.isMusicMuted());
    musicTick.setPosition(musicLabel.getX() + musicLabel.getWidth() - musicTick.getWidth(),
        musicLabel.getY() + musicLabel.getHeight() * 0.5f - musicTick.getHeight() * 0.5f);

    soundTick = buttonFactory.createButtonTick(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = !soundManager.isSoundMuted();
        soundManager.setSoundMuted(newState);
        player.setSoundMuted(newState); // Cập nhật và lưu vào Player
        soundTick.setChecked(!newState);
      }
    });
    soundTick.setChecked(!soundManager.isSoundMuted());
    soundTick.setPosition(soundLabel.getX() + soundLabel.getWidth() - soundTick.getWidth(),
        soundLabel.getY() + soundLabel.getHeight() * 0.5f - soundTick.getWidth() * 0.5f);

    addActor(musicTick);
    addActor(soundTick);

    musicLabel.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getSoundManager().setMusicMuted(!game.getSoundManager().isMusicMuted());
      }
    });
    soundLabel.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getSoundManager().setSoundMuted(!game.getSoundManager().isSoundMuted());
      }
    });
  }

  public void setUiPause() {
    Label titleLabel = createLabel("Pause", x + w * 0.3f, y + h * 0.8f, w * 0.4f, h * 0.2f, Align.center, 4f);
    addActor(titleLabel);
    addBtnHome();
    addBtnRight();
    btnRight.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        uiPopup.setVisible(false);
      }
    });
  }

  private Label createLabel(String text, float x, float y, float width, float height, int type, float scale) {
    Label lb = new Label(text, labelStyle);
    lb.setBounds(x, y, width, height);
    lb.setFontScale(scale);
    lb.setAlignment(type);
    addActor(lb);
    return lb;
  }

  private void addUiBoard() {
    boardImage = skinManager.createImageUI("popup_board");
//    boardImage.setScale(0.8f);
    boardImage.setPosition(centerX - boardImage.getWidth() * 0.5f, centerY - boardImage.getHeight() * 0.5f);
    x = boardImage.getX();
    y = boardImage.getY();
    w = boardImage.getWidth();
    h = boardImage.getHeight();
    addActor(boardImage);
  }

  private void addStarEmpty() {
    starEmpty = skinManager.createImageUI("stroke");
    starEmpty.setPosition(centerX - starEmpty.getWidth() / 2, boardImage.getY() + boardImage.getHeight() - starEmpty.getHeight() / 2);
    addActor(starEmpty);
  }

  private void addStar1() {
    star1 = skinManager.createImageUI("star1");
    star1.setPosition(starEmpty.getX() + starEmpty.getWidth() / 2 - star1.getWidth() / 2, starEmpty.getY() + starEmpty.getHeight() / 2 - star1.getHeight() / 2);
    addActor(star1);
  }

  private void addStar2() {
    star2 = skinManager.createImageUI("star2");
    star2.setPosition(starEmpty.getX() + starEmpty.getWidth() - star2.getWidth(), starEmpty.getY());
    addActor(star2);
  }

  private void addStar3() {
    star3 = skinManager.createImageUI("star3");
    star3.setPosition(starEmpty.getX(), starEmpty.getY());
    addActor(star3);
  }

  private void addBtnLeft() {
    btnLeft = buttonFactory.createButtonWood("btn_left", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {

      }
    });
    btnLeft.setPosition(btnHome.getX() - btnLeft.getWidth(), boardImage.getY() - btnLeft.getHeight() * 0.5f);
    addActor(btnLeft);
  }

  private void addBtnRight() {
    btnRight = buttonFactory.createButtonWood("btn_right", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {

      }
    });
    btnRight.setPosition(btnHome.getX() + btnHome.getWidth(), boardImage.getY() - btnRight.getHeight() * 0.5f);
    addActor(btnRight);
  }

  private void addBtnHome() {
    btnHome = buttonFactory.createButtonWood("btn_home", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(game.getHomeScreen());
        setVisible(false);
      }
    });
    btnHome.setPosition(centerX - btnHome.getWidth() / 2, boardImage.getY() - btnHome.getHeight() * 0.4f);
    addActor(btnHome);
  }

  public void setLabelWin(int core, int time) {
    Label levelLabel;
    levelLabel = new Label("Level " + level, labelStyle);
    levelLabel.setSize(starEmpty.getWidth(), 100);
    levelLabel.setFontScale(2.5f);
    levelLabel.setPosition(starEmpty.getX(), starEmpty.getY() - levelLabel.getHeight());
    levelLabel.setAlignment(Align.center);
    addActor(levelLabel);

    Label coreLabel;
    coreLabel = new Label("Core:   " + core, labelStyle);
    coreLabel.setSize(levelLabel.getWidth(), levelLabel.getHeight());
    coreLabel.setFontScale(3f);
    coreLabel.setPosition(levelLabel.getX(), levelLabel.getY() - levelLabel.getHeight() * 1.2f);
    coreLabel.setAlignment(Align.left);
    addActor(coreLabel);

    Label timeFinishLabel;
    timeFinishLabel = new Label("Time:   " + time, labelStyle);
    timeFinishLabel.setSize(coreLabel.getWidth(), coreLabel.getHeight());
    timeFinishLabel.setFontScale(3f);
    timeFinishLabel.setPosition(coreLabel.getX(), coreLabel.getY() - timeFinishLabel.getHeight());
    timeFinishLabel.setAlignment(Align.left);
    addActor(timeFinishLabel);

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

  public UiPopup(AssetManager assetManager, int type) {
    uiPopup = this;
    this.assetManager = assetManager;
  }


}
