package com.mygame.pikachu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.model.Player;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.MGB;
import com.mygame.pikachu.utils.hud.external.EventHandler;
import com.mygame.pikachu.view.SparkleEffect;
import com.mygame.pikachu.view.ui.PopupUI;


public class HomeScreen implements Screen, EventHandler {
  private GMain game;
  private Player player;

  private MapGroup homeMG;
  private PopupUI popup;
  private SparkleEffect sparkleEffect;

  private float centerX, centerY;
  int levelCompleted;

  public HomeScreen(GMain game) {
    super();
    this.game = game;
    this.player = game.getPlayer();
    levelCompleted = 4;
    centerX = GMain.stage().getWidth() / 2;
    centerY = GMain.stage().getHeight() / 2;
    homeMG = new MapGroup(centerX * 2, centerY * 2);
    GMain.hud().addActor(homeMG);

    player.setLevel(levelCompleted);
    popup = new PopupUI(game);

    createAssetHome();
    addHandler();
  }

  private void createAssetHome() {
    createBG();
    createTitle();
    createBtnPlay();
    createTag();
    createMoreGame();
  }

  private void createBG() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    IB.New().drawable("bg").size(centerX*2,centerY*2).pos(0, 0, AL.c).parent(homeMG).build();
    sparkleEffect = new SparkleEffect(centerX*1.8f,centerY*1.8f);
    homeMG.addActor(sparkleEffect, Align.center);
    sparkleEffect.start();
  }

  private void createMoreGame() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    BB.New().bg("btnOther").pos(0, 0, AL.br).idx("btnOther").parent(homeMG).build();
  }

  private void createTitle() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_MENU);
    IB.New().drawable("logo").pos(0, 100, AL.ct).parent(homeMG).build().addAction(
        Actions.forever(Actions.sequence(
            Actions.scaleTo(1.1f, 1f, 2),
            Actions.scaleTo(1, 1, 2)
        )));


    createLight(0, 0, -30, 920);
    createLight(1, -150, -30, 1000);
    createLight(2, 100, -30, 950);
  }

  private void createLight(int id, int x, int y, float duration) {
    String path = "light";
    MapGroup line = MGB.New().size(121, 179).childs(
        IB.New().drawable(path + "off" + id).align(AL.ct),
        IB.New().drawable(path + "on" + id).idx("on").align(AL.ct)
    ).pos(x, y, AL.ct).origin(AL.ct).parent(homeMG).build();
    line.query("on", Image.class).addAction(
        Actions.forever(Actions.sequence(Actions.fadeOut(1), Actions.fadeIn(1)))
    );
    line.rotateBy(MathUtils.random(0, 30));
    line.addAction(Actions.forever(Actions.sequence(
        Actions.rotateTo(-15, duration * 2 / 1000, Interpolation.sine),
        Actions.rotateTo(15, duration * 2 / 1000, Interpolation.sine)
    )));
  }

  private void createTag() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    BB.New().bg("tag").label("Menu", GConstants.BMF, 0, 10, AL.c).pos(20, 10, AL.bl).idx("menu").parent(homeMG).build();
    BB.New().bg("tag").label("Shop", GConstants.BMF, 0, 10, AL.c).pos(20, 110, AL.bl).idx("shop").parent(homeMG).build();
  }

  private void createBtnPlay() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    BB.New().bg("btn_yellow").label("Play", GConstants.BMF, 0, 0, AL.c).origin(AL.c).pos(0, 0, AL.c).idx("btnPlay").parent(homeMG).build();
  }

  @Override
  public void show() {
    GMain.hud().addActor(homeMG);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl.glClearColor(0.4f, 0.5f, 0.4f, 1);
    GMain.stage().draw();
    GMain.stage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
    homeMG.remove();
  }

  @Override
  public void dispose() {
    homeMG.clear();
  }

  private void addHandler() {
    GMain.hud().index("mgHome", homeMG);
    GMain.hud().regisHandler("homeHandler", this);
    GMain.hud().clickConnect("mgHome/btnOther", "homeHandler", "showOtherGame", 3, player);
    GMain.hud().clickConnect("mgHome/btnPlay", "homeHandler", "playGame");
    GMain.hud().clickConnect("mgHome/menu", "homeHandler", "showMenu");
    GMain.hud().clickConnect("mgHome/shop", "homeHandler", "showShop");
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {
    switch (action) {
      case "showOtherGame":
//        System.out.println("show other game - " + intParam + " - " + ((Player) objParam).getLevel());
        GMain.debugManager().onDebugConsole();
        break;
      case "playGame":
        System.out.println("click play game");
        game.getPlayScreen().setLevel(player.getLevel());
        game.setScreen(game.getPlayScreen());
        break;
      case "showMenu":
        System.out.println("click menu");
        popup.showMenuUI();
        break;
      case "showShop":
        System.out.println("click shop");
        popup.showShopUI();
        break;
      default:

    }
  }
}
