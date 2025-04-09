package com.mygame.pikachu.utils.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygame.pikachu.utils.hud.builders.AbstractActorBuilder;
import com.mygame.pikachu.utils.hud.external.EventHandler;

@SuppressWarnings("unused")
public class HUD extends MapGroup {
  ObjectMap<String, EventHandler> handlers;

  public HUD(float w, float h) {
    super(w, h);
    handlers = new ObjectMap<>();
  }

  public void clickConnect(String path, String handler, String action) {
    clickConnect(path, handler, action, 0, null);
  }

  public void clickConnect(String path, String handler, String action, int intArgs) {
    clickConnect(path, handler, action, intArgs, null);
  }

  public void clickConnect(String path, String handler, String action, int intArgs, Object objArgs) {
    Actor actor = query(path, Actor.class);
    if (actor == null) {
      Gdx.app.log("HUD", "no actor at the path: " + path);
      return;
    }

    actor.addListener(new ClickListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == 0) {
          return super.touchDown(event, x, y, pointer, button);
        }
        return false;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == 0){
          super.touchUp(event, x, y, pointer, button);
        }
      }

      @Override
      public void clicked(InputEvent event, float x, float y) {
        try {
          EventHandler eh = handlers.get(handler);
          if (eh == null) {
            Gdx.app.log("HUD", "no handler name: " + handler);
            return;
          }
          eh.handleEvent(actor, action, intArgs, objArgs);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public Actor load(String json) {
    try {
      AbstractActorBuilder<?> bd = AbstractActorBuilder.toBuilder(json);
      Actor a = bd.build();
      index(bd.index, a);
      return a;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  public void fireEvent(String handler, String action) {
    fireEvent(handler, action, 0, null);
  }

  public void fireEvent(String handler, String action, int intArgs) {
    fireEvent(handler, action, intArgs, null);
  }

  public void fireEvent(String handler, String action, int intArgs, Object objArgs) {
    EventHandler eh = handlers.get(handler);
    if (eh == null)
      return;
    eh.handleEvent(null, action, intArgs, objArgs);
  }

  public void clearAll() {
    clear();
    indexer.clear();
    handlers.clear();
  }

  public void regisHandler(String key, EventHandler handler) {
    handlers.put(key, handler);
  }

  public void removeHandler(String key) {
    handlers.remove(key);
  }
}