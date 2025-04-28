//package com.mygame.pikachu.view;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Pixmap;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.Align;
//import com.mygame.pikachu.GMain;
//import com.mygame.pikachu.data.GAssetsManager;
//import com.mygame.pikachu.model.Player;
//import com.mygame.pikachu.utils.GConstants;
//import com.mygame.pikachu.utils.hud.AL;
//import com.mygame.pikachu.utils.hud.MapGroup;
//import com.mygame.pikachu.utils.hud.builders.BB;
//import com.mygame.pikachu.utils.hud.builders.MGB;
//
//public class HUD {
//  private GMain game;
//  private MapGroup playMG;
////  private SpriteBatch batch;
//  private Player player;
////  private Board board;
////  private BitmapFont font;
////  private Label scoreLabel;
////  private Label coinsLabel;
////  private Label levelLabel;
////  private Label timeLabel;
//  private ProgressBar timeline;
//  private float timeLeft;
//  private float maxTime;
//  private final float centerW;
//  private final float centerH;
//  private boolean stop;
//
//  public HUD(GMain game, MapGroup playMG, Board board) {
//    this.game = game;
//    this.player = game.getPlayer();
//    this.board = board;
//    this.batch = new SpriteBatch();
//    this.playMG = playMG;
//    this.font = new BitmapFont(Gdx.files.internal("font/arial_uni_30.fnt"));
//    this.timeLeft = GConstants.LEVEL_TIME_SECONDS;
//    this.maxTime = GConstants.LEVEL_TIME_SECONDS;
//    this.centerW = playMG.getWidth() * 0.5f;
//    this.centerH = playMG.getHeight() * 0.5f;
//
//    createButton();
//    createLabel();
//
//    playMG.addActor(scoreLabel);
//    playMG.addActor(coinsLabel);
//    playMG.addActor(timeLabel);
//    playMG.addActor(timeline);
//  }
//
//  private void createLabel() {
//    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
//
//    scoreLabel = new Label("Score: " + player.getScore(), labelStyle);
//    scoreLabel.setPosition(10, centerH * 2 - 30);
//
//    coinsLabel = new Label("Coins: " + player.getCoins(), labelStyle);
//    coinsLabel.setPosition(10, centerH * 2 - 60);
//
//    timeLabel = new Label("Time: " + (int) timeLeft, labelStyle);
//    timeLabel.setPosition(scoreLabel.getWidth() + 50, centerH * 2 - 30);
//    timeLabel.setAlignment(Align.left);
//
//    levelLabel = new Label("Level: " + player.getLevel(), labelStyle);
//    levelLabel.setPosition(centerW - 40, centerH * 2 - 30);
//    levelLabel.setAlignment(Align.center);
//
//    ProgressBar.ProgressBarStyle style = newStyle();
//    timeline = new ProgressBar(0, 100, 1, false, style);
//    timeline.setSize(playMG.getWidth() * 0.8f, 30);
//    timeline.setPosition(playMG.getWidth() * 0.1f, centerH * 2 - 100);
//  }
//
//  private void createButton() {
//    GAssetsManager.setTextureAtlas(GConstants.DEFAULT_ATLAS_NEWPIKA);
//
//    MapGroup hint = MGB.New().size(100,100).childs(
//        BB.New().bg("hint").transform(true).pos(0,0,AL.c).scale(0.5f)/*,
//        BB.New().bg("hint").transform(true).label(player.getHints() + "", GConstants.BMF, 0, 0, AL.c).pos(-10,-10,AL.br).scale(0.5f)*/
//    ).pos(100, 100,AL.bl).parent(playMG).build();
//    hint.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        if (player.useHint()) {
//          board.showAnimationHint();
//        }
//      }
//    });
//
//
//    MapGroup MGShuffle = MGB.New().size(100,100).childs(
//        BB.New().bg("shuffle").transform(true).pos(0, 0,AL.c).scale(0.5f)/*,
//        BB.New().bg("frm_count").label(player.getHints() + "", GConstants.BMF, 0, 0, AL.c).pos(-10,-10,AL.br)*/
//    ).pos(0, 100,AL.cb).parent(playMG).build();
//    MGShuffle.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        if (player.useShuffle()) {
//          board.shuffle(); // Gọi hàm shuffle() để xáo trộn bảng
//          Gdx.app.log("ButtonFactory", "Board shuffled");
//        }
//      }
//    });
//
//    MapGroup rocket = MGB.New().size(100,100).childs(
//        BB.New().bg("boom").transform(true).pos(0,0,AL.c).scale(0.5f)
//    ).pos(100, 100,AL.br).parent(playMG).build();
//
//
//    TextButton.TextButtonStyle buttonStyle = createSimpleButtonStyle();
//    TextButton levelbtn = new TextButton("Level " + player.getLevel(), buttonStyle);
//    levelbtn.setPosition(playMG.getWidth() / 2 - 150, playMG.getHeight() - 50);
//    levelbtn.setSize(300, 60);
//    levelbtn.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        //TODO
//      }
//    });
//    playMG.addActor(levelbtn);
//
//    TextButton btnTop = new TextButton("TOP", buttonStyle);
//    btnTop.setSize(120, 60);
//    btnTop.setPosition(playMG.getWidth() - btnTop.getWidth() - 10, playMG.getHeight() / 2 + 10 + btnTop.getHeight());
//    btnTop.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        board.moveTop();
//        System.out.println("click Move TOP");
//      }
//    });
////    playMG.addActor(btnTop);
//
//    TextButton btnBottom = new TextButton("Bottom", buttonStyle);
//    btnBottom.setSize(120, 60);
//    btnBottom.setPosition(playMG.getWidth() - btnBottom.getWidth() - 10, playMG.getHeight() / 2);
//    btnBottom.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        board.moveBottom();
//        System.out.println("click Move Bottom");
//      }
//    });
////    playMG.addActor(btnBottom);
//
//    TextButton btnRight = new TextButton("Right", buttonStyle);
//    btnRight.setSize(120, 60);
//    btnRight.setPosition(playMG.getWidth() - 10 - btnRight.getWidth(), playMG.getHeight() / 2 - btnRight.getHeight() - 10);
//    btnRight.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        board.moveRight();
//        System.out.println("click Move Right");
//      }
//    });
////    playMG.addActor(btnRight);
//
//    TextButton btnLeft = new TextButton("Left", buttonStyle);
//    btnLeft.setSize(120, 60);
//    btnLeft.setPosition(playMG.getWidth() - btnLeft.getWidth() - 10, playMG.getHeight() / 2 - 2 * btnLeft.getHeight() - 20);
//    btnLeft.addListener(new ClickListener() {
//      @Override
//      public void clicked(InputEvent event, float x, float y) {
//        board.moveLeft();
//        System.out.println("click Move Left");
//      }
//    });
////    playMG.addActor(btnLeft);
//
//  }
//
//
//  private ProgressBar.ProgressBarStyle newStyle() {
//    ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
//    final float barHeight = 30;
//
//    TextureRegion backgroundRegion = GMain.getAssetHelper().getTextureRegion(GConstants.DEFAULT_ATLAS_PLAY, "barframe");
//    TextureRegion knobBeforeRegion = GMain.getAssetHelper().getTextureRegion(GConstants.DEFAULT_ATLAS_PLAY, "bar");
//
//    Drawable customBackground = new TextureRegionDrawable(backgroundRegion) {
//      @Override
//      public void draw(Batch batch, float x, float y, float width, float height) {
//        batch.draw(getRegion(), x - 3, y - 3, width + 6, barHeight + 6);
//      }
//
//      @Override
//      public float getMinHeight() {
//        return barHeight;
//      }
//    };
//
//    Drawable customKnobBefore = new TextureRegionDrawable(knobBeforeRegion) {
//      @Override
//      public void draw(Batch batch, float x, float y, float width, float height) {
//        batch.draw(getRegion(), x, y, width, barHeight);
//      }
//
//      @Override
//      public float getMinHeight() {
//        return barHeight;
//      }
//    };
//    style.knobBefore = customKnobBefore;
//    style.background = customBackground;
//    return style;
//  }
//
//  public void update(float deltaTime) {
//    if (stop) return;
//    timeLeft -= deltaTime;
//    if (timeLeft < 0) timeLeft = 0;
//
//    scoreLabel.setText("Score: " + player.getScore());
//    coinsLabel.setText("Coins: " + player.getCoins());
//    levelLabel.setText("Level: " + player.getLevel());
//    timeLabel.setText("Time: " + (int) timeLeft);
//
//    if (maxTime > 0) {
//      float timeProgress = (timeLeft / maxTime) * 100;
//      timeline.setValue(timeProgress);
//    }
//
////    playMG.act(deltaTime);
//  }
//
//
//
//  public boolean isTimeUp() {
//    return timeLeft <= 0;
//  }
//
//  public void resetTime() {
//    timeLeft = GConstants.LEVEL_TIME_SECONDS;
//  }
//
//  public void dispose() {
//    batch.dispose();
//    font.dispose();
//  }
//
//  public void setTime(float time) {
//    this.timeLeft = time;
//    this.maxTime = time;
//  }
//
//
//  public void setLevelLabel(int n) {
//    levelLabel.setText("Level: " + n);
//  }
//
//  public float getTimeLeft() {
//    return timeLeft;
//  }
//
//  private TextButton.TextButtonStyle createSimpleButtonStyle() {
//    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//    pixmap.setColor(Color.DARK_GRAY);
//    pixmap.fill();
//    Texture upTexture = new Texture(pixmap);
//
//    pixmap.setColor(Color.LIGHT_GRAY);
//    pixmap.fill();
//    Texture downTexture = new Texture(pixmap);
//    pixmap.dispose();
//
//    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
//    style.up = new TextureRegionDrawable(upTexture);
//    style.down = new TextureRegionDrawable(downTexture);
//    style.font = GMain.getAssetHelper().getBitmapFont("font/arial_uni_30.fnt");
//    style.fontColor = Color.WHITE;
//    return style;
//  }
//
//  public boolean setStop(boolean state) {
//    stop = state;
//    return stop;
//  }
//}