package com.mygdx.game.view;

import static com.mygdx.game.utils.GameConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.ButtonFactory;
import com.mygdx.game.utils.GameConstants;
import com.mygdx.game.utils.SkinManager;

public class HUD {
  private PikachuGame game;
  private Stage stage;
  private SpriteBatch batch;
  private Player player;
  private SkinManager skinManager;
  private ButtonFactory buttonFactory; // Để tạo nút
  private Board board;                 // Tham chiếu đến Board
  private BitmapFont font;
  private Label scoreLabel;
  private Label coinsLabel;
  private Label levelLabel;
  private Label hintsLabel;
  private Label shufflesLabel;
  private Label undosLabel;
  private Label timeLabel;
  private ProgressBar timeline;
  private float timeLeft;
  private float maxTime;
  private ImageButton settingButton;
  private ImageButton hintButton;
  private ImageButton shuffleButton;
  private ImageButton undoButton;
  private float centerW;
  private float centerH;
  private boolean stop;

  public HUD(PikachuGame game,Stage stagePlayScreen, Board board) {
    this.game = game;
    this.player = game.getPlayer();
    this.skinManager = game.getSkinManager();
    this.buttonFactory = game.getHomeScreen().getButtonFactory();
    this.board = board;
    this.batch = new SpriteBatch();
    this.stage = stagePlayScreen;
    this.font = new BitmapFont(Gdx.files.internal("font/arial_uni_30.fnt"));
    this.timeLeft = GameConstants.LEVEL_TIME_SECONDS;
    this.maxTime = GameConstants.LEVEL_TIME_SECONDS;
    this.centerW = stage.getWidth()*0.5f;
    this.centerH = stage.getHeight()*0.5f;

    createButton();
    createLabel();

    stage.addActor(scoreLabel);
    stage.addActor(coinsLabel);
    stage.addActor(hintsLabel);
    stage.addActor(shufflesLabel);
    stage.addActor(undosLabel);
    stage.addActor(timeLabel);
    stage.addActor(timeline);

  }

  private void createLabel() {
    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

    // Bên trái: Score, Coins, Time
    scoreLabel = new Label("Score: " + player.getScore(), labelStyle);
    scoreLabel.setPosition(10, centerH*2 - 30);

    coinsLabel = new Label("Coins: " + player.getCoins(), labelStyle);
    coinsLabel.setPosition(10, centerH*2 - 60);

    timeLabel = new Label("Time: " + (int) timeLeft, labelStyle);
    timeLabel.setPosition(10, centerH*2 - 90);
    timeLabel.setAlignment(Align.left);

    // Giữa: Level và Timeline
    levelLabel = new Label("Level: " + player.getLevel(), labelStyle);
    levelLabel.setPosition(centerW - 40, centerH*2 - 30);
    levelLabel.setAlignment(Align.center);

    ProgressBar.ProgressBarStyle style = newStyle();
    timeline = new ProgressBar(0, 100, 1, false, style);
    timeline.setSize(stage.getWidth() * 0.8f, 30);
    timeline.setPosition(stage.getWidth() * 0.1f, centerH * 2 - 160);

    // Bên phải: Hints, Shuffles, Undos + Nút
    hintsLabel = new Label(player.getHints() + " ", labelStyle);
    hintsLabel.setPosition(hintButton.getX() , hintButton.getY()-10);
    hintsLabel.debug();

    shufflesLabel = new Label(player.getShuffles()+"", labelStyle);
    shufflesLabel.setPosition(shuffleButton.getX() , shuffleButton.getY()-10);

    undosLabel = new Label("Undos: " + player.getUndos(), labelStyle);
    undosLabel.setPosition(undoButton.getX(), undoButton.getY()-10);
  }

  private void createButton() {
    // Tạo nút
    settingButton = buttonFactory.createButtonWood("btn_setting", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {

      }
    });
    settingButton.setPosition(centerW*2 - settingButton.getWidth(), centerH*2 - settingButton.getHeight());

    hintButton = buttonFactory.createHintButton(player, board);
    hintButton.setSize(hintButton.getWidth(), hintButton.getHeight());
    hintButton.setPosition(centerW - hintButton.getWidth() * 2, hintButton.getHeight() * 0.3f);
    stage.addActor(hintButton);

    shuffleButton = buttonFactory.createShuffleButton(player, board);
    shuffleButton.setSize(hintButton.getWidth(), hintButton.getHeight());
    shuffleButton.setPosition(centerW - shuffleButton.getWidth() * 0.5f, shuffleButton.getHeight() * 0.3f);
    stage.addActor(shuffleButton);

    undoButton = buttonFactory.createUndoButton(player, board);
    undoButton.setSize(hintButton.getWidth(), hintButton.getHeight());
    undoButton.setPosition(centerW + undoButton.getWidth(), undoButton.getHeight() * 0.3f);
    stage.addActor(undoButton);

    TextButton.TextButtonStyle buttonStyle = createSimpleButtonStyle();
    TextButton levelbtn = new TextButton("Level "+player.getLevel(), buttonStyle);
    levelbtn.setPosition(stage.getWidth() / 2 - 150, stage.getHeight()- 50);
    levelbtn.setSize(300, 60);
    levelbtn.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        //TODO
      }
    });
    stage.addActor(levelbtn);

    TextButton btnTop = new TextButton("TOP", buttonStyle);
    btnTop.setSize(120, 60);
    btnTop.setPosition(stage.getWidth() -btnTop.getWidth()-10, stage.getHeight()/2+ 10+btnTop.getHeight());
    btnTop.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        board.moveTop();
        System.out.println("click Move TOP");
      }
    });
    stage.addActor(btnTop);

    TextButton btnBottom = new TextButton("Bottom", buttonStyle);
    btnBottom.setSize(120, 60);
    btnBottom.setPosition(stage.getWidth() -btnBottom.getWidth()-10, stage.getHeight()/2 );
    btnBottom.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        board.moveBottom();
        System.out.println("click Move Bottom");
      }
    });
    stage.addActor(btnBottom);

    TextButton btnRight = new TextButton("Right", buttonStyle);
    btnRight.setSize(120, 60);
    btnRight.setPosition(stage.getWidth() -10 -btnRight.getWidth(), stage.getHeight()/2-btnRight.getHeight() -10);
    btnRight.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        board.moveRight();
        System.out.println("click Move Right");
      }
    });
    stage.addActor(btnRight);

    TextButton btnLeft = new TextButton("Left", buttonStyle);
    btnLeft.setSize(120, 60);
    btnLeft.setPosition(stage.getWidth() -btnLeft.getWidth() -10, stage.getHeight()/2-2*btnLeft.getHeight()-20);
    btnLeft.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        board.moveLeft();
        System.out.println("click Move Left");
      }
    });
    stage.addActor(btnLeft);

  }


  private ProgressBar.ProgressBarStyle newStyle() {
    ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
    final float barHeight = 30;

    TextureRegion backgroundRegion = skinManager.getDrawable("bar_timeline_stroke").getRegion();
    TextureRegion knobBeforeRegion = skinManager.getDrawable("bar_timeline").getRegion();

    Drawable customBackground = new TextureRegionDrawable(backgroundRegion) {
      @Override
      public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(getRegion(), x - 3, y - 3, width + 6, barHeight + 6);
      }

      @Override
      public float getMinHeight() {
        return barHeight;
      }
    };

    Drawable customKnobBefore = new TextureRegionDrawable(knobBeforeRegion) {
      @Override
      public void draw(Batch batch, float x, float y, float width, float height) {
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

  public void update(float deltaTime) {
    timeLeft -= deltaTime;
    if (timeLeft < 0) timeLeft = 0;

    scoreLabel.setText("Score: " + player.getScore());
    coinsLabel.setText("Coins: " + player.getCoins());
    levelLabel.setText("Level: " + player.getLevel());
    hintsLabel.setText("Hints: " + player.getHints());
    shufflesLabel.setText("Shuffles: " + player.getShuffles());
    undosLabel.setText("Undos: " + player.getUndos());
    timeLabel.setText("Time: " + (int) timeLeft);

    if (maxTime > 0) {
      float timeProgress = (timeLeft / maxTime) * 100;
      timeline.setValue(timeProgress);
    }

//    stage.act(deltaTime);
  }

  public void render() {
    batch.begin();
    stage.draw();
    batch.end();
  }

  public boolean isTimeUp() {
    return timeLeft <= 0;
  }

  public void resetTime() {
    timeLeft = GameConstants.LEVEL_TIME_SECONDS;
  }

  public void dispose() {
    batch.dispose();
    stage.dispose();
    font.dispose();
  }

  public void setTime(float time) {
    this.timeLeft = time;
    this.maxTime = time;
  }

  public Stage getStage() {
    return stage;
  }

  public void setLevelLabel(int n) {
    levelLabel.setText("Level: " + n);
  }

  public float getTimeLeft() {
    return timeLeft;
  }
  private TextButton.TextButtonStyle createSimpleButtonStyle() {
    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.DARK_GRAY);
    pixmap.fill();
    Texture upTexture = new Texture(pixmap);

    pixmap.setColor(Color.LIGHT_GRAY);
    pixmap.fill();
    Texture downTexture = new Texture(pixmap);
    pixmap.dispose();

    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    style.up = new TextureRegionDrawable(upTexture);
    style.down = new TextureRegionDrawable(downTexture);
    style.font = game.getAssetHelper().get("font/arial_uni_30.fnt", BitmapFont.class);
    style.fontColor = Color.WHITE;
    return style;
  }
  public boolean isStop(){
    stop =!stop;
    return !stop;
  }
}