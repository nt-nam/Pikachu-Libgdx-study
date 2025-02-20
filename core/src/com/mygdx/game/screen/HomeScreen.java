package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class HomeScreen implements Screen {
    private Game game;
    int maxLevel = 100;
    private Stage stage;
    private AssetManager assetManager;
    private Image btnProfile, blockLevel, btnStartLevel, btnSetting;
    private float centerX, centerY;
    private BitmapFont bitmapFont;
    private Label labelLevel;
    private boolean isdraw;

    public HomeScreen(Game game, AssetManager assetManager, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.assetManager = assetManager;
        centerX = stage.getWidth() / 2;
        centerY = stage.getHeight() / 2;
        createAssetHome();
        isdraw = true;
    }

    private void createAssetHome() {
        TextureAtlas ui = assetManager.get("textureAtlas/ui.atlas");
        bitmapFont = assetManager.get("font/arial_uni_30.fnt");
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;

        btnProfile = new Image(new TextureRegion(ui.findRegion("btn_blue")));
        btnProfile.setBounds(0, centerY * 2 - 100, 100, 100);
        Label label = new Label("Profile", style);
        label.setBounds(10, centerY * 2 - 100, 100, 100);
        stage.addActor(btnProfile);
        stage.addActor(label);

//        Group levelGroup = new Group();
        Table table = new Table();

        for (int n = 1; n < maxLevel; n++) {
            int i = 0, j = 0;
            final int nlevel = n;

            switch (n % 6) {
                case 0:
                    i = 2;
                    j = 0;
                    break;
                case 1:
                    i = 1;
                    j = 0;
                    break;
                case 2:
                    i = 0;
                    j = 1;
                    break;
                case 3:
                    i = 1;
                    j = 2;
                    break;
                case 4:
                    i = 2;
                    j = 2;
                    break;
                case 5:
                    i = 3;
                    j = 3;
                    break;
                default:
            }

            int x = i;
            int y = (n / 6) * 4 + j;

            Group level = new Group();
            level.setSize(stage.getWidth(), 100);
            blockLevel = new Image(new TextureRegion(ui.findRegion("btn_green")));
            blockLevel.setX(x * 110 + centerX - 220);
//            blockLevel.setWidth(100);
            labelLevel = new Label("" + n, style);
            labelLevel.setX(blockLevel.getX());
            labelLevel.setFontScale(2f);
            labelLevel.setAlignment(Align.center);
            level.addActor(blockLevel);
            level.addActor(labelLevel);
            level.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new PlayScreen(game, HomeScreen.this, stage.getViewport(), nlevel));
                }
            });
            table.add(level);
            table.row();
        }
//        table.setFillParent(true);
//        table.debug();
        ScrollPane scrollPane = new ScrollPane(table);
//        scrollPane.setDebug(true, true);
//        scrollPane.setFillParent(true);
//        scrollPane.debug();

        scrollPane.setSize(stage.getWidth(), stage.getHeight() - 100);


        scrollPane.setScrollingDisabled(true, false);
        stage.addActor(scrollPane);


//        btnStartLevel = new Image(new TextureRegion(ui.findRegion("btn_green")));
//        btnSetting = new Image(new TextureRegion(ui.findRegion("btn_green")));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (isdraw) stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
