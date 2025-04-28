package com.mygame.pikachu.view.ui;

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

  public WinUI(GMain game) {
    super(game);
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
    BB.New().bg("btn_red").label("Next Level", GConstants.BMF, 0, 0, AL.c)
        .pos(centerX * 0.2f, 50, AL.bl)
        .idx("btnNextLevel")
        .parent(this).build();
    BB.New().bg("btn_red").label("Home", GConstants.BMF, 0, 0, AL.c)
        .pos(centerX * 0.2f, 50, AL.br)
        .idx("btnHome")
        .parent(this).build();
    BB.New().bg("btn_red").label("Restart Level", GConstants.BMF, 0, 0, AL.c)
        .pos(0, -100, AL.c)
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
    GMain g = GMain.instance();
    switch (action) {
      case "NextLevelAction":
        System.out.println("NextLevelAction Click");
        break;
      case "RestartAction":
        System.out.println("RestartAction Click");
        GMain.player().save();
        g.getPlayScreen().setLevel(GMain.player().getLevel());
        break;
      case "HomeAction":
        System.out.println("Home Click Action");
        hide();
        g.getPlayScreen().hide();
        g.setScreen(g.getHomeScreen());
        break;
    }
  }
}