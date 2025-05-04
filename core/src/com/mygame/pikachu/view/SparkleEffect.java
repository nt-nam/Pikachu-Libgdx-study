package com.mygame.pikachu.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class SparkleEffect extends Group {
  private final float w, h;
  private Texture sparkleTexture;
  private ArrayList<Image> imageList;
  private int numberEffect;

  public SparkleEffect(float w, float h) {
    this.w = w;
    this.h = h;
    setSize(w,h);
    imageList = new ArrayList<>();
    sparkleTexture = new Texture(Gdx.files.internal("eff/flare_01_0.png"));
    numberEffect = 6;
    init();
  }

  private void init() {
    if (sparkleTexture == null) return;
    for (int i = 0; i < numberEffect; i++) {
      Image a = new Image(sparkleTexture);
      a.setVisible(false);
      a.setOrigin(Align.center);
      imageList.add(a);
      addActor(a);
    }

  }

  public void start() {
    for (Image a : imageList) {
      float x = MathUtils.random(0, w);
      float y = MathUtils.random(0, h);
      float time = MathUtils.random(4, 7);
      a.setVisible(true);
      a.setPosition(x, y);
      a.setScale(0);
      a.setRotation(0);
      a.clearActions();
      a.addAction(Actions.forever(Actions.sequence(
          Actions.scaleTo(0, 0),
          Actions.parallel(
              Actions.scaleTo(0.6f, 0.6f, time / 2)
              , Actions.rotateTo(90, time)
          )
          , Actions.parallel(
              Actions.scaleTo(0f, 0f, time / 2)
              , Actions.rotateTo(-90, time)
          )
          , Actions.run(() -> {
            float x2 = MathUtils.random(0, w);
            float y2 = MathUtils.random(0, h);
            a.setPosition(x2, y2);
          })
      )));
    }
  }

  public void stop() {
    for (Image a : imageList) {
      a.clearActions();
      a.setVisible(false);
    }
  }

  public void dispose() {
    for (Image a : imageList) {
      a.clear();
      a.remove();
    }
    if (sparkleTexture != null) {
      sparkleTexture.dispose();
      sparkleTexture = null;
    }
  }

}
