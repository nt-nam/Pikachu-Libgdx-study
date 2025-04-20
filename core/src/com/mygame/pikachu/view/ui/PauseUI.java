package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;

public class PauseUI extends BaseUI {

  public PauseUI(GMain game) {
    super(game);
    createUI();
  }

  @Override
  protected void createUI() {
    addPanelBlue();
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    IB.New().drawable("tittle_pausing").pos(0, 20, AL.ct).parent(this).build();
//    addHomeButton();
//    addResetButton();
    BB.New().bg("btn_red").label("Resume", GConstants.BMF, 0, 0, AL.c)
        .pos(0, 130, AL.cb).parent(this).build()
        .addListener(new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            hide();
          }
        });
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {

  }
}