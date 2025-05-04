package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.data.GAssetsManager;
import com.mygame.pikachu.utils.GConstants;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.builders.BB;
import com.mygame.pikachu.utils.hud.builders.IB;
import com.mygame.pikachu.utils.hud.builders.MGB;

public class PauseUI extends BaseUI {

  public PauseUI(GMain game) {
    super(game);
  }

  @Override
  protected void createUI() {
    miniUI();
    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_COMMON);
    IB.New().drawable("tittle_pausing").pos(0, -40, AL.ct).parent(this).build();
    BB.New().bg("btn_red").label("Resume", GConstants.BMF, 0, 0, AL.c)
        .pos(centerX * 0.2f, 50, AL.bl)
        .idx("btnResume")
        .parent(this).build();
    BB.New().bg("btn_red").label("Home", GConstants.BMF, 0, 0, AL.c)
        .pos(centerX * 0.2f, 50, AL.br)
        .idx("btnHome")
        .parent(this).build();

    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_PLAY);
    MGB.New().size(235, 108).childs(
        IB.New().drawable("Music1").pos(0, 0, AL.c).visible(!GMain.instance().getSoundManager().isMusicMuted()).idx("MusicOn"),
        IB.New().drawable("Music2").pos(0, 0, AL.c).visible(GMain.instance().getSoundManager().isMusicMuted()).idx("MusicOff")
    ).pos(0, 70, AL.c).idx("Music").parent(this).build();
    MGB.New().size(235, 108).childs(
        IB.New().drawable("sound1").pos(0, 0, AL.c).visible(!GMain.instance().getSoundManager().isSoundMuted()).idx("soundOn"),
        IB.New().drawable("sound2").pos(0, 0, AL.c).visible(GMain.instance().getSoundManager().isSoundMuted()).idx("soundOff")
    ).pos(0, -70, AL.c).idx("sound").parent(this).build();
  }


  @Override
  protected void addHandle() {
    GMain.hud().index("popupUi", this);
    GMain.hud().regisHandler("popupUiHandler", this);
    GMain.hud().clickConnect("popupUi/Music", "popupUiHandler", "MusicAction");
    GMain.hud().clickConnect("popupUi/sound", "popupUiHandler", "soundAction");
    GMain.hud().clickConnect("popupUi/btnHome", "popupUiHandler", "HomeAction");
    GMain.hud().clickConnect("popupUi/btnResume", "popupUiHandler", "ResumeAction");
  }

  @Override
  public void handleEvent(Actor actor, String action, int intParam, Object objParam) {
    switch (action) {
      case "MusicAction":
        System.out.println("Music Click");
        GMain.instance().getSoundManager().setMusicMuted(!GMain.instance().getSoundManager().isMusicMuted());
        this.query("Music/MusicOff", Image.class).setVisible(GMain.instance().getSoundManager().isMusicMuted());
        this.query("Music/MusicOn", Image.class).setVisible(!GMain.instance().getSoundManager().isMusicMuted());
        GMain.player().save();
        break;
      case "soundAction":
        System.out.println("sound Click");
        GMain.instance().getSoundManager().setSoundMuted(!GMain.instance().getSoundManager().isSoundMuted());
        this.query("sound/soundOff", Image.class).setVisible(GMain.instance().getSoundManager().isSoundMuted());
        this.query("sound/soundOn", Image.class).setVisible(!GMain.instance().getSoundManager().isSoundMuted());
        GMain.player().save();
        break;
      case "HomeAction":
        System.out.println("Home Click Action");
        hide();
        GMain.instance().getPlayScreen().hide();
        GMain.instance().setScreen(GMain.instance().getHomeScreen());
        break;
      case "ResumeAction":
        System.out.println("Resume Click Action");
        GMain.instance().getPlayScreen().setPause(false);
        hide();
        break;
    }
  }
}