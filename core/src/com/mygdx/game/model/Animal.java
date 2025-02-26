package com.mygdx.game.model;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.control.PathResult;
import com.mygdx.game.screen.PlayScreen;


public class Animal extends Actor {
  private final int id;
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
//    setBounds(gridX * distance, gridY * distance, distance, distance);
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
      PlayScreen.animalSelect = this;
      return;
    }

//     removed
    if (PlayScreen.animalSelect.getStage() == null) {
      PlayScreen.animalSelect.isSelected = false;
      PlayScreen.animalSelect = this;
      return;
    }

    //equal
    if (PlayScreen.animalSelect == this) {
      PlayScreen.animalSelect = null;
      return;
    }
    // delete
//    PathResult result = PlayScreen.checkEdible(PlayScreen.animalSelect, this);
//    if (PlayScreen.animalSelect.getId() == id && result.isEdible()) {
//      for (int[] coordinate : result.getPathCoordinates()) {
//        System.out.println("Tọa độ: (" + coordinate[0] + ", " + coordinate[1] + ")");
//      }
    if (PlayScreen.animalSelect.getId() == id && PlayScreen.checkEdible(PlayScreen.animalSelect, this)) {
      PlayScreen.removeAnimalSelect(this);
      removeAni();
      PlayScreen.drawMatrix();
      return;
    }
    PlayScreen.animalSelect.setSelected(false);
    PlayScreen.animalSelect = this;
  }

  private void removeAni() {

//    PlayScreen.animalHashMap.remove(getKey());
//    PlayScreen.animalHashMap.remove(PlayScreen.animalSelect.getKey());
//    PlayScreen.animalSelect.addAction(removeAnimal);
//    this.addAction(removeAnimal);
//    PlayScreen.removeAnimalSelect();
//    this.remove();
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

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    this.isSelected = selected;
    addAction(selected ? Actions.scaleTo(1.2f, 1.2f, 0.2f) : Actions.scaleTo(1, 1, 0.2f));
  }

  public int getId() {
    return id;
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
