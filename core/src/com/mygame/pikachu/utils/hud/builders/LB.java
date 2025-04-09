package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

@SuppressWarnings("unused")
public class LB extends AbstractActorBuilder<Label> {
  private String fontKey = "font_nc3";
  private String text = "";
  private String color;
  private float fontScale = 1;
  private int lineHeight = -1;
  private boolean wrap;
  private float widthWrap;

  private LB() {
  }

  public static LB New() {
    return new LB();
  }

  public LB text(String text) {
    this.text = text;
    return this;
  }

  public LB fontColor(String color) {
    this.color = color;
    return this;
  }

  public LB font(String key) {
    fontKey = key;
    return this;
  }

  public LB fontScale(float fontScale) {
    this.fontScale = fontScale;
    return this;
  }

  public LB lineHeight(int lineHeight) {
    this.lineHeight = lineHeight;
    return this;
  }

  public LB wrap(boolean wrap, float widthWrap) {
    this.wrap = wrap;
    this.widthWrap = widthWrap;
    return this;
  }

  @Override
  public Label build() {
    Label.LabelStyle ls = new Label.LabelStyle();
    ls.font = adapter.getBitmapFont(fontKey);
    ls.fontColor = color == null ? null : Color.valueOf(color);
    Label res = new Label(text, ls);
    res.setFontScale(fontScale);
    if (wrap) {
      res.setWrap(true);
      res.setWidth(widthWrap);
    }
    if (lineHeight != -1)
      res.getStyle().font.getData().setLineHeight(lineHeight);
    applyCommonProps(res);
//    res.setVisible(false);
    return res;
  }
}