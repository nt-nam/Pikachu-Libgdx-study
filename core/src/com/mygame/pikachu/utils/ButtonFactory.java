package com.mygame.pikachu.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygame.pikachu.GMain;
import com.mygame.pikachu.model.Player;
import com.mygame.pikachu.view.Board;

public class ButtonFactory {
  private SoundManager soundManager;

  // Constructor
  public ButtonFactory(SoundManager soundManager) {
    this.soundManager = soundManager;
  }

  public TextButton.TextButtonStyle createButtonStyle() {
    // Tạo Pixmap cho trạng thái "lên" (up)
    Pixmap upPixmap = createButtonPixmap(150, 50, Color.BLUE, Color.WHITE);
    // Tạo Pixmap cho trạng thái "nhấn" (down)
    Pixmap downPixmap = createButtonPixmap(150, 50, Color.DARK_GRAY, Color.WHITE);

    // Chuyển Pixmap thành Texture
    Texture upTexture = new Texture(upPixmap);
    Texture downTexture = new Texture(downPixmap);

    // Giải phóng Pixmap để tránh rò rỉ bộ nhớ
    upPixmap.dispose();
    downPixmap.dispose();

    // Tạo TextButtonStyle
    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
    style.down = new TextureRegionDrawable(new TextureRegion(downTexture));

    // (Tùy chọn) Thêm font nếu muốn hiển thị chữ trên nút
    Skin skin = new Skin();
    skin.add("default-font", new com.badlogic.gdx.graphics.g2d.BitmapFont());
    style.font = skin.getFont("default-font");
    style.fontColor = Color.WHITE;

    return style;
  }

  public Pixmap createButtonPixmap(int width, int height, Color backgroundColor, Color borderColor) {
    Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

    // Vẽ viền
    pixmap.setColor(borderColor);
    pixmap.drawRectangle(0, 0, width, height);

    // Vẽ nền (bên trong viền)
    pixmap.setColor(backgroundColor);
    pixmap.fillRectangle(1, 1, width - 2, height - 2);

    return pixmap;
  }

}