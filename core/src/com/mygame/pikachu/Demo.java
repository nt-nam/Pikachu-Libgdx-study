package com.mygame.pikachu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.pikachu.screen.UiPopup;

public class Demo extends Game {
  AssetManager assetManager;
  Viewport viewport;
  OrthographicCamera cam;
  Stage stage;
  Image img;

  UiPopup winPopup;

  @Override
  public void create() {
    assetManager = new AssetManager();
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    float worldHeight = 720;
    float worldWidth = worldHeight * screenWidth / screenHeight;

    cam = new OrthographicCamera(worldWidth, worldHeight);
    cam.setToOrtho(false);

    viewport = new FitViewport(worldWidth, worldHeight, cam);
    viewport.apply();

    stage = new Stage(viewport);
    Gdx.input.setInputProcessor(stage);

    assetManager.load("textureAtlas/ani/ani0.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui/ui0.atlas", TextureAtlas.class);
    assetManager.load("sound/bubble_fall.mp3", Sound.class);
    assetManager.load("font/arial_uni_30.fnt", BitmapFont.class);
    assetManager.finishLoading();
//    winPopup = new UiPopup(assetManager,0);

    TextureAtlas animals = assetManager.get("textureAtlas/ani/ani0.atlas");
    img = new Image(new TextureRegion(animals.findRegion("cucxilau1"))) {
      @Override
      public void act(float delta) {
        this.setRotation(this.getRotation() + 100 * delta);
        this.setX(getX() + 1);
        super.act(delta);
      }
    };
    img.setOrigin(Align.center);
    winPopup.scaleBy(0.5f,0.5f);
//    winPopup.setPosition(worldWidth/2 - winPopup.getCenterX(),worldHeight/2 - winPopup.getCenterY());
    stage.addActor(winPopup);

  }

  @Override
  public void render() {
    ScreenUtils.clear(Color.GRAY);
    float dt = Gdx.graphics.getDeltaTime();
    stage.act(dt);
    stage.draw();
    super.render();
  }

  @Override
  public void dispose() {
    stage.dispose();
    assetManager.dispose();
    super.dispose();
  }
}
