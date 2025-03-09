package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.PikachuGame;

public class LoadingScreen implements Screen {
  PikachuGame game;
  Stage stage;
  AssetManager assetManager;
  public LoadingScreen(PikachuGame game, Viewport viewport){
    this.game = game;
    stage = new Stage(viewport);
    assetManager = new AssetManager();
    loadAsset();
  }
  private void loadAsset() {
    assetManager.load("textureAtlas/animals2.atlas", TextureAtlas.class);
    assetManager.load("textureAtlas/ui.atlas", TextureAtlas.class);
    assetManager.load("sound/bubble_fall.mp3", Sound.class);
    assetManager.load("font/arial_uni_30.fnt", BitmapFont.class);
    assetManager.finishLoading();
    game.setScreen(game.getHomeScreen());
  }
  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }
}