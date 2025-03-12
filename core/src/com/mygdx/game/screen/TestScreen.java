package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.utils.GameConstants;
import com.mygdx.game.view.Board;

public class TestScreen implements Screen {
  PikachuGame game;
  Stage stage;
  Board board;
  public TestScreen(PikachuGame game){
    this.game = game;
    stage = game.getStage();
    board = new Board(GameConstants.DEFAULT_ROWS,GameConstants.DEFAULT_COLS);
  }
  @Override
  public void show() {
    stage.addActor(board);
    System.out.println("mo Test Screen");
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.6f, 0.4f, 1);
    stage.draw();
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
