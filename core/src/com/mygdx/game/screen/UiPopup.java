package com.mygdx.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.PikachuGame;

public class UiPopup extends Group {
  public static UiPopup me;
  PikachuGame game;
  AssetManager assetManager;
  Image starEmpty0, starEmpty1, starEmpty2, board, boardCoinMainMenu, btnBack, btnReplay, btnResume, coin, heart, popup, ribbonBlue, ribbonFailed;
  ImageButton button;
  Sound soundClick;
  BitmapFont font;
  Label label;
  float centerX, centerY;
  private boolean type;

  public UiPopup(PikachuGame game, AssetManager assetManager, boolean type) {
    me= this;
    this.game = game;
    this.assetManager = assetManager;
    createAsset();
    this.type = type;
    showAssetByType();
  }
  public UiPopup( AssetManager assetManager, boolean type) {
    me= this;
    this.assetManager = assetManager;
    createAsset();
    this.type = type;
    showAssetByType();
  }
  private void showAssetByType() {
    this.addActor(popup);
    this.addActor(board);
    if(type){

      this.addActor(btnResume);
      this.addActor(ribbonBlue);
    }else {

      this.addActor(ribbonFailed);
    }
    this.addActor(btnReplay);
    this.addActor(btnBack);
    this.addActor(starEmpty0);
    this.addActor(starEmpty1);
    this.addActor(starEmpty2);
    this.addActor(button);
  }

  private void createAsset() {
    TextureAtlas ui = assetManager.get("textureAtlas/ui.atlas");
    popup = new Image(new TextureRegion(ui.findRegion("popup")));
    this.setSize(popup.getWidth(), popup.getHeight());
    centerX = this.getWidth() / 2;
    centerY = this.getHeight() / 2;

    board = new Image(new TextureRegion(ui.findRegion("board")));
    board.setPosition(centerX - board.getWidth() / 2, centerY - board.getHeight() / 2 + 50);

    boardCoinMainMenu = new Image(new TextureRegion(ui.findRegion("board_coin_mainmenu")));
//this.addActor(boardCoinMainMenu);
    btnReplay = new Image(new TextureRegion(ui.findRegion("btn_replay")));
    btnReplay.setPosition(centerX - btnReplay.getWidth() / 2, 50);
    btnReplay.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        soundClick.play();
        super.clicked(event, x, y);
      }
    });

    btnBack = new Image(new TextureRegion(ui.findRegion("btn_back")));
    btnBack.setPosition(centerX - btnBack.getWidth() - btnReplay.getWidth() / 2, 50);
    btnBack.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        soundClick.play();
        super.clicked(event, x, y);
      }
    });

    btnResume = new Image(new TextureRegion(ui.findRegion("btn_resume")));
    btnResume.setPosition(centerX + btnReplay.getWidth() / 2, 50);
    btnResume.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        soundClick.play();
        super.clicked(event, x, y);
      }
    });

    coin = new Image(new TextureRegion(ui.findRegion("coin")));
    heart = new Image(new TextureRegion(ui.findRegion("heart")));

    ribbonBlue = new Image(new TextureRegion(ui.findRegion("ribbon_blue")));
    ribbonBlue.setPosition(centerX - ribbonBlue.getWidth() / 2, this.getHeight() - ribbonBlue.getHeight() / 2);

    ribbonFailed = new Image(new TextureRegion(ui.findRegion("ribbon_failed")));
    ribbonFailed.setPosition(centerX - ribbonFailed.getWidth() / 2, this.getHeight() - ribbonFailed.getHeight() / 2);

    starEmpty0 = new Image(new TextureRegion(ui.findRegion("Star_empty")));
    starEmpty0.setPosition(centerX - starEmpty0.getWidth() / 2, this.getHeight());

    starEmpty1 = new Image(new TextureRegion(ui.findRegion("Star_empty")));
    starEmpty1.setPosition(centerX - starEmpty1.getWidth() * 2, this.getHeight());

    starEmpty2 = new Image(new TextureRegion(ui.findRegion("Star_empty")));
    starEmpty2.setPosition(centerX + starEmpty2.getWidth(), this.getHeight());

    soundClick = assetManager.get("sound/bubble_fall.mp3");

    font = assetManager.get("font/arial_uni_30.fnt");
    label = new Label("Click!!!", new Label.LabelStyle(font, Color.GRAY));
    label.setPosition(105, 65);

    TextureRegionDrawable drawableUp = new TextureRegionDrawable(ui.findRegion("btn_green"));
    TextureRegionDrawable drawableDown = new TextureRegionDrawable(ui.findRegion("btn_red"));

    button = new ImageButton(drawableUp, drawableDown);
    button.setSize(300, 150);
    button.setPosition(centerX - button.getWidth() / 2, centerY - button.getHeight() / 2 + 50);
    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        soundClick.play();
        super.clicked(event, x, y);
      }
    });
    button.addActor(label);
    this.setBounds(0, 0, popup.getImageWidth(), popup.getImageHeight());


  }

  public float getCenterX() {
    return centerX;
  }

  public float getCenterY() {
    return centerY;
  }

}
