package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ButtonFactory extends Actor {
  String nameButton;
  TextureAtlas skin;
  String text;
  int x,y,w,h;
  public ButtonFactory(TextureAtlas skin, String nameButton, String text){
    this.nameButton = nameButton;
    this.skin = skin;
    this.text = text;
  }

  public ButtonFactory( TextureAtlas skin,String nameButton) {
    this.nameButton = nameButton;
    this.skin = skin;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(skin.findRegion(nameButton),x,y,w,h);
    super.draw(batch, parentAlpha);
  }
}
