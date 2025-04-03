package com.mygdx.game.utils.hud.external;

import com.badlogic.gdx.scenes.scene2d.Actor;

@FunctionalInterface
@SuppressWarnings("unused")
public interface EventHandler {
  void handleEvent(Actor actor, String action, int intParam, Object objParam);
}