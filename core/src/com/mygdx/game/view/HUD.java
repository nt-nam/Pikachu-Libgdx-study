package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
  private float timeLeft;             // Thời gian còn lại (giây)

  // Constructor
  public HUD(Player player, SkinManager skinManager) {
    this.player = player;
    this.skinManager = skinManager;
    this.batch = new SpriteBatch();
    this.stage = new Stage();
    this.font = new BitmapFont(); // Font mặc định, có thể thay bằng font tùy chỉnh
    this.timeLeft = GameConstants.LEVEL_TIME_SECONDS;

    // Thiết lập style cho Label
    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

    // Tạo các nhãn
    scoreLabel = new Label("Score: " + player.getScore(), labelStyle);
    scoreLabel.setPosition(10, GameConstants.SCREEN_HEIGHT - 30);

    coinsLabel = new Label("Coins: " + player.getCoins(), labelStyle);
    coinsLabel.setPosition(10, GameConstants.SCREEN_HEIGHT - 60);

    levelLabel = new Label("Level: " + player.getLevel(), labelStyle);
    levelLabel.setPosition(10, GameConstants.SCREEN_HEIGHT - 90);

    hintsLabel = new Label("Hints: " + player.getHints(), labelStyle);
    hintsLabel.setPosition(GameConstants.SCREEN_WIDTH - 150, GameConstants.SCREEN_HEIGHT - 30);

    shufflesLabel = new Label("Shuffles: " + player.getShuffles(), labelStyle);
    shufflesLabel.setPosition(GameConstants.SCREEN_WIDTH - 150, GameConstants.SCREEN_HEIGHT - 60);

    undosLabel = new Label("Undos: " + player.getUndos(), labelStyle);
    undosLabel.setPosition(GameConstants.SCREEN_WIDTH - 150, GameConstants.SCREEN_HEIGHT - 90);

    timeLabel = new Label("Time: " + (int) timeLeft, labelStyle);
    timeLabel.setPosition(GameConstants.SCREEN_WIDTH / 2 - 50, GameConstants.SCREEN_HEIGHT - 30);
    timeLabel.setAlignment(Align.center);

    // Thêm vào stage
    stage.addActor(scoreLabel);
    stage.addActor(coinsLabel);
    stage.addActor(levelLabel);
    stage.addActor(hintsLabel);
    stage.addActor(shufflesLabel);
    stage.addActor(undosLabel);
    stage.addActor(timeLabel);
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

    // Cập nhật stage
    stage.act(deltaTime);
  }

  // Vẽ HUD
  public void render() {
    batch.begin();
    stage.draw();
    // Vẽ thêm biểu tượng nếu cần (ví dụ: icon cạnh số hints)
    TextureAtlas.AtlasRegion hintIcon = skinManager.getButtonTexture("hint_button");
    batch.draw(hintIcon, GameConstants.SCREEN_WIDTH - 180, GameConstants.SCREEN_HEIGHT - 30, 24, 24);
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

  // Getter cho Stage (nếu cần thêm nút vào HUD)
  public Stage getStage() {
    return stage;
  }
}
