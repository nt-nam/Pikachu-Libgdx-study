package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.pikachu.utils.hud.AL;
import com.mygame.pikachu.utils.hud.Button;

@SuppressWarnings("unused")
public class BB extends AbstractActorBuilder<Button> {
  public BB() {
  }

  public static BB New() {
    return new BB();
  }

  String bgRegion = "";
  String bgdRegion = "";

  NinePatch ninePatch;
  NinePatch ninePatchDown;

  String fontKey = "";
  String text = "";
  int textAlign = AL.c;
  float textX, textY;

  float fontScale = 1;
  String fontColor = "FFFFFF";

  String iconRegion = "";
  int iconAlign = AL.c;
  float iconX, iconY, scaleX, scaleY;
  boolean transform;

  public BB icon(String region, float x, float y, int align) {
    this.iconRegion = region;
    this.iconX = x;
    this.iconY = y;
    this.iconAlign = align;
    return this;
  }
  
  public BB icon(String region, float x, float y, int align, float scaleX, float scaleY) {
    this.iconRegion = region;
    this.iconX = x;
    this.iconY = y;
    this.scaleX = scaleX;
    this.scaleY = scaleY;
    this.iconAlign = align;
    return this;
  }

  public BB label(String text, String font, float x, float y, int align) {
    this.fontKey = font;
    this.text = text;
    this.textX = x;
    this.textY = y;
    this.textAlign = align;
    return this;
  }

  public BB fontScale(float scale) {
    this.fontScale = scale;
    return this;
  }

  public BB fontColor(String color) {
    this.fontColor = color;
    return this;
  }

  public BB bg(String region) {
    this.bgRegion = region;
    return this;
  }

  public BB bgd(String region) {
    this.bgdRegion = region;
    return this;
  }

  public BB np(String region, int left, int right, int top, int bottom) {
    this.ninePatch = adapter.ninePatch(region, left, right, top, bottom);
    this.bgRegion = null;
    return this;
  }

  public BB npd(String region, int left, int right, int top, int bottom) {
    this.ninePatchDown = adapter.ninePatch(region, left, right, top, bottom);
    this.bgdRegion = null;
    return this;
  }

  public BB transform(boolean transform){
    this.transform = transform;
    return this;
  }

  @Override
  public Button build() {
    Button res = new Button();
    res.setTransform(this.transform);

    if (ninePatch != null) {
      Image img = new Image(ninePatch);
      img.setSize(w, h);
      img.setOrigin(AL.c);
      res.setBg(img);
    } else
      res.setBg(IB.New().drawable(bgRegion).align(AL.c).build());
    if (ninePatchDown != null) {
      Image img = new Image(ninePatchDown);
      img.setSize(w, h);
      img.setOrigin(AL.c);
      res.setBgd(img);
    } else if (bgdRegion != null && !bgdRegion.isEmpty())
      res.setBgd(IB.New().drawable(bgdRegion).align(AL.c).build());
    if (iconRegion != null && !iconRegion.isEmpty())
      res.setIcon(IB.New().drawable(iconRegion).build(), iconX, iconY, iconAlign, scaleX, scaleY);
    if (fontKey != null && !fontKey.isEmpty()) {
      LB l = LB.New().font(fontKey).text(text).fontScale(fontScale);
      if (fontColor != null && !fontColor.isEmpty())
        l.fontColor(fontColor);
      res.setLabel(l.build(), textX, textY, textAlign);
    }

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

    applyCommonProps(res);
    return res;
  }
}