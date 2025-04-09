package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.GridGroup;
import com.mygame.pikachu.utils.hud.MapGroup;
import com.mygame.pikachu.utils.hud.external.BuilderAdapter;

@SuppressWarnings("unused unchecked")
public abstract class AbstractActorBuilder<T extends Actor> implements ActorBuilder<T> {
  private static final Json serializer = new Json();
  protected AbstractActorBuilder() { }
  public String                 color   = null;
  public String                 name    = "";
  private int                   origin;
  public float                  x,y;
  private int                   align = AL.l;
  private int                   zIndex = -1;
  private float                 rot;
  private float                 sx = 1,sy = 1;
  public float                  w,h;
  private boolean               touchable = true;
  private boolean               visible   = true;
  private boolean               debug     = false;
  private transient Group       parent = null;
  private String                 parentPath = "";
  public String                 index     = "";
  public String                 clazz;
  public static BuilderAdapter  adapter;
  public Array<AbstractActorBuilder<?>> childs = new Array<>();

  public AbstractActorBuilder<T> color(String color) {
    this.color = color; return this;
  }

  public AbstractActorBuilder<T> name(String name) {
    this.name = name; return this;
  }

  public AbstractActorBuilder<T> origin(int origin) {
    this.origin = origin; return this;
  }

  public AbstractActorBuilder<T> pos(float x, float y) {
    this.x = x; this.y = y; return this;
  }

  public AbstractActorBuilder<T> pos(float x, float y, int align) {
    this.x = x; this.y = y; this.align = align;return this;
  }

  public AbstractActorBuilder<T> align(int align) {
    this.align = align; return this;
  }

  public AbstractActorBuilder<T> scale(float sx, float sy) {
    this.sx = sx; this.sy = sy; return this;
  }

  public AbstractActorBuilder<T> scale(float scl) {
    this.sx = this.sy = scl; return this;
  }

  public AbstractActorBuilder<T> size(float w, float h) {
    this.w = w; this.h = h; return this;
  }

  public AbstractActorBuilder<T> touchable(boolean touchable) {
    this.touchable = touchable; return this;
  }

  public AbstractActorBuilder<T> visible(boolean visible) {
    this.visible = visible;
    return this;
  }

  public AbstractActorBuilder<T> debug(boolean debug) {
    this.debug = debug; return this;
  }

  public AbstractActorBuilder<T> rot(float rotation) {
    this.rot = rotation; return this;
  }

  public AbstractActorBuilder<T> parent(Group group) {
    this.parent = group; this.parentPath = "";
    return this;
  }

  public AbstractActorBuilder<T> parent(String path) {
    this.parent = null; this.parentPath = path;
    return this;
  }

  @Override
  public AbstractActorBuilder<T> idx(String key) {
   this.index = key; return this;
  }

  @Override
  public AbstractActorBuilder<T> zIndex(int index) {
   this.zIndex = index; return this;
  }

  protected void applyCommonProps(T t) {
    t.setPosition(x, y);
    t.setRotation(rot);
    t.setScale(sx, sy);
    t.setTouchable(touchable ? Touchable.enabled : Touchable.disabled);
    t.setName(name);
    t.setVisible(visible);
    t.setOrigin(origin);
    t.setDebug(debug);

    if (!parentPath.isEmpty())
      parent = adapter.query(parentPath);

    if (parent instanceof MapGroup) {
      MapGroup g = (MapGroup) parent;
      if (index != null && !index.isEmpty()) {
        g.addActor(t, align);
        g.index(index, t);
      }
      else
        g.addActor(t, align);
    }

    if (parent instanceof GridGroup) {
      GridGroup g = (GridGroup) parent;
      if (index != null && !index.isEmpty()) {
        g.addGrid(t, index);
      }
      else
        g.addGrid(t, "");
    }
    else if (parent != null){
      parent.addActor(t);
    }
    if (zIndex >= 0)
      t.setZIndex(zIndex);

    if (color != null) {
      t.setColor(Color.valueOf(color));
    }
    this.clazz = this.getClass().getName();
  }

  protected void reset() {

  }

  public AbstractActorBuilder<T> childs(AbstractActorBuilder<?> ...args) {
    for (AbstractActorBuilder<?> ab : args)
      this.childs.add(ab);
    return this;
  }

  public AbstractActorBuilder<T> child(AbstractActorBuilder<?> child) {
    this.childs.add(child); return this;
  }

  protected String serialize() {
    return "";
  }

  public static <B extends AbstractActorBuilder<?>> B toBuilder(String json) throws ReflectionException {
    JsonReader jr = new JsonReader();
    JsonValue js = jr.parse(json);
    String clazz = js.get("clazz").asString();
    return serializer.fromJson((Class<? extends B>) ClassReflection.forName(clazz), json);
  }

  public static <B extends AbstractActorBuilder<?>> String toJson(B builder) {
    return serializer.prettyPrint(builder);
  }
}