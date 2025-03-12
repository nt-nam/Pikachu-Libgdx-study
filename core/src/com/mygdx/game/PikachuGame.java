package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.data.AssetHelper;
import com.mygdx.game.model.Player;
import com.mygdx.game.screen.HomeScreen;
import com.mygdx.game.screen.LoadingScreen;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.screen.TestScreen;
import com.mygdx.game.utils.SkinManager;
import com.mygdx.game.utils.SoundManager;


public class PikachuGame extends Game {
  Stage stage;
  AssetHelper assetManager;
  OrthographicCamera camera;
  Viewport viewport;
  Player player;
  SkinManager skinManager;
  SoundManager soundManager;

  LoadingScreen loadingScreen;
  HomeScreen homeScreen;
  PlayScreen playScreen;
  TestScreen testScreen;
  MenuScreen menuScreen;
  

  @Override
  public void create() {
    assetManager = new AssetHelper();

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
    return playScreen;
  }
  private void initPlayScreen(){
    if(playScreen == null){
      playScreen = new PlayScreen(this);
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

  public AssetHelper getAssetHelper() {
    return assetManager;
  }

  public Stage getStage() {
    return stage;
  }

  public MenuScreen getMenuScreen() {
    if(menuScreen == null){
      menuScreen = new MenuScreen(this);
    }
    return menuScreen;
  }

  public void setMenuScreen(MenuScreen menuScreen) {
    this.menuScreen = menuScreen;
  }

  public TestScreen getTestScreen() {
    return testScreen;
  }

  public void setTestScreen(TestScreen testScreen) {
    this.testScreen = testScreen;
  }

  private void initScreen() {
    loadingScreen = new LoadingScreen(this,viewport);
    setScreen(loadingScreen);
  }

  private void loadAsset() {
    assetManager.load("textureAtlas/animals2.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui.atlas", TextureAtlas.class);
    assetManager.load("sound/bubble_fall.mp3", Sound.class);
    assetManager.load("font/arial_uni_30.fnt", BitmapFont.class);
    assetManager.finishLoading();
  }


  public Player getPlayer() {
    if(player == null){
      player = new Player();
    }
    return player;
  }

  public SkinManager getSkinManager() {
    if(skinManager == null){
      getPlayer();
      skinManager = new SkinManager(player);
    }
    return skinManager;
  }

  public SoundManager getSoundManager() {
    if(soundManager == null){
      soundManager = new SoundManager();
    }
    return soundManager;
  }
}