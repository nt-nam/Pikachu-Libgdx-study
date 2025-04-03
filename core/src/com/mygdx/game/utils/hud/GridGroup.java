package com.mygdx.game.utils.hud;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.utils.GUtils;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused unchecked")
public class GridGroup extends ScrollPane {
  ObjectMap<String, Actor> indexer;
  int nCol;
  float pad;
  Table innerTable;
  boolean scrollFocus;

  float scaleCurrent;
  boolean scrollByNode;
  int curNode;

  public GridGroup(float width, float height, int nCol, float pad, int al) {
    super(new Table());
    innerTable = (Table) getActor();
    innerTable.align(al);
    setSize(width, height);
    indexer = new ObjectMap<>();
    this.nCol = nCol;
    this.pad = pad;
  }

  public void addGrid(Actor child, String key) {
    if (child != null) {
      if (nCol > 1) {
        innerTable.add(child).pad(pad);
        indexer.put(key, child);
        if (indexer.size % nCol == 0)
          innerTable.row();
      } else if (nCol == 1) {
        innerTable.add(child).padTop(pad).row();
        indexer.put(key, child);
      } else if (nCol == 0) {
        innerTable.add(child).padRight(pad).padLeft(pad);
        indexer.put(key, child);
      }
    }
    innerTable.setSize(getPrefWidth(), getPrefHeight());
  }

  public void addGrid(Actor child, String key, int colspan) {
    if (child != null) {
      if (nCol > 1) {
        innerTable.add(child).pad(pad).colspan(colspan);
        indexer.put(key, child);
        if (indexer.size % nCol == 0)
          innerTable.row();
      } else if (nCol == 1) {
        innerTable.add(child).colspan(colspan).padTop(pad).row();
        indexer.put(key, child);
      } else if (nCol == 0) {
        innerTable.add(child).colspan(colspan).padRight(pad).padLeft(pad);
        indexer.put(key, child);
      }
    }
    innerTable.setSize(getPrefWidth(), getPrefHeight());
  }

  public void remove(String identity) {
    Actor child = indexer.get(identity);
    if (child != null) {
      indexer.remove(identity);
      innerTable.removeActor(child);
    }
    setSize(getPrefWidth(), getPrefHeight());
  }

  public void clearGridChildren() {
    innerTable.clearChildren();
    indexer.clear();
  }

  public <T extends Actor> T query(String path, Class<T> type) {
    List<String> split = Arrays.asList(path.split("/"));
    return query(split, type);
  }

  <T extends Actor> T query(List<String> path, Class<T> type) {
    if (path == null || path.size() == 0)
      return null;
    if (path.size() == 1)
      return (T) indexer.get(path.get(0));
    Actor innerGroup = indexer.get(path.get(0));
    if (innerGroup instanceof MapGroup)
      return ((MapGroup) innerGroup).query(path.subList(1, path.size()), type);
    return null;
  }

  public Table getTable() {
    return innerTable;
  }

  public int getCurNode() {
    return curNode;
  }

  public void setCurNode(int node){
    this.curNode = node;
  }

  public void setScaleCurrent(float scl) {
    this.scaleCurrent = scl;
  }

  public void setScrollByNode(boolean scroll) {
    this.scrollByNode = scroll;
    if (scrollByNode) {
      setFlingTime(0);
      addListener(new InputListener() {
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
          if (getParent() != null && getParent().getStage() != null) {
            getParent().getStage().setScrollFocus(GridGroup.this);
          }
        }

        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
          if (getParent() != null && getParent().getStage() != null) {
            getParent().getStage().setScrollFocus(null);
          }
        }
      });
    }
  }

  @Override
  protected void visualScrollX(float pixelsX) {
    super.visualScrollX(pixelsX);
  }

  @Override
  public float getVisualScrollX() {
    return super.getVisualScrollX();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    if (scaleCurrent != 1f) {
      for (Cell<Actor> cell : innerTable.getCells()) {
        Actor actor = cell.getActor();
        if (actor instanceof MapGroup) {
          Actor child = ((MapGroup) actor).query("child", Actor.class);
          if (child != null) {
            actor = child;
          }
        }

        Vector2 v = GUtils.absPos(actor, 0, 0, AL.c);
        float dx = Math.abs((getX() + v.x) - getWidth() / 2);
        float pc = dx / actor.getWidth();
        float dScl = scaleCurrent - 1;
        float scl = Math.max(1, scaleCurrent - (pc * dScl));
        if (scl != actor.getScaleX())
          actor.setScale(scl);
      }
    }
    if (scrollByNode) {
      float w = innerTable.getCells().first().getActor().getWidth();
      if (scrollFocus && !hasScrollFocus()) {
        int scrollTo = (int) ((getScrollX() + w / 2) / w);
        if (scrollTo < 0)
          return;
        curNode = scrollTo;
        scrollTo(w * scrollTo, 0, getWidth(), getHeight());
      }
      scrollFocus = hasScrollFocus();
    }
  }
}