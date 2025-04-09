package com.mygame.pikachu.utils.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

@SuppressWarnings("unused")
public class Button extends MapGroup {
  protected Image bg;
  protected Image bgd;
  protected Image icon;
  protected Label text;
  protected Drawable temp;

  public Button() {
    super(0, 0);
    addListener(new ClickListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (bgd != null) {
          temp = bg.getDrawable();
          bg.setDrawable(bgd.getDrawable());
          bg.setSize(bgd.getWidth(), bgd.getHeight());
          setTouchable(Touchable.disabled);
        } else
          bg.addAction(Actions.color(Color.GRAY, .05f));
        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (temp != null) {
          bg.setDrawable(temp);
          bg.setSize(getWidth(), getHeight());
          temp = null;
          setTouchable(Touchable.enabled);
        } else
          bg.addAction(Actions.color(Color.WHITE, .05f));
        super.touchUp(event, x, y, pointer, button);
      }
    });
  }

  public void setBg(Image bg) {
    if (bg == null)
      return;
    int oldIndex = 0;
    if (this.bg != null)
      oldIndex = this.bg.getZIndex();
    removeActor(this.bg);
    this.bg = bg;
    addActorAt(oldIndex, this.bg);
    setSize(this.bg.getWidth(), this.bg.getHeight());
  }

  public void setBgd(Image bgd) {
    if (bgd == null)
      return;
    this.bgd = bgd;
  }
  
  public Image getIcon() {
    return this.icon;
  }

  public void setIcon(Image icon, float padX, float padY, int align) {
    if (this.bg == null)
      return;
    removeActor(this.icon);
    this.icon = icon;
    addActor(this.icon);
    align(padX, padY, align, this.icon);
  }
  
  public void setIcon(Image icon, float padX, float padY, int align, float scaleX, float scaleY) {
    if (this.bg == null)
      return;
    removeActor(this.icon);
    this.icon = icon;
    this.icon.setScale(scaleX, scaleY);
    addActor(this.icon);
    align(padX, padY, align, this.icon);
  }

  public void setLabel(Label label, float padX, float padY, int align) {
    removeActor(label);
    this.text = label;
    addActor(this.text);
    align(padX, padY, align, this.text);
  }

  public void setText(String text) {
    this.text.setText(text);
  }

  public Image getBg(){
    return bg;
  }
  
  public Label getLabel(){
    return text;
  }

  public Image getBgd(){
    return bgd;
  }
}
