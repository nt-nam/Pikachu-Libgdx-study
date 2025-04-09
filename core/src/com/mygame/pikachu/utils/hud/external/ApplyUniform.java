package com.mygame.pikachu.utils.hud.external;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

@SuppressWarnings("unused")
@FunctionalInterface
public interface ApplyUniform {
  void apply(ShaderProgram shader);
}