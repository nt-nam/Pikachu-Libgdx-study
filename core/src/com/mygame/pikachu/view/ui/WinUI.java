package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.screen.widget.BorderPM;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.LB;
import com.mygame.pikachu.utils.hud.builders.MGB;

public class WinUI extends BaseUI {

  public WinUI(Screen screen) {
    super(screen);
  }

  @Override
  protected void createUI() {
    miniUI();
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    IB.New().drawable("victory_en").pos(0, 20, AL.ct).parent(this).build();

    MGB.New().size(300, 100).childs(
            IB.New().texture(new Texture(new BorderPM().get(300, 90,30, 0x008080FF))).pos(0, 0, AL.c),
            LB.New().font(GConstants.BMF).text("Coin : " + GMain.player().getCoins()).pos(20, 0, AL.cl)
        ).pos(0, 50, AL.c)
        .idx("txtCoin")
        .parent(this).build();
    MGB.New().size(300, 100).childs(
            IB.New().texture(new Texture(new BorderPM().get(300, 90,30, 0x008080FF))).size(300, 100).pos(0, 0, AL.c),
            LB.New().font(GConstants.BMF).text("Score : " + GMain.player().getScore()).pos(20, 0, AL.cl)
        ).pos(0, -50, AL.c)
        .idx("txtScore")
        .parent(this).build();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    BB.New().bg("btn_red").label("Next", GConstants.BMF, 0, 0, AL.c)
        .pos(centerX * 0.2f, 50, AL.br)
        .idx("btnNextLevel")
        .parent(this).build();
//    BB.New().bg("btn_red").label("Home", GConstants.BMF, 0, 0, AL.c)
//        .pos(centerX * 0.2f, 50, AL.br)
//        .idx("btnHome")
//        .parent(this).build();
    BB.New().bg("btn_red").label("Restart", GConstants.BMF, 0, 0, AL.c)
        .pos(centerX * 0.2f, 50, AL.bl)
        .idx("btnRestart")
        .parent(this).build();
  }

  @Override
  protected void addHandle() {
    GMain.hud().index("winUI",this);
    GMain.hud().regisHandler("winHandler",this);
    GMain.hud().clickConnect("winUI/btnNextLevel", "winHandler", "NextLevelAction");
    GMain.hud().clickConnect("winUI/btnHome", "winHandler", "HomeAction");
    GMain.hud().clickConnect("winUI/btnRestart", "winHandler", "RestartAction");
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {
    switch (action) {
      case "NextLevelAction":
        System.out.println("NextLevelAction Click");
        if(game.getPlayScreen().getLevel() == game.getPlayer().getLevel()){
          game.getPlayer().setLevel(game.getPlayScreen().getLevel()+1);
          game.getPlayer().save();
        }
        game.getPlayScreen().hide();
        game.getPlayScreen().setLevel(game.getPlayScreen().getLevel()+1);
        game.getPlayScreen().show();
        hide();
        break;
      case "RestartAction":
        System.out.println("RestartAction Click");
//        game.getPlayScreen().setLevel(GMain.player().getLevel());
        game.getPlayScreen().restart();
        hide();
        break;
      case "HomeAction":
        System.out.println("Home Click Action");
        hide();
        game.getPlayScreen().hide();
        game.setScreen(game.getHomeScreen());
        break;
    }
  }
}