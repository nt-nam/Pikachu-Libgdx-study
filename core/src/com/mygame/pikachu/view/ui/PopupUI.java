package com.mygame.pikachu.view.ui;

import com.badlogic.gdx.Screen;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.utils.hud.AL;

public class PopupUI {
  private Screen screen;
  private BaseUI currentUI;

  public PopupUI(Screen screen) {
    this.screen = screen;
  }

  public void showWinUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new WinUI(screen);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showFailUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new FailUI(screen);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showPauseUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new PauseUI(screen);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showSettingUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new SettingUI(screen);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showMenuUI(Screen screen) {
    if (currentUI != null) currentUI.remove();
    currentUI = new MenuUI(screen);
    GMain.hud().addActor(currentUI);
    currentUI.show();
  }

  public void showShopUI() {
    if (currentUI != null) currentUI.remove();
    currentUI = new ShopUI(screen);
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