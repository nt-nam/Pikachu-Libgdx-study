package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.LB;
import com.mygame.pikachu.utils.hud.builders.MGB;

public class SettingUI extends BaseUI {

  public SettingUI(GMain game) {
    super(game);
    createUI();
  }

  @Override
  protected void createUI() {

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    MapGroup bgMG = MGB.New().size(getWidth(), getHeight()).childs(
        IB.New().texture("atlas/common/btn_exit.png").pos(0, 0, AL.tr).idx("close"),
        BB.New().bg("btn_exit").pos(0, 0, AL.tr).idx("close"),
        LB.New().text("Setting").font(GConstants.BMF).fontScale(3.5f).pos(0, 0, AL.ct)
    ).pos(0, 0, AL.c).parent(this).build();

    bgMG.query("close", com.mygame.pikachu.utils.hud.Button.class).addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        hide();
      }
    });

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    MapGroup musicTick = MGB.New().size(getWidth() * 0.5f, 100).childs(
        BB.New().bg("Music2").pos(0, 0, AL.c).idx("unTick").visible(soundManager.isMusicMuted()),
        BB.New().bg("Music1").pos(0, 0, AL.c).idx("Tick").visible(!soundManager.isMusicMuted())
    ).pos(0, getHeight() * 0.25f, AL.cb).parent(bgMG).build();

    musicTick.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        boolean newState = !soundManager.isMusicMuted();
        soundManager.setMusicMuted(newState);
        game.getPlayer().setMusicMuted(newState);
        musicTick.query("Tick", com.mygame.pikachu.utils.hud.Button.class).setVisible(!newState);
        musicTick.query("unTick", com.mygame.pikachu.utils.hud.Button.class).setVisible(newState);
      }
    });
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {

  }
}