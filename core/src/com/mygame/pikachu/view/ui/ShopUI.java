package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.screen.widget.BorderPM;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.LB;
import com.mygame.pikachu.utils.hud.builders.MGB;

public class ShopUI extends BaseUI {
  public ShopUI(GMain game) {
    super(game);
  }

  @Override
  protected void createUI() {
    int w = (int) (centerX * 1.4f);
    int h = 100;
    int cornerRadius = 20;
    int color = 0x606060FF;

    miniUI();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().drawable("ribbon").scale(0.9f, 1).pos(0, -30, AL.ct).parent(this).build();
    LB.New().font(GConstants.BMF).text("SHOP").fontScale(2).pos(0,-15,AL.ct).parent(this).build();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    BB.New().bg("btn_exit").pos(0, -40, AL.tr).idx("btnClose").parent(this).build();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    MapGroup buyHint = MGB.New().size(w, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(w, h, cornerRadius, color))).pos(0, 0, AL.c),
        IB.New().drawable("hint").pos(20, 0, AL.cl).debug(false),
        LB.New().font(GConstants.BMF).text("Hint: " + GMain.player().getHints()).pos(-30, 0, AL.c)
    ).pos(0, 110, AL.c).idx("buyHint").parent(this).debug(false).build();
    GMain.hud().index("buyHint", buyHint);


    MapGroup buyShuffle = MGB.New().size(w, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(w, h, cornerRadius, color))).pos(0, 0, AL.c),
        IB.New().drawable("shuffle").pos(20, 0, AL.cl).debug(false),
        LB.New().font(GConstants.BMF).text("Shuffle: " + GMain.player().getShuffles()).pos(-30, 0, AL.c)
    ).pos(0, -10, AL.c).idx("buyShuffle").parent(this).build();
    GMain.hud().index("buyShuffle", buyShuffle);


    MapGroup buyRocket = MGB.New().size(w, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(w, h, cornerRadius, color))).pos(0, 0, AL.c),
        IB.New().drawable("boom").pos(20, 0, AL.cl).debug(false),
        LB.New().font(GConstants.BMF).text("Rocket: " + GMain.player().getRockets()).pos(-30, 0, AL.c)
    ).pos(0, -130, AL.c).idx("buyRocket").parent(this).build();
    GMain.hud().index("buyRocket", buyRocket);


    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    BB.New().bg("btn_yellow").transform(true).label("Buy", GConstants.BMF, 0, 0, AL.c).fontScale(1.5f).size(218, 91).pos(-20, 0, AL.cr).scale(0.75f).idx("btnBuyH").parent(buyHint).build();
    BB.New().bg("btn_yellow").transform(true).label("Buy", GConstants.BMF, 0, 0, AL.c).fontScale(1.5f).size(218, 91).pos(-20, 0, AL.cr).scale(0.75f).idx("btnBuyS").parent(buyShuffle).build();
    BB.New().bg("btn_yellow").transform(true).label("Buy", GConstants.BMF, 0, 0, AL.c).fontScale(1.5f).size(218, 91).pos(-20, 0, AL.cr).scale(0.75f).idx("btnBuyR").parent(buyRocket).build();
debugAll();
  }

  @Override
  protected void addHandle() {
    GMain.hud().index("menuUI", this);
    GMain.hud().regisHandler("menuHandle", this);
    GMain.hud().clickConnect("menuUI/btnClose", "menuHandle", "closeAction");
    GMain.hud().clickConnect("menuUI/buyHint/btnBuyH", "menuHandle", "buyHint");
    GMain.hud().clickConnect("menuUI/buyShuffle/btnBuyS", "menuHandle", "buyShuffle");
    GMain.hud().clickConnect("menuUI/buyRocket/btnBuyR", "menuHandle", "buyRocket");
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {
    switch (action) {
      case "closeAction":
        System.out.println("click CloseShop");
        hide();
        break;
      case "buyHint":
        System.out.println("click buyHint");
        GMain.player().buyHint();
        break;
      case "buyShuffle":
        System.out.println("click buyShuffle");
        GMain.player().buyShuffle();
        break;
      case "buyRocket":
        System.out.println("click buyRocket");
        GMain.player().buyRocket();
        break;
    }
  }
}
