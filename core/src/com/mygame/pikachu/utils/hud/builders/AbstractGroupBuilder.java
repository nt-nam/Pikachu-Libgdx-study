package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

@SuppressWarnings("unused")
abstract class AbstractGroupBuilder<T extends Group> extends AbstractActorBuilder<T>{
  Rectangle culling;

  public AbstractGroupBuilder(){
    culling = new Rectangle(0,0,0,0);
  }

  @Override
  protected void reset() {
    super.reset();
    culling.setPosition(0,0);
    culling.setSize(0,0);
  }

  public AbstractGroupBuilder<T> culling(Rectangle culling) {
    this.culling = culling; return this;
  }
}