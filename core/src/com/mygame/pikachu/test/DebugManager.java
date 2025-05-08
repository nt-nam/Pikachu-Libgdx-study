package com.mygame.pikachu.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.mygame.pikachu.GMain;

public class DebugManager {
  private TextField consoleInput;
  private Skin skin;
  private boolean consoleVisible;
  private boolean debugMode = true;

  public DebugManager() {
    // Khởi tạo console
    skin = new Skin(Gdx.files.internal("default/uiskin.json"));
    consoleInput = new TextField("", skin);
    consoleInput.setWidth(200);
    consoleInput.setPosition(50, GMain.stage().getHeight(), Align.topLeft);
    GMain.stage().addActor(consoleInput);
    consoleInput.setVisible(false);
    consoleVisible = false;
  }

  public void update(float delta) {
    if (!debugMode) return; // Bỏ qua nếu debug mode tắt

    // Phím tắt để test
//    if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
////      GMain.instance().getPlayScreen().setLevel(GMain.instance().getPlayScreen().getLevel());
//
//      GMain.instance().getPlayScreen().dispose();
//      GMain.instance().getPlayScreen().show();
//    }

    // Bật/tắt console bằng phím `
    if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
      onDebugConsole();
    }

    // Xử lý lệnh console
    if (consoleVisible && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      String command = consoleInput.getText().trim();
      processCommand(command);
      consoleInput.setText("");
    }
  }

  public void onDebugConsole() {
    consoleInput.setText("");
    consoleVisible = !consoleVisible;
    consoleInput.setVisible(consoleVisible);
    if (consoleVisible) {
      GMain.stage().setKeyboardFocus(consoleInput);
//        Gdx.input.setInputProcessor(stage);
    } else {
//        Gdx.input.setInputProcessor(null);
    }
  }

  private void processCommand(String command) {
    if (command.startsWith("p ")) {
      try {
        int levelId = Integer.parseInt(command.substring(2));
        GMain.instance().getPlayScreen().setLevel(levelId);
        GMain.instance().setScreen(GMain.instance().getPlayScreen());
      } catch (NumberFormatException e) {
        System.out.println("Invalid level ID");
      }
    } else if (command.startsWith("r")) {
      try {

//        GMain.instance().getPlayScreen().dispose();
//        GMain.instance().getPlayScreen().show();
        System.out.println("Not restart");
//        GMain.instance().setScreen(GMain.instance().getPlayScreen());
      } catch (NumberFormatException e) {
        System.out.println("Not restart");
      }
    } else if (command.startsWith("--r")) {
      try {
        GMain.player().clearData();
      } catch (NumberFormatException e) {
        System.out.println("Not clear data ");
      }
    }
  }


  public void dispose() {
    skin.dispose();
  }

}
