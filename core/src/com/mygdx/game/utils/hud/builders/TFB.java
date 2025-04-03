package com.mygdx.game.utils.hud.builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

@SuppressWarnings("unused")
public class TFB extends AbstractActorBuilder<TextField> {
  private String fontKey = "font_nc3";
  private String text = "";
  private String color;
  private Drawable background;
  private NinePatch ninepatchBg;
  private Drawable cursor;
  private Drawable selection;
  private int alignment;
  private int maxLength;
  private TextField.TextFieldFilter filter;

  public TFB() {
  }

  public static TFB New() {
    return new TFB();
  }


  public TFB fontColor(String color) {
    this.color = color;
    return this;
  }

  public TFB font(String key) {
    fontKey = key;
    return this;
  }

  public TFB background(String bg) {
    this.background = adapter.getDrawable(bg);
    return this;
  }

  public TFB nPathBg(String key, int left, int right, int top, int bottom) {
    this.ninepatchBg = adapter.ninePatch(key, left, right, top, bottom);
    background = null;
    return this;
  }

  public TFB cursor(String cursor) {
    this.cursor = adapter.getDrawable(cursor);
    return this;
  }

  public TFB selection(String selection) {
    this.selection = adapter.getDrawable(selection);
    return this;
  }

  public TFB alignment(int align) {
    this.alignment = align;
    return this;
  }
  public TFB text(String text) {
    this.text = text;
    return this;
  }

  public TFB maxLength(int maxLength) {
    this.maxLength = maxLength;
    return this;
  }
  public TFB filter(TextField.TextFieldFilter filter) {
    this.filter = filter;
    return this;
  }

  @Override
  public TextField build() {
    TextField.TextFieldStyle style = new TextField.TextFieldStyle();
    if (background != null)
      style.background = this.background;
    else if (ninepatchBg != null)
      style.background = new NinePatchDrawable(this.ninepatchBg);
    style.font = adapter.getBitmapFont(fontKey);
    style.fontColor = color == null ? Color.WHITE : Color.valueOf(color);
    style.cursor = this.cursor;
    style.selection = this.selection;

    TextField res = new TextField(text, style);
    res.setSize(style.background.getMinWidth(), style.background.getMinHeight());
    res.setAlignment(alignment);
    res.setMaxLength(maxLength);
    res.setTextFieldFilter(filter);
    if (w != 0)
      res.setWidth(w);
    if (h != 0)
      res.setHeight(h);
    applyCommonProps(res);
    return res;
  }
}