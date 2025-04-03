package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GUtils {



  public static Vector2 absPos(Actor c, float x, float y, int align) {
    return c.localToStageCoordinates(new Vector2(c.getX(align) + x - c.getX(), c.getY(align) + y - c.getY()));
  }

  public static Vector2 absPos(Actor c, int align) {
    return c.localToStageCoordinates(new Vector2(c.getX(align) - c.getX(), c.getY(align) - c.getY()));
  }
}
