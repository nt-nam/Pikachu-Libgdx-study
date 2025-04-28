package com.mygame.pikachu.screen.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

// ProgressBar Builder - Tùy chỉnh ProgressBar với hiệu ứng che phủ
public class ProgressBarCr extends ProgressBar implements Disposable {
  private Image coverImage; // Lớp che phủ
  private float coverWidth = 0.0f; // Chiều rộng hiện tại của lớp che phủ
  private float coverSpeed = 200.0f; // Tốc độ mở rộng (pixel/giây)
  private boolean isPaused = false; // Trạng thái tạm dừng
  private boolean isCoverEffectEnabled = true; // Bật/tắt hiệu ứng che phủ
  private Stage stage; // Stage chứa PBB (để quản lý coverImage)

  // Constructor với cấu hình mặc định
  public ProgressBarCr(float min, float max, float stepSize, boolean vertical, Stage stage) {
    super(min, max, stepSize, vertical, createDefaultStyle());
    this.stage = stage;
    initializeCoverImage();
  }

  // Constructor với style tùy chỉnh
  public ProgressBarCr(float min, float max, float stepSize, boolean vertical, ProgressBarStyle style, Stage stage) {
    super(min, max, stepSize, vertical, style);
    this.stage = stage;
    initializeCoverImage();
  }

  // Tạo style mặc định với giao diện cải tiến
  private static ProgressBarStyle createDefaultStyle() {
    ProgressBarStyle style = new ProgressBarStyle();
    final float barHeight = 30;

    // Tạo Pixmap cho background (gradient xám)
    Pixmap pixmap = new Pixmap(200, (int) barHeight, Pixmap.Format.RGBA8888);
    for (int x = 0; x < 200; x++) {
      float t = x / 200f;
      pixmap.setColor(0.5f + t * 0.2f, 0.5f + t * 0.2f, 0.5f + t * 0.2f, 1);
      pixmap.fillRectangle(x, 0, 1, (int) barHeight);
    }
    TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new Texture(pixmap));

    // Tạo Pixmap cho knobBefore (gradient xanh)
    pixmap.setColor(0, 0, 0, 1);
    pixmap.fill();
    for (int x = 0; x < 200; x++) {
      float t = x / 200f;
      pixmap.setColor(0, 0.8f + t * 0.2f, 0, 1);
      pixmap.fillRectangle(x, 0, 1, (int) barHeight);
    }
    TextureRegionDrawable knobDrawable = new TextureRegionDrawable(new Texture(pixmap));
    pixmap.dispose();

    style.background = backgroundDrawable;
    style.knobBefore = knobDrawable;
    return style;
  }

  // Khởi tạo Image làm lớp che phủ
  private void initializeCoverImage() {
    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.DARK_GRAY); // Màu xám đậm cho lớp che phủ
    pixmap.fill();
    Texture coverTexture = new Texture(pixmap);
    pixmap.dispose();

    coverImage = new Image(new TextureRegionDrawable(new TextureRegion(coverTexture)));
    coverImage.setSize(0, getHeight());
    coverImage.setPosition(getX() + getWidth(), getY());
    if (stage != null) {
      stage.addActor(coverImage); // Thêm vào Stage
    }
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    if (!isPaused && isCoverEffectEnabled) {
      updateCover(delta);
    }
  }

  // Cập nhật hiệu ứng che phủ
  private void updateCover(float delta) {
    // Tính chiều rộng dựa trên giá trị ProgressBar
    float targetWidth = (1 - getVisualValue() / getMaxValue()) * getWidth();
    // Sử dụng Interpolation để mượt mà
    coverWidth = Interpolation.linear.apply(coverWidth, targetWidth, 0.1f);
    coverImage.setSize(coverWidth, getHeight());
    coverImage.setPosition(getX() + getWidth() - coverWidth, getY());

    // Hiệu ứng nhấp nháy nhẹ khi giá trị thấp
    if (getVisualValue() < getMaxValue() * 0.2f) {
      float alpha = (float) (0.7 + 0.3 * Math.sin(System.currentTimeMillis() / 200.0));
      setColor(1, 1, 1, alpha);
    } else {
      setColor(1, 1, 1, 1);
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    // Đảm bảo coverImage được vẽ ngay sau ProgressBar
    if (coverImage.getStage() == null && stage != null) {
      stage.addActor(coverImage);
    }
  }

  // Quản lý tạm dừng
  public void setPaused(boolean paused) {
    this.isPaused = paused;
  }

  public boolean isPaused() {
    return isPaused;
  }

  // Bật/tắt hiệu ứng che phủ
  public void setCoverEffectEnabled(boolean enabled) {
    this.isCoverEffectEnabled = enabled;
    coverImage.setVisible(enabled);
  }

  // Cập nhật vị trí và kích thước khi ProgressBar thay đổi
  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);
    coverImage.setPosition(x + getWidth() - coverWidth, y);
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);
    coverImage.setSize(coverWidth, height);
    coverImage.setPosition(getX() + getWidth() - coverWidth, getY());
  }

  // Thiết lập tốc độ che phủ
  public void setCoverSpeed(float speed) {
    this.coverSpeed = speed;
  }

  @Override
  public void dispose() {
    if (coverImage != null && coverImage.getDrawable() != null) {
      ((TextureRegionDrawable) coverImage.getDrawable()).getRegion().getTexture().dispose();
    }
    if (getStyle().background != null) {
      ((TextureRegionDrawable) getStyle().background).getRegion().getTexture().dispose();
    }
    if (getStyle().knobBefore != null) {
      ((TextureRegionDrawable) getStyle().knobBefore).getRegion().getTexture().dispose();
    }
  }
}