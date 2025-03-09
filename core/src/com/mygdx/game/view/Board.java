package com.mygdx.game.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.data.AssetHelper;

public class Board extends Group {
  PikachuGame game;
  Image btn_hind,btn_shuffle,btn_undo,btn_back;
  AssetHelper assetHelper;
  public Board(PikachuGame game) {
    this.game = game;
    assetHelper = game.getAssetHelper();
    Viewport vp = game.getStage().getViewport();
    this.setBounds(vp.getScreenX(), vp.getScreenY(),vp.getWorldWidth(),vp.getWorldHeight());
    initAsset();
  }

  private void initAsset() {

  }


}
