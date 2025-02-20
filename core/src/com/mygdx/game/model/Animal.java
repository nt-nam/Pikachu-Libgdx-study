package com.mygdx.game.model;

import static com.mygdx.game.screen.PlayScreen.checkEdible;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.screen.PlayScreen;

//public class Animal extends Actor {
//    int id;
//    int gridX, gridY;
//    boolean isSelected;
//    Image image;
//
//    public Animal(int id, int gridX, int gridY, Image image) {
//        this.id = id;
//        this.gridX = gridX;
//        this.gridY = gridY;
//        this.image = image;
//        setBounds(gridX * 110, gridY * 110, 100, 100);
//        isSelected = false;
//        clickSelect();
//
//    }
//
//    private void clickSelect() {
//        addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                checkEdible(PlayScreen.animalSelect, Animal.this);
//                super.clicked(event, x, y);
//            }
//        });
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        image.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
//        image.setOrigin(image.getWidth()/2,image.getHeight()/2);
//        if(isSelected){
//            image.setScale(1.3f,1.3f);
//        }else{
//            image.setScale(1.0f,1.0f);
//        }
//        image.draw(batch, parentAlpha);
//    }
//
//    public void setSelected(Animal a0) {
//        isSelected = !isSelected;
//
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getGridX() {
//        return gridX;
//    }
//
//    public void setGridX(int gridX) {
//        this.gridX = gridX;
//    }
//
//    public int getGridY() {
//        return gridY;
//    }
//
//    public void setGridY(int gridY) {
//        this.gridY = gridY;
//    }
//}
public class Animal extends Actor {
  private TextureRegion region;
  private boolean isSelected;
  private int id;
  private int gridX, gridY;
  private int distance;

  public Animal(TextureRegion region, int id, int gridX, int gridY, int distance) {
    this.id = id;
    this.gridX = gridX;
    this.gridY = gridY;
    this.isSelected = false;
    this.distance = distance;
    this.region = region;

    setBounds(gridX * distance, gridY * distance, distance - 10, distance - 10);
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
//    isSelected = !isSelected;
//    addAction(isSelected ? Actions.scaleTo(1.2f, 1.2f, 0.5f) : Actions.scaleTo(1, 1, 0.2f));
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

    // != id
//    if (PlayScreen.animalSelect.getId() != id) {
////      PlayScreen.animalSelect.isSelected = false; // chưa hiểu vì sao dòng này khác dòng dưới
//      PlayScreen.animalSelect.setSelected(false);
//      PlayScreen.animalSelect = this;
//      return;
//    }

    //equal
    if (PlayScreen.animalSelect == this) {
      PlayScreen.animalSelect = null;
      return;
    }
    // delete
    if (PlayScreen.animalSelect.getId() == id && PlayScreen.checkEdible(PlayScreen.animalSelect, this)) {
      removeAni();
      PlayScreen.drawMatrix();
      return;
    }
    PlayScreen.animalSelect.setSelected(false);
    PlayScreen.animalSelect = this;
  }

  private void removeAni() {
    PlayScreen.removeAnimalSelect();
    PlayScreen.animalHashMap.remove(getKey());
    this.remove();
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
        getWidth(), getHeight(), getScaleX() * 0.8f, getScaleY() * 0.8f, getRotation());

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
