package com.mygame.pikachu.view.ui;

import com.mygame.pikachu.GMain;

public class PopupUI {
  private GMain game;
  private BaseUI currentUI;

  public PopupUI(GMain game) {
    this.game = game;
  }

  public void showWinUI(int level, int numStar) {
    if (currentUI != null) currentUI.remove();
    currentUI = new WinUI(game, level, numStar);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showFailUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new FailUI(game);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showPauseUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new PauseUI(game);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showSettingUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new SettingUI(game);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showMenuUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new MenuUI(game);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void hide() {
    if (currentUI != null) {
      currentUI.hide();
      currentUI.remove();
      currentUI = null;
    }
  }
}