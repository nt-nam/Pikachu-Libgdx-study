package com.mygdx.game;

import static com.mygdx.game.utils.GConstants.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.data.GAssetsManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.screen.HomeScreen;
import com.mygdx.game.screen.LoadingScreen;
//import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.screen.SettingScreen;
import com.mygdx.game.utils.SkinManager;
import com.mygdx.game.utils.SoundManager;
import com.mygdx.game.utils.hud.BuilderBridge;
import com.mygdx.game.utils.hud.HUD;
import com.mygdx.game.utils.hud.builders.AbstractActorBuilder;


public class GMain extends Game {
  Stage stage;
  GAssetsManager assetManager;
  OrthographicCamera camera;
  Viewport viewport;
  Player player;
  SkinManager skinManager;
  SoundManager soundManager;

  LoadingScreen loadingScreen;
  HomeScreen homeScreen;
  PlayScreen playScreen;
  TestScreen testScreen;
  SettingScreen settingScreen;


  @Override
  public void create() {
    assetManager = new GAssetsManager();

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    float worldWidth = 720;
    float worldHeight = worldWidth * screenHeight / screenWidth;

    camera = new OrthographicCamera(worldWidth, worldHeight);
    camera.setToOrtho(false);

    viewport = new FitViewport(worldWidth, worldHeight, camera);
    viewport.apply();

    HUD hud = new HUD(screenHeight, screenWidth);
    AbstractActorBuilder.adapter = new BuilderBridge(hud);
    stage = new Stage(viewport);
    Gdx.input.setInputProcessor(stage);
    loadAsset();

    getPlayer();
    getSoundManager();
    playMusic();

    initScreen();
  }

  private void playMusic() {
    if(soundManager == null){
      soundManager = new SoundManager();
    }
    soundManager.playBackgroundMusic();
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
  public SettingScreen getSettingScreen() {
    return settingScreen;
  }
  public static GAssetsManager getAssetHelper(){
    return  ((GMain) Gdx.app.getApplicationListener()).assetManager;
  }

  public Stage getStage() {
    return stage;
  }

  public TestScreen getTestScreen() {
    return testScreen;
  }

  public void setTestScreen(TestScreen testScreen) {
    this.testScreen = testScreen;
  }

  private void initScreen() {
    loadingScreen = new LoadingScreen(this,viewport);
    settingScreen = new SettingScreen(this);
    setScreen(loadingScreen);
  }

  private void loadAsset() {
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_ANIMAL);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_UI_WOOD);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_BTN);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_UI);
    assetManager.loadBitmapFont(BMF+".fnt");
    assetManager.loadSound("sound/bubble_fall.mp3");
    assetManager.finishLoading();
  }


  public Player getPlayer() {
    if(player == null){
      player = new Player();
      player.load();
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
      soundManager.initFromPlayer(player);
    }
    return soundManager;
  }
}