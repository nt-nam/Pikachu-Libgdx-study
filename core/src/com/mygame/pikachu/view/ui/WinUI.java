package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.LB;

public class WinUI extends BaseUI {
  private int level;
  private int numStar;

  public WinUI(GMain game, int level, int numStar) {
    super(game);
    this.level = level;
    this.numStar = numStar;
    createUI();
  }

  @Override
  protected void createUI() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_LEADER_BOARD);
    IB.New().texture("topscore").pos(0, 0, AL.ct).parent(this).build();
    LB.New().text("Level " + level).font(GConstants.BMF).fontScale(2.5f).pos(0, 150, AL.c).parent(this).build();
    addHomeButton();
    addResetButton();
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {

  }
}