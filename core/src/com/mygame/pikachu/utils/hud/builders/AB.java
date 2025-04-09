package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.scenes.scene2d.Actor;

@SuppressWarnings("unused")
public class AB extends AbstractActorBuilder<Actor> {
  public AB() {
  }

  public static AB New() {
    return new AB();
  }


  @Override
  public Actor build() {
    Actor a = new Actor();
    a.setSize(w, h);
    applyCommonProps(a);
    return a;
  }
}