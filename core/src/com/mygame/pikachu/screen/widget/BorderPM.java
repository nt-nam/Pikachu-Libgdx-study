package com.mygame.pikachu.screen.widget;

import com.badlogic.gdx.graphics.Pixmap;

public class BorderPM {
  private Pixmap pixmap;
  public BorderPM(){
  }

  public Pixmap get(float width, float height, float cornerRadius, int color) {
    pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
    pixmap.setColor(color); // Màu của hình chữ nhật (ví dụ: màu trắng)

    // Vẽ hình chữ nhật bo góc
    // Vẽ 4 góc (hình tròn)
    pixmap.fillCircle((int) cornerRadius, (int) cornerRadius, (int) cornerRadius); // Góc trên-trái
    pixmap.fillCircle((int) (width - cornerRadius), (int) cornerRadius, (int) cornerRadius); // Góc trên-phải
    pixmap.fillCircle((int) cornerRadius, (int) (height - cornerRadius), (int) cornerRadius); // Góc dưới-trái
    pixmap.fillCircle((int) (width - cornerRadius), (int) (height - cornerRadius), (int) cornerRadius); // Góc dưới-phải

    // Vẽ các cạnh và phần giữa
    pixmap.fillRectangle((int) cornerRadius, 0, (int) (width - 2 * cornerRadius), (int) height); // Cạnh trên và dưới
    pixmap.fillRectangle(0, (int) cornerRadius, (int) width, (int) (height - 2 * cornerRadius)); // Cạnh trái và phải
    return pixmap;
  }
}
