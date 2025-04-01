package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.screen.PlayScreen;

public class Animal extends Actor {
  private int id;
  private final TextureRegion region;
  private final int distance;
  private boolean isSelected;
  private int gridX, gridY;

  public Animal(TextureRegion region, int id, int gridX, int gridY, int distance) {
    this.id = id;
    this.gridX = gridX;
    this.gridY = gridY;
    this.isSelected = false;
    this.distance = distance;
    this.region = region;

    setBounds(gridX * distance, gridY * distance, distance - 5, distance - 5);
    setOrigin(getWidth() / 2, getHeight() / 2);

    addClickListener();
  }

  private void addClickListener() {
    this.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        toggleSelection();
        return true;
      }
    });
  }

  public void toggleSelection() {
    setSelected(!isSelected);

  }
  public String getKey() {
    return gridX + "," + gridY;
  }

  public void moveTo() {
    addAction(Actions.moveTo(gridX * distance, gridY * distance, 0.2f));
  }

  Color color = getColor();

  @Override
  public void draw(Batch batch, float parentAlpha) {

    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
        getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

  }

  public boolean getSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    this.isSelected = selected;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getGridX() {
    return gridX;
  }

  public int getGridY() {
    return gridY;
  }

  public void setGridX(int gridX) {
    this.gridX = gridX;
  }

  public void setGridY(int gridY) {
    this.gridY = gridY;
  }

}
