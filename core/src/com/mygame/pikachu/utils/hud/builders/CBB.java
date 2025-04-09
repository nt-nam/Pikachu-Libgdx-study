package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;

@SuppressWarnings("unused")
public class CBB extends AbstractButtonBuilder<CheckBox> {
  public CBB() {
  }

  public static CBB New() {
    CBB inst = new CBB();
    inst.font       = adapter.getBitmapFont("font_nc3");
    inst.isChecked  = false;
    return inst;
  }

  protected boolean isChecked;


  public CBB isCheck(boolean isChecked) {
    this.isChecked = isChecked;
    return this;
  }

  @Override
  public CheckBox build() {
    CheckBox.CheckBoxStyle bs = new CheckBox.CheckBoxStyle();

    bs.checked          = checked;
    bs.checkedFocused   = checkedFocused;
    bs.checkedOver      = checkedOver;
    bs.disabled         = disabled;
    bs.down             = down;
    bs.focused          = focused;
    bs.over             = over;
    bs.up               = up;

    bs.checkedOffsetX   = checkedOffsetX;
    bs.checkedOffsetY   = checkedOffsetY;
    bs.pressedOffsetX   = pressedOffsetX;
    bs.pressedOffsetY   = pressedOffsetY;
    bs.unpressedOffsetX = unpressedOffsetX;
    bs.unpressedOffsetY = unpressedOffsetY;

    bs.font                   = font;
    bs.checkedFontColor       = checkedFontColor;
    bs.checkedOverFontColor   = checkedOverFontColor;
    bs.disabledFontColor      = disabledFontColor;
    bs.downFontColor          = downFontColor;
    bs.fontColor              = fontColor;
    bs.overFontColor          = overFontColor;

    bs.checkboxOff           = checkboxOff;
    bs.checkboxOn            = checkboxOn;
    bs.checkboxOffDisabled   = checkboxOffDisabled;
    bs.checkboxOnDisabled    = checkboxOnDisabled;
    bs.checkboxOnOver        = checkboxOnOver;
    bs.checkboxOver          = checkboxOver;

    CheckBox res             = new CheckBox(text, bs);
    applyCommonProps(res);
    return res;
  }
}