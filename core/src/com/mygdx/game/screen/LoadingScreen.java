package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LoadingScreen implements Screen {
  Game game;
  Stage stage;
  AssetManager assetManager;
  public LoadingScreen(Game game,Stage stage){
    this.game = game;
    this.stage = stage;
    assetManager = new AssetManager();
  }
  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

//    game.setScreen(new HomeScreen(game, stage));

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
