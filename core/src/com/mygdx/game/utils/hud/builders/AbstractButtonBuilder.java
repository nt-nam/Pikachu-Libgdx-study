package com.mygdx.game.utils.hud.builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

@SuppressWarnings("unused")
public abstract class AbstractButtonBuilder<T extends Button> extends AbstractWidgetGroupBuilder<T> {

  protected   String                      text = "";
  protected   Drawable    checked;
  protected   Drawable    checkedFocused;
  protected   Drawable    checkedOver;
  protected   Drawable    disabled;
  protected   Drawable    down;
  protected   Drawable    focused;
  protected   Drawable    over;
  protected   Drawable    up;

  protected   float       checkedOffsetX;
  protected   float       checkedOffsetY;
  protected   float       pressedOffsetX;
  protected   float       pressedOffsetY;
  protected   float       unpressedOffsetX;
  protected   float       unpressedOffsetY;


  protected   BitmapFont  font;
  protected   Color       checkedFontColor;
  protected   Color       checkedOverFontColor;
  protected   Color       disabledFontColor;
  protected   Color       downFontColor;
  protected   Color       fontColor;
  protected   Color       overFontColor;

  protected   Drawable    checkboxOff;
  protected   Drawable    checkboxOn;
  protected   Drawable    checkboxOffDisabled;
  protected   Drawable    checkboxOnDisabled;
  protected   Drawable    checkboxOnOver;
  protected   Drawable    checkboxOver;

  public AbstractButtonBuilder() {

  }

  @Override
  protected void reset() {
    super.reset();
    this.checked            = null;
    this.checkedFocused     = null;
    this.checkedOver        = null;
    this.disabled           = null;
    this.down               = null;
    this.focused            = null;
    this.over               = null;
    this.up                 = null;

    this.checkedOffsetX     = 0;
    this.checkedOffsetY     = 0;
    this.pressedOffsetX     = 0;
    this.pressedOffsetY     = 0;
    this.unpressedOffsetX   = 0;
    this.unpressedOffsetY   = 0;

    this.font                  = null;
    this.checkedFontColor      = Color.WHITE;
    this.checkedOverFontColor  = Color.WHITE;
    this.disabledFontColor     = Color.WHITE;
    this.downFontColor         = Color.WHITE;
    this.fontColor             = Color.WHITE;
    this.overFontColor         = Color.WHITE;

    this.checkboxOff           = null;
    this.checkboxOn            = null;
    this.checkboxOffDisabled   = null;
    this.checkboxOnDisabled    = null;
    this.checkboxOnOver        = null;
    this.checkboxOver          = null;

    this.text                  = "";
  }

  public AbstractButtonBuilder<T> font(String key) {
    this.font = adapter.getBitmapFont(key);return this;
  }

  public AbstractButtonBuilder<T> fCheckFontColor(String value) {
    this.checkedFontColor = Color.valueOf(value); return this;
  }

  public AbstractButtonBuilder<T> fCheckOverFontColor(String value) {
    this.checkedOverFontColor = Color.valueOf(value); return this;
  }

  public AbstractButtonBuilder<T> fDisableFontColor(String value) {
    this.disabledFontColor = Color.valueOf(value); return this;
  }

  public AbstractButtonBuilder<T> fDownFontColor(String value) {
    this.downFontColor = Color.valueOf(value); return this;
  }

  public AbstractButtonBuilder<T> fontColor(String value) {
    this.fontColor = Color.valueOf(value); return this;
  }

  public AbstractButtonBuilder<T> fOverFontColor(String value) {
    this.overFontColor = Color.valueOf(value); return this;
  }

  //----
  public AbstractButtonBuilder<T> dChecked(String key) {
    this.checked = adapter.getDrawable(key);
    return this;

  }

  public AbstractButtonBuilder<T> dCheckedFocus(String key) {
    this.checkedFocused = adapter.getDrawable(key);
    return this;
  }

  public AbstractButtonBuilder<T> dCheckedOver(String key) {
    this.checkedOver = adapter.getDrawable(key);
    return this;
  }

  public AbstractButtonBuilder<T> dDisable(String key) {
    this.disabled = adapter.getDrawable(key);
    return this;
  }

  public AbstractButtonBuilder<T> dDown(String key) {
    this.down = adapter.getDrawable(key);
    return this;
  }

  public AbstractButtonBuilder<T> dFocused(String key) {
    this.focused = adapter.getDrawable(key);
    return this;
  }

  public AbstractButtonBuilder<T> dOver(String key) {
    this.over = adapter.getDrawable(key);
    return this;
  }

  public AbstractButtonBuilder<T> dUp(String key) {
    this.up = adapter.getDrawable(key);
    return this;
  }

  public AbstractButtonBuilder<T> vCheckedOffX(float off) {
    this.checkedOffsetX = off;
    return this;
  }

  public AbstractButtonBuilder<T> vCheckedOffY(float off) {
    this.checkedOffsetY = off;
    return this;
  }

  public AbstractButtonBuilder<T> vPressedOffX(float off) {
    this.pressedOffsetX = off;
    return this;
  }

  public AbstractButtonBuilder<T> vPressedOffY(float off) {
    this.pressedOffsetY = off;
    return this;
  }

  public AbstractButtonBuilder<T> vUnPressOffX(float off) {
    this.unpressedOffsetX = off;
    return this;
  }

  public AbstractButtonBuilder<T> vUnPressOffY(float off) {
    this.unpressedOffsetY = off;
    return this;
  }

  public AbstractButtonBuilder<T> text(String text) {
    this.text = text;return this;
  }

  public AbstractButtonBuilder<T> dCheckboxOff(String key) {
    this.checkboxOff = adapter.getDrawable(key);return this;
  }

  public AbstractButtonBuilder<T> dCheckboxOn(String key) {
    this.checkboxOn = adapter.getDrawable(key);return this;
  }

  public AbstractButtonBuilder<T> dCheckboxOffDisabled(String key) {
    this.checkboxOffDisabled = adapter.getDrawable(key);return this;
  }

  public AbstractButtonBuilder<T> dCheckboxOnDisabled(String key) {
    this.checkboxOnDisabled = adapter.getDrawable(key);return this;
  }

  public AbstractButtonBuilder<T> dCheckboxOnOver(String key) {
    this.checkboxOnOver = adapter.getDrawable(key);return this;
  }

  public AbstractButtonBuilder<T> dCheckboxOver(String key) {
    this.checkboxOver = adapter.getDrawable(key);return this;
  }

}