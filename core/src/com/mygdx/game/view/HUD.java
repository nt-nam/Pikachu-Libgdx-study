package com.mygdx.game.view;

import static com.mygdx.game.utils.GameConstants.SCREEN_HEIGHT;
import static com.mygdx.game.utils.GameConstants.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.GameConstants;
import com.mygdx.game.utils.SkinManager;

public class HUD {
  private Stage stage;                // Stage để quản lý các thành phần UI
  private SpriteBatch batch;          // Batch để vẽ HUD
  private Player player;              // Tham chiếu đến người chơi
  private SkinManager skinManager;    // Quản lý texture của skin
  private BitmapFont font;            // Font để vẽ text
  private Label scoreLabel;           // Nhãn điểm số
  private Label coinsLabel;           // Nhãn số xu
  private Label levelLabel;           // Nhãn cấp độ
  private Label hintsLabel;           // Nhãn số gợi ý
  private Label shufflesLabel;        // Nhãn số xáo trộn
  private Label undosLabel;           // Nhãn số hoàn tác
  private Label timeLabel;            // Nhãn thời gian
  private ProgressBar timeline;
  private float timeLeft;             // Thời gian còn lại (giây)
  private float maxTime; // Thời gian tối đa của cấp độ
  private int totalPairs;
  private int matchedPairs;

  // Constructor
  public HUD(Player player, SkinManager skinManager) {
    this.player = player;
    this.skinManager = skinManager;
    this.batch = new SpriteBatch();
    this.stage = new Stage();
    this.font = new BitmapFont(Gdx.files.internal("font/arial_uni_30.fnt"));
    this.timeLeft = GameConstants.LEVEL_TIME_SECONDS;
    this.maxTime = GameConstants.LEVEL_TIME_SECONDS;
    this.matchedPairs = 0;
    // Thiết lập style cho Label
    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

    // Tạo các nhãn
    scoreLabel = new Label("Score: " + player.getScore(), labelStyle);
    scoreLabel.setPosition(10, SCREEN_HEIGHT - 30);

    coinsLabel = new Label("Coins: " + player.getCoins(), labelStyle);
    coinsLabel.setPosition(10, SCREEN_HEIGHT - 60);

    levelLabel = new Label("Level: " + player.getLevel(), labelStyle);
    levelLabel.setPosition(SCREEN_WIDTH * 0.5f - 40, SCREEN_HEIGHT - 50);

    hintsLabel = new Label("Hints: " + player.getHints(), labelStyle);
    hintsLabel.setPosition(SCREEN_WIDTH - 150, SCREEN_HEIGHT - 30);

    shufflesLabel = new Label("Shuffles: " + player.getShuffles(), labelStyle);
    shufflesLabel.setPosition(SCREEN_WIDTH - 150, SCREEN_HEIGHT - 60);

    undosLabel = new Label("Undos: " + player.getUndos(), labelStyle);
    undosLabel.setPosition(SCREEN_WIDTH - 150, SCREEN_HEIGHT - 90);

    timeLabel = new Label("Time: " + (int) timeLeft, labelStyle);
    timeLabel.setPosition(50, SCREEN_HEIGHT - 90);
    timeLabel.setAlignment(Align.center);

    // Tạo Timeline (thanh thời gian)
    ProgressBar.ProgressBarStyle style = newStyle();

    // Tạo ProgressBar với chiều cao mới
    timeline = new ProgressBar(0, 100, 1, false, style);
    timeline.setSize(stage.getWidth() * 0.8f, 30); // Đặt kích thước mới cho toàn bộ thanh
    timeline.setPosition(stage.getWidth() * 0.1f, GameConstants.SCREEN_HEIGHT - 150);

    // Thêm vào stage
    stage.addActor(scoreLabel);
    stage.addActor(coinsLabel);
    stage.addActor(levelLabel);
    stage.addActor(hintsLabel);
    stage.addActor(shufflesLabel);
    stage.addActor(undosLabel);
    stage.addActor(timeLabel);
    stage.addActor(timeline);
  }

  private ProgressBar.ProgressBarStyle newStyle() {
    ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();

    final float barHeight = 30;

    TextureRegion backgroundRegion = skinManager.getDrawable("bar_timeline_stroke").getRegion();
    TextureRegion knobBeforeRegion = skinManager.getDrawable("bar_timeline").getRegion();

    Drawable customBackground = new TextureRegionDrawable(backgroundRegion) {
      @Override
      public void draw(Batch batch, float x, float y, float width, float height) {
        // Vẽ background với chiều cao cố định, lặp texture nếu cần
        batch.draw(getRegion(), x-3, y-3, width+6, barHeight+6);
      }

      @Override
      public float getMinHeight() {
        return barHeight;
      }
    };


    Drawable customKnobBefore = new TextureRegionDrawable(knobBeforeRegion) {
      @Override
      public void draw(Batch batch, float x, float y, float width, float height) {
        // Vẽ knobBefore với chiều cao cố định, chỉ vẽ phần tiến độ
        batch.draw(getRegion(), x, y, width, barHeight);
      }

      @Override
      public float getMinHeight() {
        return barHeight;
      }
    };
    style.knobBefore = customKnobBefore;
    style.background = customBackground;
    return style;
  }


  // Cập nhật HUD
  public void update(float deltaTime) {
    // Cập nhật thời gian
    timeLeft -= deltaTime;
    if (timeLeft < 0) timeLeft = 0;

    // Cập nhật text cho các nhãn
    scoreLabel.setText("Score: " + player.getScore());
    coinsLabel.setText("Coins: " + player.getCoins());
    levelLabel.setText("Level: " + player.getLevel());
    hintsLabel.setText("Hints: " + player.getHints());
    shufflesLabel.setText("Shuffles: " + player.getShuffles());
    undosLabel.setText("Undos: " + player.getUndos());
    timeLabel.setText("Time: " + (int) timeLeft);

    // Cập nhật thanh thời gian
    if (maxTime > 0) {
      float timeProgress = (timeLeft / maxTime) * 100; // Phần trăm thời gian còn lại
      timeline.setValue(timeProgress);
    }

    // Cập nhật stage
    stage.act(deltaTime);
  }

  // Vẽ HUD
  public void render() {
    batch.begin();
    stage.draw();
    // Vẽ thêm biểu tượng nếu cần (ví dụ: icon cạnh số hints)
//    TextureAtlas.AtlasRegion hintIcon = skinManager.getButtonTexture("hint_button");
//    batch.draw(hintIcon, GameConstants.SCREEN_WIDTH - 180, GameConstants.SCREEN_HEIGHT - 30, 24, 24);
    batch.end();
  }

  // Kiểm tra hết thời gian
  public boolean isTimeUp() {
    return timeLeft <= 0;
  }

  // Reset thời gian (khi sang cấp mới)
  public void resetTime() {
    timeLeft = GameConstants.LEVEL_TIME_SECONDS;
  }

  // Giải phóng tài nguyên
  public void dispose() {
    batch.dispose();
    stage.dispose();
    font.dispose();
  }

  public void setTime(float time) {
    timeline.
    this.timeLeft = time;
  }

  // Getter cho Stage (nếu cần thêm nút vào HUD)
  public Stage getStage() {
    return stage;
  }

  public void setLevelLabel(int n) {
    levelLabel.setText("Level: " + n);
  }
}
