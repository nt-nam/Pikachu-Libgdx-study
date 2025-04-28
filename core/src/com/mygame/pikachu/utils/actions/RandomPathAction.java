package com.mygame.pikachu.utils.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class RandomPathAction extends TemporalAction {
  private float startX, startY;
  private float targetX, targetY;
  private float maxDeviation; // Độ lệch tối đa so với đường thẳng
  private Array<Float> noiseX; // Noise cho trục X
  private Array<Float> noiseY; // Noise cho trục Y
  private int noiseSteps = 10; // Số điểm noise để nội suy
  private float seed; // Seed cho tính ngẫu nhiên

  public RandomPathAction(float targetX, float targetY, float duration, float maxDeviation) {
    this.targetX = targetX;
    this.targetY = targetY;
    this.maxDeviation = maxDeviation;
    setDuration(duration);
    this.seed = MathUtils.random(1000f); // Seed ngẫu nhiên cho noise
  }

  @Override
  protected void begin() {
    // Lưu vị trí bắt đầu của Actor
    startX = actor.getX();
    startY = actor.getY();

    // Tạo mảng noise cho X và Y
    noiseX = new Array<>();
    noiseY = new Array<>();
    noiseX.add(0f); // Điểm bắt đầu không lệch
    noiseY.add(0f);

    // Tạo các điểm noise ngẫu nhiên
    for (int i = 1; i < noiseSteps - 1; i++) {
      noiseX.add(MathUtils.random(-maxDeviation, maxDeviation));
      noiseY.add(MathUtils.random(-maxDeviation, maxDeviation));
    }
    noiseX.add(0f); // Điểm kết thúc không lệch
    noiseY.add(0f);
  }

  @Override
  protected void update(float percent) {
    // percent là tỷ lệ từ 0 đến 1
    float t = percent;

    // Tính vị trí trên đường thẳng
    float linearX = startX + (targetX - startX) * t;
    float linearY = startY + (targetY - startY) * t;

    // Tính giá trị noise bằng cách nội suy giữa các điểm noise
    float noiseIndex = t * (noiseSteps - 1);
    int index = (int) noiseIndex;
    float frac = noiseIndex - index;

    // Nội suy tuyến tính mượt mà
    float noiseXValue = MathUtils.lerp(noiseX.get(index), noiseX.get(Math.min(index + 1, noiseSteps - 1)), frac);
    float noiseYValue = MathUtils.lerp(noiseY.get(index), noiseY.get(Math.min(index + 1, noiseSteps - 1)), frac);

    // Tính vị trí cuối cùng
    float x = linearX + noiseXValue;
    float y = linearY + noiseYValue;

    // Tính vận tốc tức thời (gần đúng bằng cách lấy hiệu số)
    float deltaT = 0.01f;
    float nextT = Math.min(t + deltaT, 1f);
    float nextNoiseIndex = nextT * (noiseSteps - 1);
    int nextIndex = (int) nextNoiseIndex;
    float nextFrac = nextNoiseIndex - nextIndex;
    float nextNoiseX = MathUtils.lerp(noiseX.get(nextIndex), noiseX.get(Math.min(nextIndex + 1, noiseSteps - 1)), nextFrac);
    float nextNoiseY = MathUtils.lerp(noiseY.get(nextIndex), noiseY.get(Math.min(nextIndex + 1, noiseSteps - 1)), nextFrac);
    float nextX = startX + (targetX - startX) * nextT + nextNoiseX;
    float nextY = startY + (targetY - startY) * nextT + nextNoiseY;

    float vx = (nextX - x) / deltaT;
    float vy = (nextY - y) / deltaT;

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
    actor.setRotation(180); // Đặt lại góc
  }

  // Getter và setter để tái sử dụng Action
  public void setTarget(float targetX, float targetY) {
    this.targetX = targetX;
    this.targetY = targetY;
  }
}