package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.SkinManager;
import com.mygdx.game.utils.SoundManager;
import com.mygdx.game.view.ButtonFactory;

public class MenuScreen implements Screen {
  private PikachuGame game;
  private Player player;
  private Stage stage;
  private SkinManager skinManager;
  private SoundManager soundManager;
  private ButtonFactory buttonFactory;
  private BitmapFont font;
  private int maxLevel = 100;

  public MenuScreen(PikachuGame game) {
    this.game = game;
    this.player = game.getPlayer()
    ;
    this.stage = new Stage(game.getStage().getViewport());
    this.skinManager = game.getSkinManager();
    this.soundManager = game.getSoundManager();
    this.buttonFactory = new ButtonFactory(skinManager, soundManager);
    this.font = game.getAssetHelper().get("font/arial_uni_30.fnt", BitmapFont.class);
    createMenu();
  }

  private void createMenu() {
//    soundManager.playBackgroundMusic();

    // Tạo nút chính
    float centerX = stage.getWidth() / 2;
    float centerY = stage.getHeight() / 2;

    ImageButton playButton = buttonFactory.createButton("play_button", new Runnable() {
      @Override
      public void run() {
        game.getPlayScreen().setLevel(player.getLevel());
        game.setScreen(game.getPlayScreen());
      }
    });
    playButton.setPosition(centerX - 50, centerY + 50);
    stage.addActor(playButton);

    ImageButton settingsButton = buttonFactory.createSettingsButton(new Runnable() {
      @Override
      public void run() {
        // TODO: Tạo SettingsScreen và chuyển sang
        Gdx.app.log("MenuScreen", "Settings clicked");
      }
    });
    settingsButton.setPosition(centerX - 50, centerY - 20);
    stage.addActor(settingsButton);

    ImageButton profileButton = buttonFactory.createButton("profile_button", new Runnable() {
      @Override
      public void run() {
        createLevelScrollPane(); // Hiển thị danh sách cấp độ
      }
    });
    profileButton.setPosition(centerX - 50, centerY - 90);
    stage.addActor(profileButton);

    ImageButton exitButton = buttonFactory.createButton("exit_button", new Runnable() {
      @Override
      public void run() {
        Gdx.app.exit();
      }
    });
    exitButton.setPosition(centerX - 50, centerY - 160);
    stage.addActor(exitButton);

    Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
    Label titleLabel = new Label("Pikachu 2003", style);
    titleLabel.setFontScale(2f);
    titleLabel.setPosition(centerX - 100, centerY + 150, Align.center);
    stage.addActor(titleLabel);
  }

  private void createLevelScrollPane() {
    // Xóa các ScrollPane cũ
    for (int i = stage.getActors().size - 1; i >= 0; i--) {
      if (stage.getActors().get(i) instanceof ScrollPane) {
        stage.getActors().removeIndex(i);
      }
    }

    // Tạo danh sách cấp độ
    Table table = new Table();
    table.defaults().pad(10).width(100).height(100);

    int levelsPerRow = 5; // 5 cấp độ mỗi hàng
    for (int n = 1; n <= maxLevel; n++) {
      final int level = n;
      Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
      TextureAtlas.AtlasRegion buttonRegion = skinManager.getButtonTexture("btn_green");
      ImageButton levelButton = new ImageButton(new TextureRegionDrawable(buttonRegion));
      Label levelLabel = new Label("" + n, style);
      levelLabel.setAlignment(Align.center);
      levelLabel.setFontScale(1.5f);
      levelButton.getImageCell().expand().fill();
      levelButton.add(levelLabel);

      if (level > player.getLevel()) {
        levelButton.setColor(0.4f, 0.4f, 0.4f, 0.8f); // Làm mờ cấp độ chưa mở
      } else {
        levelButton.addListener(new ClickListener() {
          @Override
          public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
            game.getPlayScreen().setLevel(level);
            player.setLevel(level); // Cập nhật level trong Player
            player.save();          // Lưu lại tiến độ
            game.setScreen(game.getPlayScreen());
          }
        });
      }

      table.add(levelButton);
      if (n % levelsPerRow == 0) table.row();
    }

    ScrollPane scrollPane = new ScrollPane(table);
    scrollPane.setSize(stage.getWidth(), stage.getHeight() - 100);
    scrollPane.setScrollingDisabled(true, false);
    scrollPane.setPosition(0, 0);
    stage.addActor(scrollPane);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.5f, 0.4f, 1);
    stage.act(Math.min(delta, 1 / 30f));
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
    player.save(); // Lưu tiến độ khi thoát MenuScreen
  }
}