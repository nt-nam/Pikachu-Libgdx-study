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
    createUI();
    addHandle();
  }

  private void addHandle() {
    GMain.hud().regisHandler("MenuHandler", this);
    GMain.hud().clickConnect("menu", "MenuHandler", "");
  }

  @Override
  protected void createUI() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);

    MGB.New().size(centerX * 2, centerY * 2).childs(
        IB.New().drawable("panel").pos(0, 0, AL.c),
        IB.New().drawable("ribbon").pos(0, 0, AL.ct)
    ).pos(0, 0, AL.c).parent(this).build();

    Table table = new Table();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_UI);
    for (int n = 1; n < 100; n++) {
      GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
      MapGroup level = MGB.New().size(centerX * 0.55f, 100).childs(
          BB.New().bg("btn_yellow").transform(true).scale(0.8f).pos(+0, 0, AL.c).idx(""),
          BB.New().bg("btn_red").transform(true).scale(0.8f).pos(+0, 0, AL.c).idx("disable").visible(false).touchable(false),
          LB.New().text("" + n).font("font/arial_uni_30").fontScale(2f).pos(0, 0, AL.c)
      ).pos(0, 0, AL.r).debug(true).build();

      if (n > GMain.player().getLevel()) {
        level.query("disable", Button.class).setVisible(true);
      } else {
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
      table.columnDefaults((n % 5) + 1);
      if (n % 3 == 0)
        table.row();
    }
    ScrollPane scrollPane = new ScrollPane(table);
    scrollPane.setSize(centerX * 1.6f, centerY * 1.2f);
    scrollPane.setScrollingDisabled(true, false);
//    table.debug();
    scrollPane.debug();
    scrollPane.setOrigin(Align.center);
    scrollPane.setPosition(centerX, centerY, Align.center);
    this.addActor(scrollPane);
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {

  }
}
