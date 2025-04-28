package com.mygame.pikachu.utils.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class FallingAction extends TemporalAction {
  private float startX, startY; // Toạ độ tuyệt đối so với Stage
  private float targetY; // Vị trí Y đích tuyệt đối (mặt đất)
  private float gravity; // Gia tốc trọng lực (pixel/s²)
  private boolean isCircle; // Hình tròn (true) hay hình vuông (false)
  private float lastAngle; // Lưu góc xoay trước đó để làm mượt
  private float parentY; // Toạ độ Y của parent trong Stage

  public FallingAction(float targetY, float gravity, boolean isCircle) {
    this.targetY = targetY;
    this.gravity = gravity;
    this.isCircle = isCircle;
    this.lastAngle = 0;
  }

  @Override
  protected void begin() {
    // Lấy toạ độ tuyệt đối của Actor trong Stage
    Vector2 stagePos = actor.localToStageCoordinates(new Vector2(0, 0));
    startX = stagePos.x;
    startY = stagePos.y;

    // Lấy toạ độ Y của parent trong Stage
    if (actor.getParent() != null) {
      parentY = actor.getParent().localToStageCoordinates(new Vector2(0, 0)).y;
    } else {
      parentY = 0; // Không có parent, parentY = 0
    }

    // Tính thời gian rơi: t = sqrt(2*(startY - targetY)/g)
    float distance = startY - targetY;
    if (distance > 0) {
      float duration = (float) Math.sqrt(2 * distance / gravity);
      setDuration(duration);
    } else {
      setDuration(0); // Đã ở hoặc dưới targetY
    }
  }

  @Override
  protected void update(float percent) {
    // percent là tỷ lệ từ 0 đến 1
    float t = percent * getDuration(); // Thời gian thực tế (giây)

    // Tính vị trí Y tuyệt đối: y = startY - (1/2)*g*t^2
    float yAbsolute = startY - 0.5f * gravity * t * t;

    // Chuyển y tuyệt đối thành y tương đối so với parent
    float yRelative = yAbsolute - parentY;

    // Tính vận tốc Y: vy = -g*t
    float vy = -gravity * t;

    // Tính góc xoay (hướng xuống, bù -90 cho sprite hướng lên)
    float angle = MathUtils.atan2(vy, 0) * MathUtils.radiansToDegrees - 90;

    // Làm mượt góc xoay
    angle = MathUtils.lerpAngleDeg(lastAngle, angle, 0.5f);
    lastAngle = angle;

    // Cập nhật vị trí và góc xoay
    actor.setPosition(actor.getX(), yRelative);
    actor.setRotation(angle);
  }

  @Override
  protected void end() {
    // Đặt Actor đúng vị trí đích tuyệt đối
    float yRelative = targetY - parentY;
    actor.setPosition(actor.getX(), yRelative);

    // Thêm hiệu ứng lăn nhẹ
    float rollDistance = isCircle ? MathUtils.random(-100f, 100f) : MathUtils.random(-20f, 20f); // Hình tròn lăn xa hơn
    float rollRotation = isCircle ? MathUtils.random(180f, 360f) * MathUtils.randomSign() : MathUtils.random(-20f, 20f); // Hình tròn xoay nhiều hơn
    float rollDuration = isCircle ? 0.8f : 0.3f; // Hình tròn lăn lâu hơn

    actor.addAction(Actions.sequence(
        // Nén nhẹ khi chạm đất
        Actions.scaleTo(1f, 0.8f, 0.1f),
        Actions.scaleTo(1f, 1f, 0.1f),
        // Lăn nhẹ
        Actions.parallel(
            Actions.moveBy(rollDistance, 0, rollDuration, Interpolation.sineOut),
            Actions.rotateBy(rollRotation, rollDuration, Interpolation.sineOut)
        )
    ));
  }

  // Getter và setter để tái sử dụng Action
  public void setTargetY(float targetY) {
    this.targetY = targetY;
  }
}