package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.data.AssetHelper;
import com.mygdx.game.data.LevelManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.ButtonFactory;


public class HomeScreen implements Screen {
  private PikachuGame game;
  private final Stage stage;
  private AssetHelper assetHelper;
  private Player player;
  private ButtonFactory buttonFactory;

  private ImageButton btnProfile;
  private Image blockLevel;
  private BitmapFont bitmapFont;
  private Label labelLevel;
  private Label.LabelStyle style;
  private TextureAtlas ui;

  private float centerX, centerY;
  private boolean isdraw;
  int maxLevel;
  int levelCompleted;

  public HomeScreen(PikachuGame game, Viewport viewport) {
    this.game = game;
    this.player = game.getPlayer();
    stage = new Stage(viewport);
    this.assetHelper = game.getAssetHelper();
    buttonFactory = new ButtonFactory(game.getSkinManager(),game.getSoundManager());
    maxLevel = 100;
    levelCompleted = 4;
    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;
    createAssetHome();
    isdraw = true;
  }

  private void createAssetHome() {
    ui = assetHelper.get(player.getPathUi());
    bitmapFont = assetHelper.get("font/arial_uni_30.fnt");
    style = new Label.LabelStyle();
    style.font = bitmapFont;
    createProfile();

  }

  private void createProfile() {
    btnProfile = buttonFactory.createProfileButton(player);
//    btnProfile = new Image(new TextureRegion(ui.findRegion("btn_blue")));
    btnProfile.setBounds(0, centerY * 2 - 100, 100, 100);
    btnProfile.setColor(1f, 1f, 1f, 1f);
    Label label = new Label("Profile", style);
    label.setBounds(10, centerY * 2 - 100, 100, 100);
    stage.addActor(btnProfile);
//    stage.addActor(label);
  }

  private void createScollPane() {
    clearStage();
    Table table = new Table();

    for (int n = 1; n < maxLevel; n++) {
      int i = 0, j = 0;
      final int nlevel = n;

      switch (n % 6) {
        case 0:
          i = 2;
          j = 0;
          break;
        case 1:
          i = 1;
          j = 0;
          break;
        case 2:
          i = 0;
          j = 1;
          break;
        case 3:
          i = 1;
          j = 2;
          break;
        case 4:
          i = 2;
          j = 2;
          break;
        case 5:
          i = 3;
          j = 3;
          break;
        default:
      }

      int x = i;
      int y = (n / 6) * 4 + j;

      Group level = new Group();
      level.setSize(stage.getWidth(), 100);
      blockLevel = new Image(new TextureRegion(ui.findRegion("btn_green")));
      blockLevel.setX(x * 110 + centerX - 220);
      labelLevel = new Label("" + n, style);
      labelLevel.setBounds(blockLevel.getX(), blockLevel.getY() + 5, blockLevel.getWidth(), blockLevel.getHeight());
      labelLevel.setFontScale(2f);
      labelLevel.setAlignment(Align.center);
      level.addActor(blockLevel);
      level.addActor(labelLevel);
      if (n > levelCompleted) {
        blockLevel.setColor(0.4f, 0.4f, 0.4f, 0.8f);
      } else {
        labelLevel.addListener(new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            game.getPlayScreen().setLevel(nlevel);
            game.setScreen(game.getPlayScreen());
          }
        });
      }

      table.add(level);
      table.row();
    }

    ScrollPane scrollPane = new ScrollPane(table);
    scrollPane.setSize(stage.getWidth(), stage.getHeight() - 100);
    scrollPane.setScrollingDisabled(true, false);
    stage.addActor(scrollPane);
  }

  private void clearStage() {
    for (Actor a : stage.getActors()) {
      if (a instanceof ScrollPane) {
        a.remove();
      }
    }
  }




  @Override
  public void show() {
    createScollPane();
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.5f, 0.4f, 1);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//    hud.render();
    if (isdraw) stage.draw();
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
    stage.dispose();
  }
}
