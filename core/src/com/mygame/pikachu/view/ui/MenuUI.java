package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.Button;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.LB;
import com.mygame.pikachu.utils.hud.builders.MGB;

public class MenuUI extends BaseUI {
  public MenuUI(GMain game) {
    super(game);
    index("menu", this);
  }

  @Override
  protected void addHandle() {
    GMain.hud().index("menuUI", this);
    GMain.hud().regisHandler("menuHandle", this);
    GMain.hud().clickConnect("menuUI/closeMenu", "menuHandle", "close");
//    GMain.hud().clickConnect("menuUI/level", "menuHandle", "level",);
  }

  @Override
  protected void createUI() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().drawable("panel").size(centerX * 1.8f, centerY * 1.8f).pos(0, 0, AL.c).parent(this).build();

    Table table = new Table();
    table.setName("table");
    ScrollPane scrollPane = new ScrollPane(table);
    scrollPane.setName("scP");
    Actor a = new Actor();
    a.setSize(100, 50);
    table.add(a);
    table.row();
    for (int n = 1; n <= 100; n++) {
      GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
      MapGroup level = MGB.New().size(centerX * 0.55f, 100).childs(
          BB.New().bg("btn_yellow").transform(true).scale(0.8f).pos(+0, 0, AL.c).idx("unlockLevel"),
          BB.New().bg("btn_red").transform(true).scale(0.8f).pos(+0, 0, AL.c).idx("disable").visible(false).touchable(false),
          LB.New().text("" + n).font("font/arial_uni_30").fontScale(2f).pos(0, 0, AL.c)
      ).pos(0, 0, AL.r).idx("level").build();

      if (n > GMain.player().getLevel()) {
        level.query("disable", Button.class).setVisible(true);
      }
      else {
        int finalN = n;
        level.addListener(new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            game.getPlayScreen().setLevel(finalN);
            game.setScreen(game.getPlayScreen());
          }
        });
      }

      table.add(level);
      if (n % 3 == 0)
        table.row();
    }

    scrollPane.setSize(centerX * 1.6f, centerY * 1.5f);
    scrollPane.setScrollingDisabled(true, false);
    scrollPane.setOrigin(Align.center);
    scrollPane.setPosition(centerX, centerY, Align.center);
    this.addActor(scrollPane);


    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().drawable("ribbon").scale(0.9f, 1).pos(0, 75, AL.ct).parent(this).build();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
    BB.New().bg("btn_pause").transform(true).pos(50, 50, AL.tr).idx("closeMenu").parent(this).build();
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {
    switch (action) {
      case "close":
        System.out.println("click CloseMenu");
        hide();
        break;
      case "playlevel":
        game.getPlayScreen().setLevel(intParam);
        game.setScreen(game.getPlayScreen());
        break;
    }
  }
}
