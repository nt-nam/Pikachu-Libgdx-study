package com.mygame.pikachu;

import static com.mygame.pikachu.utils.GConstants.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.model.Player;
import com.mygame.pikachu.screen.HomeScreen;
import com.mygame.pikachu.screen.LoadingScreen;
import com.mygame.pikachu.screen.PlayScreen;
import com.mygame.pikachu.screen.SettingScreen;
import com.mygame.pikachu.utils.SkinManager;
import com.mygame.pikachu.utils.SoundManager;
import com.mygame.pikachu.utils.hud.BuilderBridge;
import com.mygame.pikachu.utils.hud.HUD;
import com.mygame.pikachu.utils.hud.builders.AbstractActorBuilder;


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
  SettingScreen settingScreen;


  @Override
  public void create() {
    assetManager = new GAssetsManager();

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    float worldHeight = 720;
    float worldWidth = worldHeight * screenWidth / screenHeight;

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
    if (soundManager == null) {
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

  private void initPlayScreen() {
    if (playScreen == null) {
      playScreen = new PlayScreen(this);
    }
  }

  private void initHomeScreen() {
    if (homeScreen == null) {
      homeScreen = new HomeScreen(this, viewport);
    }
  }


  public HomeScreen getHomeScreen() {
    initHomeScreen();
    return homeScreen;
  }

  public SettingScreen getSettingScreen() {
    return settingScreen;
  }

  public static GAssetsManager getAssetHelper() {
    return ((GMain) Gdx.app.getApplicationListener()).assetManager;
  }

  public Stage getStage() {
    return stage;
  }


  private void initScreen() {
    loadingScreen = new LoadingScreen(this, viewport);
    settingScreen = new SettingScreen(this);
    setScreen(loadingScreen);
  }

  private void loadAsset() {
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_ANIMAL);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_UI_WOOD);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_BTN);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_UI);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_LEADER_BOARD);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_ANIMALS);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_COMMON);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_CROSS);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_PARTICLE);
    assetManager.loadTextureAtlas(DEFAULT_ATLAS_PLAY);
    assetManager.loadBitmapFont(BMF + ".fnt");
    assetManager.loadSound("sound/bubble_fall.mp3");
    assetManager.finishLoading();
  }


  public Player getPlayer() {
    if (player == null) {
      player = new Player();
      player.load();
    }
    return player;
  }

  public SkinManager getSkinManager() {
    if (skinManager == null) {
      getPlayer();
      skinManager = new SkinManager(player);
    }
    return skinManager;
  }

  public SoundManager getSoundManager() {
    if (soundManager == null) {
      soundManager = new SoundManager();
      soundManager.initFromPlayer(player);
    }
    return soundManager;
  }
}