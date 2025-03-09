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

    // null thi return
    if (PlayScreen.animalSelect == null) {
      PlayScreen.setAnimalSelect(this);
      return;
    }

    if (PlayScreen.animalSelect.getStage() == null) {
      PlayScreen.animalSelect.isSelected = false;
      PlayScreen.setAnimalSelect(this);
      return;
    }

    //equal
    if (PlayScreen.animalSelect == this) {
      PlayScreen.setAnimalSelect(null);
      return;
    }

    if (PlayScreen.animalSelect.getId() == id && PlayScreen.checkEdible(PlayScreen.animalSelect, this)) {
      PlayScreen.removeAnimalSelect(this);
      PlayScreen.setAnimalSelect(null);
//      PlayScreen.drawMatrix();
      return;
    }
    PlayScreen.animalSelect.setSelected(false);
    PlayScreen.setAnimalSelect(this);
  }
  public static void removeAnimalSelect(Animal animal) {
    // Loại bỏ Animal khỏi animalHashMap
    PlayScreen.animalHashMap.remove(animal.getKey());
    PlayScreen.animalHashMap.remove(PlayScreen.animalSelect.getKey());

    // Xóa khỏi stage
    animal.addAction(Actions.sequence(
        Actions.delay(1f),    // Đợi 1 giây
        Actions.removeActor() // Xóa khỏi stage
    ));
    PlayScreen.animalSelect.addAction(Actions.sequence(
        Actions.delay(1f),    // Đợi 1 giây
        Actions.removeActor() // Xóa khỏi stage
    ));
  }
  public String getKey() {
    return gridX + "," + gridY;
  }

  public void moveTo() {
    addAction(Actions.moveTo(gridX * distance, gridY * distance, 0.5f));
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
//    setDebug(isSelected);
//    addAction(selected ? Actions.scaleTo(1.2f, 1.2f, 0.2f) : Actions.scaleTo(1, 1, 0.2f));
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
