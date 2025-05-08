package com.mygame.pikachu.exSprite.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygame.pikachu.GMain;

public class GSprite extends Actor {
  Sprite sprite;

  public GSprite() {

  }

  public GSprite(TextureRegion region) {
    this.init(region);
  }

  public GSprite(Texture region) {
    this.init(region);
  }

  public Sprite getSprite() {
    return sprite;
  }

  public void init(TextureRegion region) {
    sprite = new Sprite(region);
    setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
    //sprite.setBounds(0, 0, region.getRegionWidth()/100f, region.getRegionHeight()/100f);
//        this.setWidth(sprite.getRegionWidth());
//        this.setHeight(sprite.getRegionHeight());


    //sprite.setScale(1/100f);
  }

  public void init(Texture texture) {
    sprite = new Sprite(texture);
    setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
    //sprite.setBounds(0, 0, region.getRegionWidth()/100f, region.getRegionHeight()/100f);
//        this.setWidth(sprite.getRegionWidth());
//        this.setHeight(sprite.getRegionHeight());


    //sprite.setScale(1/100f);
  }

  float PPM = 1f;

  public void setPPM(float PPM) {
    this.PPM = PPM;
    sprite.setBounds(0, 0, sprite.getRegionWidth() / PPM, sprite.getRegionHeight() / PPM);
    sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
//        setScale(1);
  }

  public void init(String atlasName, String regionName) {
    TextureAtlas atlas = GMain.getAssetHelper().getTextureAtlas(atlasName);
    TextureAtlas.AtlasRegion region = atlas.findRegion(regionName);
    init(region);
  }

  public void draw(Batch batch, float parentAlpha) {
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    sprite.draw(batch, parentAlpha);

  }

  public void setPosition(float x, float y) {
    super.setPosition(x, y);
    sprite.setPosition(this.getX() - sprite.getOriginX(), this.getY() - sprite.getOriginY());

  }

  public void setPosition(float x, float y, int align) {
    super.setPosition(x, y, align);
    sprite.setPosition(this.getX() - sprite.getOriginX(), this.getY() - sprite.getOriginY());

  }

  public void moveBy(float x, float y) {
    super.moveBy(x, y);
    sprite.setPosition(this.getX() - sprite.getOriginX(), this.getY() - sprite.getOriginY());

  }

  public void setX(float x) {
    super.setX(x);
    sprite.setX(x - sprite.getOriginX());
  }

  public void setY(float y) {
    super.setY(y);
    sprite.setY(y - sprite.getOriginY());
  }

  public void setOrigin(float originX, float originY) {
    super.setOrigin(originX, originY);
    sprite.setOrigin(originX, originY);
    //sprite.setOriginCenter();
  }

  public void setOrigin(int alignment) {
    super.setOrigin(alignment);
    sprite.setOrigin(this.getOriginX(), this.getOriginY());
  }

  public void setColor(Color c) {
    sprite.setColor(c);

  }

  public void setScale(float x, float y) {
    super.setScale(x, y);
//        sprite.setScale(1 / PPM * x, 1 / PPM * y);
    sprite.setScale(x, y);
  }

  @Override
  public void setScale(float scl) {
    super.setScale(scl);
//        scl /= PPM;
    sprite.setScale(scl);

  }

  public Color getColor() {
    return sprite.getColor();
  }

  public void setRotation(float degrees) {
    sprite.setRotation(degrees);
  }

  public float getRotation() {
    return sprite.getRotation();
  }


}
