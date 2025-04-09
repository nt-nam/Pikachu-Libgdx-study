package com.mygame.pikachu.utils.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Arrays;
import java.util.List;

/*
Support:
  - dynamic logic attachment
  - query child base on mount label before
  - simple alignment
*/
@SuppressWarnings("unused unchecked")
public class MapGroup extends Group {
  protected ObjectMap<String, Actor> indexer;
  protected String jsonSerialized;

  public MapGroup(float w, float h) {
    setSize(w,h);
    indexer = new ObjectMap<>();
  }

  public<T extends Actor> T query(String path, Class<T> type) {
    List<String> split = Arrays.asList(path.split("/"));
    return query(split, type);
  }

  <T extends Actor> T query(List<String> split, Class<T> type) {
    if (split == null || split.size() == 0)
      return null;
    if (split.size() == 1)
      return (T)indexer.get(split.get(0));
    Actor innerGroup = indexer.get(split.get(0));
    if (innerGroup instanceof MapGroup)
      return ((MapGroup)innerGroup).query(split.subList(1, split.size()), type);
    if (innerGroup instanceof GridGroup)
      return ((GridGroup)innerGroup).query(split.subList(1, split.size()), type);
    return null;
  }

  public void addActor(Actor actor, int align) {
    addActor(actor);
    align(actor.getX(), actor.getY(), align, actor);
  }

  public void index(String key, Actor actor) {
    if (key != null && !key.isEmpty())
      indexer.put(key, actor);
  }

  public void removeIndex(String key) {
    indexer.remove(key);
  }

  protected void align(float padX, float padY, int align, Actor child) {
    float posX  = 0;
    float posY  = 0;
    int   textAlign = 0;

    if ((align & Align.center) == Align.center) {
      textAlign |= Align.center;
      posX = getWidth()/2 + padX;
      posY = getHeight()/2 + padY;
    }

    if ((align & Align.left) == Align.left) {
      textAlign |= Align.left;
      posX = padX;
    }

    if ((align & Align.top) == Align.top) {
      textAlign |= Align.top;
      posY = getHeight() - padY;
    }

    if ((align & Align.bottom) == Align.bottom) {
      textAlign |= Align.bottom;
      posY = padY;
    }

    if ((align & Align.right) == Align.right) {
      textAlign |= Align.right;
      posX = getWidth() - padX;
    }

    if (child instanceof Label)
      ((Label)child).setAlignment(textAlign);
    child.setPosition(posX, posY, align);
  }

  public void setJsonSerialized(String jsonSerialized) {
    this.jsonSerialized = jsonSerialized;
  }

  public String getJsonSerialized() {
    return jsonSerialized;
  }
}