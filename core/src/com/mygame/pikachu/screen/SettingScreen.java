package com.mygame.pikachu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.pikachu.GMain;

public class SettingScreen implements Screen {
  private GMain game;
  private Stage stage;
  private Viewport viewport;

  public SettingScreen(GMain game) {
    this.game = game;
    this.viewport = GMain.stage().getViewport();
    this.stage = new Stage(viewport);
    Gdx.input.setInputProcessor(stage);
    createUI();
  }

  private void createUI() {
    Image background = createOverlay(stage.getWidth(), stage.getHeight(), 0.8f, Color.GRAY);
    stage.addActor(background);

    Label.LabelStyle labelStyle = new Label.LabelStyle(GMain.getAssetHelper().getBitmapFont("font/arial_uni_30.fnt"), Color.WHITE);
    Label titleLabel = new Label("Settings", labelStyle);
    titleLabel.setFontScale(2f);
    titleLabel.setPosition(stage.getWidth() / 2 - titleLabel.getWidth() / 2, stage.getHeight() - 100);
    stage.addActor(titleLabel);

    TextButton.TextButtonStyle buttonStyle = createSimpleButtonStyle();
    TextButton musicButton = new TextButton("Music: ON", buttonStyle);
    musicButton.setPosition(stage.getWidth() / 2 - 150, stage.getHeight() / 2 + 50);
    musicButton.setSize(300, 60);
    musicButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean isMuted = game.getSoundManager().isMusicMuted();
        game.getSoundManager().setMusicMuted(!isMuted);
        musicButton.setText("Music: " + (isMuted ? "ON" : "OFF"));
      }
    });
    stage.addActor(musicButton);

    TextButton soundButton = new TextButton("Sound: ON", buttonStyle);
    soundButton.setPosition(stage.getWidth() / 2 - 150, stage.getHeight() / 2 - 50);
    soundButton.setSize(300, 60);
    soundButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean isMuted = game.getSoundManager().isSoundMuted();
        game.getSoundManager().setSoundMuted(!isMuted);
        soundButton.setText("Sound: " + (isMuted ? "ON" : "OFF"));
      }
    });
    stage.addActor(soundButton);

    TextButton backButton = new TextButton("Back", buttonStyle);
    backButton.setPosition(stage.getWidth() / 2 - 150, stage.getHeight() / 2 - 150);
    backButton.setSize(300, 60);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(game.getHomeScreen()); // Quay về HomeScreen
      }
    });
    stage.addActor(backButton);
  }

  private TextButton.TextButtonStyle createSimpleButtonStyle() {
    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.DARK_GRAY);
    pixmap.fill();
    Texture upTexture = new Texture(pixmap);

    pixmap.setColor(Color.LIGHT_GRAY);
    pixmap.fill();
    Texture downTexture = new Texture(pixmap);
    pixmap.dispose();

    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    style.up = new TextureRegionDrawable(upTexture);
    style.down = new TextureRegionDrawable(downTexture);
    style.font = GMain.getAssetHelper().getBitmapFont("font/arial_uni_30.fnt");
    style.fontColor = Color.WHITE;
    return style;
  }

  private Image createOverlay(float w, float h, float alpha, Color color) {
    color.a = alpha;
    Texture texture = createSolid(1, 1, color);
    Image image = new Image(texture);
    image.setSize(w, h);
    return image;
  }

  private Texture createSolid(int w, int h, Color color) {
    Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
    pixmap.setColor(color);
    pixmap.fillRectangle(0, 0, w, h);
    Texture texture = new Texture(pixmap);
    pixmap.dispose();
    return texture;
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
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
    stage.dispose();
  }
}