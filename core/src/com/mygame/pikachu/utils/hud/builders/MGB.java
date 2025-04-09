package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.utils.hud.MapGroup;

@SuppressWarnings("unused")
public class MGB extends AbstractActorBuilder<MapGroup> {
  boolean transform = true;

  public MGB() {
  }

  public static MGB New() {
    return new MGB();
  }

  public MGB transform(boolean transform) {
    this.transform = transform;
    return this;
  }

  @Override
  public MapGroup build() {
    boolean fitW = false, fitH = false;
    if (w == -1) {
      w = adapter.worldWidth();
      fitW = true;
    }
    if (h == -1) {
      h = adapter.worldHeight();
      fitH = true;
    }

    MapGroup res = new MapGroup(w, h);
    applyCommonProps(res);
    res.setTransform(this.transform);

    if (fitW)
      w = -1;
    if (fitH)
      h = -1;

    if (this.childs != null)
      for (AbstractActorBuilder<?> bd : this.childs) {
        bd.name(name + "/" + index);
        Actor out = bd.parent(res).build();
        if (adapter.debug()) {
          out.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
              if (bd.index != null && !bd.index.isEmpty()) {
                out.setDebug(true);
                adapter.debugMsg(bd.name + "/" + bd.index);
              }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
              out.setDebug(false);
              adapter.debugMsg("");
            }
          });
        }
      }
    res.setJsonSerialized(serialize());
    return res;
  }
}
