package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screen.HomeScreen;
import com.mygdx.game.screen.LoadingScreen;
import com.mygdx.game.screen.PlayScreen;

public class PikachuGame extends Game {
  Stage stage;
  AssetManager assetManager;
  OrthographicCamera camera;
  Viewport viewport;
//  Preferences prefs = Gdx.app.getPreferences("Pika_vip");

  LoadingScreen loadingScreen;
  HomeScreen homeScreen;
  PlayScreen playScreen;

  @Override
  public void create() {
    assetManager = new AssetManager();

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    float worldWidth = 720;
    float worldHeight = worldWidth * screenHeight / screenWidth;

    camera = new OrthographicCamera(worldWidth, worldHeight);
    camera.setToOrtho(false);

    viewport = new FitViewport(worldWidth, worldHeight, camera);
    viewport.apply();

    stage = new Stage(viewport);
    Gdx.input.setInputProcessor(stage);
    loadAsset();

    initScreen();
  }

  public LoadingScreen getLoadingScreen() {

    return loadingScreen;
  }

  public PlayScreen getPlayScreen() {

    initPlayScreen();
    playScreen.resetScreen();
    return playScreen;
  }
  private void initPlayScreen(){
    if(playScreen == null){
      playScreen = new PlayScreen(this,viewport);
    }
  }
  private void initHomeScreen(){
    if(homeScreen == null){
      homeScreen = new HomeScreen(this,viewport);
    }
  }
  public HomeScreen getHomeScreen() {
    initHomeScreen();
    return homeScreen;
  }

  public AssetManager getAssetManager() {
    return assetManager;
  }

  private void initScreen() {
    loadingScreen = new LoadingScreen(this, viewport);
  }

  private void fileAtlas() {
    for (int i = 0; i < 36; i++) {
      System.out.println(i);
      System.out.println("  rotate: false");
      System.out.println("  xy: " + ((int) (i / 6) * (390 + 30) + 30) + ", " + ((i % 6) * (390 + 30) + 60));
      System.out.println("  size: 390, 390");
      System.out.println("  orig: 45, 45");
      System.out.println("  offset: 0, 0");
      System.out.println("  index: -1");
    }
  }

  private void loadAsset() {
    assetManager.load("textureAtlas/animals2.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui.atlas", TextureAtlas.class);
    assetManager.load("sound/bubble_fall.mp3", Sound.class);
    assetManager.load("font/arial_uni_30.fnt", BitmapFont.class);
    assetManager.finishLoading();
  }


}
