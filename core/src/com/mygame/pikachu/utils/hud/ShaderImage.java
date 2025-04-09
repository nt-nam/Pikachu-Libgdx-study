package com.mygame.pikachu.utils.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygame.pikachu.utils.hud.external.ApplyUniform;


/*Statical shader image*/
@SuppressWarnings("unused")
public class ShaderImage extends Image {

  public boolean active = false;
  public ShaderProgram shader;
  public ApplyUniform uniform;

  public ShaderImage() {
    super();
  }

  public ShaderImage(Drawable drawable) {
    super(drawable);
  }

  public ShaderImage(NinePatch region) {
    super(region);
  }

  public void setUniform(ApplyUniform uniform){
    this.uniform = uniform;
  }

  public void draw(Batch batch, float parentAlpha) {
    if(getDebug())
      System.out.println("active: "+ active);
    if (!active) {
      super.draw(batch, parentAlpha);
    } else {
      batch.end();
      batch.setShader(shader);
      batch.begin();
      if (uniform != null)
        uniform.apply(shader);
      super.draw(batch, parentAlpha);
      batch.end();
      batch.setShader(null);
      batch.begin();
    }
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}