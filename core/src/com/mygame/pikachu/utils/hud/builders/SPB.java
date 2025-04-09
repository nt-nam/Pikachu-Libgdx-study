package com.mygame.pikachu.utils.hud.builders;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

@SuppressWarnings("unused")
public class SPB extends AbstractActorBuilder<ScrollPane> {
  public SPB() {
  }

  public static SPB New() {
    return new SPB();
  }

  private Table table;
  private boolean disableX, disableY;


  public SPB table(Table table) {
    this.table = table;
    return this;
  }

  public SPB disableScroll(boolean dx, boolean dy) {
    disableX = dx;
    disableY = dy;
    return this;
  }


  @Override
  public ScrollPane build() {
    ScrollPane res = new ScrollPane(table);
    res.setSize(w, h);
    res.setPosition(x, y);
    res.setScrollingDisabled(disableX, disableY);
    applyCommonProps(res);
    return res;
  }
}
