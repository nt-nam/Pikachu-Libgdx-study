package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.LB;

public class FailUI extends BaseUI {

  public FailUI(GMain game) {
    super(game);
    createUI();
    addHandle();
  }

  private void addHandle() {

  }

  @Override
  protected void createUI() {
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    LB.New().text("Lose").font(GConstants.BMF).fontScale(5).pos(0, 0, AL.ct).parent(this).build();
    addHomeButton();
    addResetButton();
    BB.New().bg("btn_red").label("Next Level", GConstants.BMF, 0, 0, AL.c)
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