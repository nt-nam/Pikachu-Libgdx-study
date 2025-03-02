package com.mygdx.game.defaults.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.defaults.PikachuGameA;

public class SettingScreen implements Screen {
    PikachuGameA game;
    private AssetManager assetManager;
    private Stage stage;
    private UiPopup winPopup;
    Image starEmpty0, starEmpty1, starEmpty2, board, boardCoinMainMenu, btnBack, btnReplay, btnResume, coin, heart, popup, ribbonBlue, ribbonFailed;


    public SettingScreen(PikachuGameA game, AssetManager assetManager, Stage stage) {
        this.assetManager = assetManager;
        this.stage = stage;

    }

    float startX, startY;

    @Override
    public void show() {
        winPopup = new UiPopup(game, assetManager, false);


        stage.addActor(winPopup);

    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));  // Cập nhật hành động của stage
        stage.draw();

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

    public UiPopup getWinPopup() {
        return winPopup;
    }

    public void setWinPopup(UiPopup winPopup) {
        this.winPopup = winPopup;
    }
}
