package com.mygame.pikachu.utils.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Gdx;

public class ParabolicAction extends TemporalAction {
  private float startX, startY;
  private float targetX, targetY;
  private float maxHeight; // Độ cao tối đa của parabol so với startY hoặc targetY
  private float a; // Hệ số điều chỉnh độ cong parabol

  public ParabolicAction(float targetX, float targetY, float duration, float maxHeight) {
    this.targetX = targetX;
    this.targetY = targetY;
    this.maxHeight = maxHeight;
    setDuration(duration);
  }

  @Override
  protected void begin() {
    // Lưu vị trí bắt đầu của Actor
    startX = actor.getX();
    startY = actor.getY();
    // Tính hệ số a để đảm bảo đỉnh parabol không vượt quá maxHeight
    float h = Math.min(maxHeight, Gdx.graphics.getHeight() * 0.3f); // Giới hạn maxHeight
    a = -4 * h; // Đỉnh tại t=0.5, y=startY + h
  }

  @Override
  protected void update(float percent) {
    // percent là tỷ lệ từ 0 đến 1
    float t = percent; // Loại bỏ nhân 2 để parabol hoàn thành trong duration

    // Tính vị trí X (di chuyển đều)
    float x = startX + (targetX - startX) * t;

    // Tính vị trí Y theo phương trình parabol: y = a*t*(t-1) + (targetY - startY)*t + startY
    float y = a * t * (t - 1) + (targetY - startY) * t + startY;

    // Giới hạn y trong màn hình
    y = MathUtils.clamp(y, 0, Gdx.graphics.getHeight() - actor.getHeight());

    // Tính vận tốc tức thời (đạo hàm của vị trí)
    float vx = (targetX - startX) / getDuration(); // Vận tốc X
    float vy = a * (2 * t - 1) + (targetY - startY); // Vận tốc Y (đạo hàm của y)

    // Tính góc xoay (radian -> độ)
    float angle = MathUtils.atan2(vy, vx) * MathUtils.radiansToDegrees;

    // Cập nhật vị trí và góc xoay của Actor
    actor.setPosition(x, y);
    actor.setRotation(angle - 90); // Giữ bù -90 để khớp với sprite
  }

  @Override
  protected void end() {
    // Đảm bảo Actor ở đúng vị trí mục tiêu khi kết thúc
    actor.setPosition(targetX, targetY);
    actor.setRotation(0); // Đặt lại góc
  }

  // Getter và setter để tái sử dụng Action
  public void setTarget(float targetX, float targetY) {
    this.targetX = targetX;
    this.targetY = targetY;
  }
}