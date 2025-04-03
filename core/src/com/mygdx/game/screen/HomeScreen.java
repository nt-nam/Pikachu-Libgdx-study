package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GMain;
import com.mygdx.game.data.GAssetsManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.GConstants;
import com.mygdx.game.utils.ButtonFactory;
import com.mygdx.game.utils.hud.AL;
import com.mygdx.game.utils.hud.Button;
import com.mygdx.game.utils.hud.MapGroup;
import com.mygdx.game.utils.hud.builders.BB;
import com.mygdx.game.utils.hud.builders.LB;
import com.mygdx.game.utils.hud.builders.MGB;


public class HomeScreen implements Screen {
  private GMain game;
  private final Stage stage;
  private GAssetsManager gAssetsManager;
  private Player player;

  public ButtonFactory getButtonFactory() {
    return buttonFactory;
  }

  private ButtonFactory buttonFactory;

  private ImageButton btnProfile, btnPlay, btnSetting, levelCenter;

  private Label.LabelStyle style;
//  private TextureAtlas ui;

  private float centerX, centerY;
  private boolean isdraw;
  int maxLevel;
  int levelCompleted;

  public HomeScreen(GMain game, Viewport viewport) {
    this.game = game;
    this.player = game.getPlayer();
    stage = new Stage(viewport);
    this.gAssetsManager = game.getAssetHelper();
    buttonFactory = new ButtonFactory(game.getSkinManager(), game.getSoundManager());
    maxLevel = 100;
    levelCompleted = 4;
    centerX = stage.getWidth() / 2;
    centerY = stage.getHeight() / 2;
    createAssetHome();
    isdraw = true;
    player.setLevel(levelCompleted);
  }

  private void createAssetHome() {
//    ui = GAssetsManager.get(player.getPathUi());
//    bitmapFont = GAssetsManager.get("font/arial_uni_30.fnt");
    style = new Label.LabelStyle();
    style.font = GMain.getAssetHelper().getBitmapFont("font/arial_uni_30.fnt");
    createScollPane();
//    createProfile();
    createBtnPlay();
    createBtnSetting();
  }

  private void createBtnSetting() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_UI_WOOD);
    Button setting = BB.New().transform(true).bg("btn_setting").scale(0.5f).pos(centerX * 1.5f, centerY * 1.7f).build();
    setting.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        UiPopup uiSetting = new UiPopup(game);
        uiSetting.setUiSetting();
//        uiSetting.setPosition((stage.getWidth() - uiSetting.getWidth() * 0.8f) * 0.5f, (stage.getHeight() - uiSetting.getHeight() * 0.8f) * 0.5f);
        uiSetting.setPosition(stage.getWidth()*0.1f, stage.getHeight()*0.1f, Align.bottomLeft);
        stage.addActor(uiSetting);
      }
    });
    stage.addActor(setting);
  }

  private void createBtnPlay() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_UI_WOOD);
    Button play = BB.New().transform(true).bg("btn_play").scale(0.75f).pos(centerX * 0.5f, -60).build();
    play.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getPlayScreen().setLevel(player.getLevel());
        game.setScreen(game.getPlayScreen());
      }
    });
    stage.addActor(play);
  }

  private void createProfile() {

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_UI_WOOD);
    Button btnProfile = BB.New().transform(true).bg("btn_home").scale(0.5f).pos(0,centerY*1.85f).build();
    stage.addActor(btnProfile);
  }

  private void createScollPane() {
    clearScrollPane();
    Table table = new Table();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_UI);
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

      MapGroup level = MGB.New().size(stage.getWidth(), 50).childs(
          BB.New().bg("btn_green")/*.label(n + "", "font/arial_uni_30", 0,0, AL.c).fontScale(2)*/.pos(x * 110 + centerX - 220, 0, AL.bl).idx(""),
          BB.New().bg("btn_red")/*.label(n + "", "font/arial_uni_30", 0,0, AL.c).fontScale(2)*/.pos(x * 110 + centerX - 220, 0, AL.bl).idx("disable").visible(false).touchable(false),
          LB.New().text(""+n).font("font/arial_uni_30").fontScale(2f).pos(x * 110 + centerX - 140, +10, AL.bl)
      ).build();

//        Actor btn = BB.New().bg("btn_green").label(n + "", "font/arial_uni_30", 0,0, AL.c).fontScale(2).pos(x * 110 + centerX - 220, 0, AL.bl).parent(level).build()  ;
      levelCenter = buttonFactory.createButtonWood("btn_level", new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          super.clicked(event, x, y);

        }
      });
      levelCenter.setSize(level.getWidth(), level.getHeight());
      if (n > levelCompleted) {
        level.query("disable", Button.class).setVisible(true);
      } else {
        level.addListener(new ClickListener() {
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

  private void clearScrollPane() {
    for (Actor a : stage.getActors()) {
      if (a instanceof ScrollPane) {
        a.remove();
      }
    }
  }


  @Override
  public void show() {

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
