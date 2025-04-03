package com.mygdx.game.utils.hud.builders;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.utils.hud.GridGroup;

@SuppressWarnings("unused")
public class GGB extends AbstractActorBuilder<GridGroup> {
  public static GGB New() {
    return new GGB();
  }

  private int col = 1;
  private float pad = 10;
  private boolean scrollX, scrollY;
  private int tbAlign = 1;
  private float scaleCurrent = 1f;
  private boolean scrollByNode;
  private boolean transform;

  public GGB col(int col) {
    this.col = col;
    return this;
  }

  public GGB pad(float pad) {
    this.pad = pad;
    return this;
  }

  public GGB disableScroll(boolean x, boolean y) {
    this.scrollX = x;
    this.scrollY = y;
    return this;
  }

  public GGB tbAlign(int align) {
    this.tbAlign = align;
    return this;
  }

  public GGB scaleCurrent(float scl) {
    this.scaleCurrent = scl;
    return this;
  }

  public GGB scrollByNode(boolean scr) {
    this.scrollByNode = scr;
    return this;
  }

  public GGB transform(boolean transform){
    this.transform = transform;
    return this;
  }

  @Override
  public GridGroup build() {
    GridGroup res = new GridGroup(w, h, col, pad, tbAlign);
    applyCommonProps(res);
    res.setScrollingDisabled(scrollX, scrollY);
    res.setScaleCurrent(scaleCurrent);
    res.setScrollByNode(scrollByNode);
    for (AbstractActorBuilder<?> bd : childs) {
      bd.name(name + "/" + index);
      Actor out = bd.build();
      res.addGrid(out, bd.index);
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

    return res;
  }
}