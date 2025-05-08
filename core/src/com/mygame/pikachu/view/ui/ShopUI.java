package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
  public ShopUI(Screen screen) {
    super(screen);
  }

  @Override
  protected void createUI() {
    int w = (int) (centerX * 1.4f);
    int h = 100;
    int cornerRadius = 20;
    int grayColor = 0x606060FF;
    int blueColor = 0x6EC2F7FF;
    int orangeColor = 0xFF7F50FF;
    int redColor = 0xCD5C5CFF;
    int brownColor2 = 0x964800FF;
    int brownColor1 = 0xB87333FF;
    int brownColor3 = 0xBA5536FF;
    int brownColor4 = 0xA43820FF;
    int brownColor5 = 0x974B00FF;

    miniUI();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().drawable("ribbon").scale(0.9f, 1).pos(0, -30, AL.ct).parent(this).build();
    LB.New().font(GConstants.BMF).text("SHOP").fontScale(2).pos(0, -15, AL.ct).parent(this).build();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    BB.New().bg("btn_exit").pos(0, -40, AL.tr).idx("btnClose").parent(this).build();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    MapGroup buyHint = MGB.New().size(w, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(w, h, cornerRadius, grayColor))).pos(0, 0, AL.c),
        IB.New().texture(new Texture(new BorderPM().get(w * 21 / 100, h, cornerRadius, redColor))).pos(0, 0, AL.cl),
        IB.New().drawable("hint").pos(20, 0, AL.cl).debug(false),
        LB.New().font(GConstants.BMF).text("Hint:           " + GMain.player().getHints()).pos(w * 0.25f, 0, AL.cl).idx("hintLB")
    ).pos(0, 110, AL.c).idx("buyHint").parent(this).debug(false).build();
    GMain.hud().index("buyHint", buyHint);


    MapGroup buyShuffle = MGB.New().size(w, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(w, h, cornerRadius, grayColor))).pos(0, 0, AL.c),
        IB.New().texture(new Texture(new BorderPM().get(w * 21 / 100, h, cornerRadius, blueColor))).pos(0, 0, AL.cl),
        IB.New().drawable("shuffle").pos(20, 0, AL.cl).debug(false),
        LB.New().font(GConstants.BMF).text("Shuffle:     " + GMain.player().getShuffles()).pos(w * 0.25f, 0, AL.cl).idx("shuffleLB")
    ).pos(0, -10, AL.c).idx("buyShuffle").parent(this).build();
    GMain.hud().index("buyShuffle", buyShuffle);


    MapGroup buyRocket = MGB.New().size(w, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(w, h, cornerRadius, grayColor))).pos(0, 0, AL.c),
        IB.New().texture(new Texture(new BorderPM().get(w * 21 / 100, h, cornerRadius, orangeColor))).pos(0, 0, AL.cl),
        IB.New().drawable("boom").pos(20, 0, AL.cl).debug(false),
        LB.New().font(GConstants.BMF).text("Rocket:     " + GMain.player().getRockets()).pos(w * 0.25f, 0, AL.cl).idx("rocketLB")
    ).pos(0, -130, AL.c).idx("buyRocket").parent(this).build();
    GMain.hud().index("buyRocket", buyRocket);

//    MGB.New().size(196, 96).childs(
//        IB.New().texture(new Texture(new BorderPM().get(196, 64, 30, 0x696969FF))).origin(AL.cl).pos(0, 0, AL.cl),
//        IB.New().drawable("coin").idx("coinOrigin").pos(0, 0, AL.cr).scale(0.9f),
//        LB.New().font(GConstants.BMF).text(GMain.player().getCoins() + "").fontScale(1f).pos(25, 0, AL.cl).idx("coinLB")
//    ).origin(AL.ct).pos(0, 20, AL.cb).idx("coinT").parent(this).debug(false).build();


//    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
//    BB.New().bg("btn_yellow").transform(true).label("" + GConstants.HINT_COST, GConstants.BMF, 0, 0, AL.c).fontScale(1.5f).size(218, 91).pos(-20, 0, AL.cr).scale(0.75f).idx("btnBuyH").parent(buyHint).build();
//    BB.New().bg("btn_yellow").transform(true).label("" + GConstants.SHUFFLE_COST, GConstants.BMF, 0, 0, AL.c).fontScale(1.5f).size(218, 91).pos(-20, 0, AL.cr).scale(0.75f).idx("btnBuyS").parent(buyShuffle).build();
//    BB.New().bg("btn_yellow").transform(true).label("" + GConstants.ROCKET_COST, GConstants.BMF, 0, 0, AL.c).fontScale(1.5f).size(218, 91).pos(-20, 0, AL.cr).scale(0.75f).idx("btnBuyR").parent(buyRocket).build();

    MGB.New().size(w * 0.25f, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(h, h * 8 / 10, 25, brownColor5))).pos(0, 0, AL.c),
        IB.New().texture(new Texture(new BorderPM().get(h * 9 / 10, h * 7 / 10, 25, brownColor3))).pos(0, 0, AL.c),
        LB.New().font(GConstants.BMF).text("" + GConstants.HINT_COST).pos(30, 0, AL.cl).idx("coinLB"),
        IB.New().drawable("coin").idx("c").pos(10, 0, AL.cr).scale(0.5f)
    ).pos(10, 0, AL.cr).idx("btnBuyH").parent(buyHint).build();


    MGB.New().size(w * 0.25f, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(h, h * 8 / 10, 25, brownColor5))).pos(0, 0, AL.c),
        IB.New().texture(new Texture(new BorderPM().get(h * 9 / 10, h * 7 / 10, 25, brownColor3))).pos(0, 0, AL.c),
        LB.New().font(GConstants.BMF).text("" + GConstants.HINT_COST).pos(30, 0, AL.cl).idx("coinLB"),
        IB.New().drawable("coin").idx("c").pos(10, 0, AL.cr).scale(0.5f)
    ).pos(10, 0, AL.cr).idx("btnBuyS").parent(buyShuffle).build();

    MGB.New().size(w * 0.25f, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(h, h * 8 / 10, 25, brownColor5))).pos(0, 0, AL.c),
        IB.New().texture(new Texture(new BorderPM().get(h * 9 / 10, h * 7 / 10, 25, brownColor3))).pos(0, 0, AL.c),
        LB.New().font(GConstants.BMF).text("" + GConstants.HINT_COST).pos(30, 0, AL.cl).idx("coinLB"),
        IB.New().drawable("coin").idx("c").pos(10, 0, AL.cr).scale(0.5f)
    ).pos(10, 0, AL.cr).idx("btnBuyR").parent(buyRocket).build();

//    IB.New().texture(new Texture(new BorderPM().get(w * 2 / 10, h * 8 / 10, 25, brownColor1))).pos(20, 0, AL.cr).idx("btnBuyH").parent(buyHint).build();
//    IB.New().texture(new Texture(new BorderPM().get(w * 15 / 100, h * 70 / 100, 25, brownColor2))).pos(22.5f, 0, AL.cr).idx("btnBuyH").parent(buyHint).build();
//    LB.New().font(GConstants.BMF).text("" + GConstants.HINT_COST).pos(35, 0, AL.cr).idx("btnBuyH").parent(buyHint).build();
//    IB.New().drawable("coin").idx("c").pos(w * 0.15f, 0, AL.cr).scale(0.9f).parent(buyHint).build();
//
//    IB.New().texture(new Texture(new BorderPM().get(w * 2 / 10, h * 8 / 10, 25, brownColor1))).pos(20, 0, AL.cr).idx("btnBuyS").parent(buyShuffle).build();
//    IB.New().texture(new Texture(new BorderPM().get(w * 15 / 100, h * 70 / 100, 25, brownColor2))).pos(22.5f, 0, AL.cr).idx("btnBuyH").parent(buyShuffle).build();
//    LB.New().font(GConstants.BMF).text("" + GConstants.SHUFFLE_COST).pos(35, 0, AL.cr).idx("btnBuyS").parent(buyShuffle).build();
//    IB.New().drawable("coin").idx("c").pos(w * 0.15f, 0, AL.cr).scale(0.9f).parent(buyShuffle).build();
//
//    IB.New().texture(new Texture(new BorderPM().get(w * 2 / 10, h * 8 / 10, 25, brownColor1))).pos(20, 0, AL.cr).idx("btnBuyR").parent(buyRocket).build();
//    IB.New().texture(new Texture(new BorderPM().get(w * 15 / 100, h * 70 / 100, 25, brownColor2))).pos(22.5f, 0, AL.cr).idx("btnBuyH").parent(buyRocket).build();
//    LB.New().font(GConstants.BMF).text("" + GConstants.ROCKET_COST).pos(35, 0, AL.cr).idx("btnBuyR").parent(buyRocket).build();
//    IB.New().drawable("coin").idx("c").pos(w * 0.15f, 0, AL.cr).scale(0.9f).parent(buyRocket).build();

    MGB.New().size(w * 0.35f, h).childs(
        IB.New().texture(new Texture(new BorderPM().get(w * 3/10, h * 8 / 10, 25, brownColor1))).pos(0, 0, AL.cr),
        IB.New().texture(new Texture(new BorderPM().get(w * 25 / 100, h * 70 / 100, 25, brownColor2))).pos(5f, 0, AL.cr),
        LB.New().font(GConstants.BMF).text("" + GMain.player().getCoins()).pos(20, 0, AL.cr).idx("coinLB"),
        IB.New().drawable("coin").idx("c").pos(10, 0, AL.cl).scale(0.9f)
    ).pos(10, -h*2.8f, AL.tl).idx("coinT").parent(this).debug(false).build();
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
        screen.resume();
        break;
      case "buyHint":
        System.out.println("click buyHint");
        GMain.player().buyHint();
        updateLabel("buyHint/hintLB", "Hint:           " + game.getPlayer().getHints());
        updateLabelCoin();
        break;
      case "buyShuffle":
        System.out.println("click buyShuffle");
        GMain.player().buyShuffle();
        updateLabel("buyShuffle/shuffleLB", "Shuffle:     " + game.getPlayer().getShuffles());
        updateLabelCoin();
        break;
      case "buyRocket":
        System.out.println("click buyRocket");
        GMain.player().buyRocket();
        updateLabel("buyRocket/rocketLB", "Rocket:     " + game.getPlayer().getRockets());
        updateLabelCoin();
        break;
    }
  }

  private void updateLabel(String name, String text) {
    this.query(name, Label.class).setText(text);
  }

  private void updateLabelCoin() {
    this.query("coinT/coinLB", Label.class).setText(game.getPlayer().getCoins());
  }

}
