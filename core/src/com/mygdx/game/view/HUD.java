package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
  private Stage stage;
  private Viewport viewport;
  private PikachuGame game;
  private Label scoreLabel;
  private Label timeLabel;
  private int score;
  private float timeCount;
  private float maxTime;

  public HUD(PikachuGame game, Viewport viewport) {
    this.game = game;
    this.viewport = viewport;
    this.stage = new Stage(viewport);
    this.score = 0;
    this.timeCount = 0;
    this.maxTime = 300; // 5 minutes in seconds

    Table table = new Table();
    table.top();
    table.setFillParent(true);

    scoreLabel = new Label("Score: " + score, new Label.LabelStyle(game.getAssetHelper().get("font/arial_uni_30.fnt", BitmapFont.class), Color.WHITE));
    timeLabel = new Label("Time: " + (int)timeCount, new Label.LabelStyle(game.getAssetHelper().get("font/arial_uni_30.fnt", BitmapFont.class), Color.WHITE));

    table.add(scoreLabel).expandX().padTop(10);
    table.add(timeLabel).expandX().padTop(10);

    stage.addActor(table);
  }

  public void update(float dt) {
    timeCount += dt;
    timeLabel.setText("Time: " + (int)timeCount);
  }

  public void addScore(int value) {
    score += value;
    scoreLabel.setText("Score: " + score);
  }

  public Stage getStage() {
    return stage;
  }

  public void dispose() {
    stage.dispose();
  }

  public boolean isTimeUp() {
    return timeCount >= maxTime;
  }

  public int getScore() {
    return score;
  }

}