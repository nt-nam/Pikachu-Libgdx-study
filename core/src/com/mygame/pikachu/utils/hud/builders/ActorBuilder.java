package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

@SuppressWarnings("unused")
interface ActorBuilder<T extends Actor> {
  ActorBuilder<T> color(String color);
  ActorBuilder<T> name(String name);
  ActorBuilder<T> origin(int origin);
  ActorBuilder<T> pos(float x, float y);
  ActorBuilder<T> align(int align);
  ActorBuilder<T> idx(String key);
  ActorBuilder<T> zIndex(int index);
  ActorBuilder<T> scale(float sx, float sy);
  ActorBuilder<T> size(float w, float h);
  ActorBuilder<T> touchable(boolean touchable);
  ActorBuilder<T> visible(boolean visible);
  ActorBuilder<T> debug(boolean debug);
  ActorBuilder<T> rot(float rotation);
  ActorBuilder<T> parent(Group group);
  ActorBuilder<T> parent(String path);
  T               build();
}