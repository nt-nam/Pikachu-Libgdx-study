package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GMain;

public class LoadingScreen implements Screen {
  GMain game;
  UiPopup uiPopup;

  public LoadingScreen(GMain game, Viewport v) {
    this.game = game;
//    uiPopup = new UiPopup(game);
//    uiPopup.setUiWin(3, 1);
//    uiPopup.setLabelWin(300, 120);

//    uiPopup.setUiLose();

//    uiPopup.setUiSetting();

//    uiPopup.setUiPause();

//    uiPopup.setScale(0.8f);
//    uiPopup.setPosition(game.getStage().getWidth() *0.5f - uiPopup.getWidth() * 0.5f * 0.8f, game.getStage().getHeight()*0.5f - uiPopup.getHeight() * 0.5f * 0.8f);
//    uiPopup.updateOverlayImage();
  }


  @Override
  public void show() {
//    game.getStage().addActor(uiPopup);
    game.setScreen(game.getHomeScreen());
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    game.getStage().draw();
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