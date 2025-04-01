package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.Board;

public class ButtonFactory {
  private SkinManager skinManager;    // Quản lý texture của skin
  private SoundManager soundManager;  // Quản lý âm thanh

  // Constructor
  public ButtonFactory(SkinManager skinManager, SoundManager soundManager) {
    this.skinManager = skinManager;
    this.soundManager = soundManager;
  }

  public ImageButton createButton(String buttonName, final Runnable action) {
    TextureRegionDrawable drawable = new TextureRegionDrawable(
        skinManager.getButtonTextureUIAtlas(buttonName)
    );

    ImageButton button = new ImageButton(drawable);

    if (action != null) {
      button.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          soundManager.playSound("click");
          action.run();
        }
      });
    }

    return button;
  }

  public ImageButton createButton2(String buttonName, final Runnable action) {
    // Tạo Texture từ đường dẫn (ví dụ: "buttons/hint_button.png")
    Texture texture = new Texture(Gdx.files.internal("images/btn/" + buttonName + ".png"));
    TextureRegionDrawable drawable = new TextureRegionDrawable(texture);

    ImageButton button = new ImageButton(drawable);

    if (action != null) {
      button.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          soundManager.playSound("click");
          action.run();
        }
      });
    }

    return button;
  }

  public ImageButton createButtonBtn(String buttonName, final ClickListener clickListener) {
    // Tạo Texture từ đường dẫn (ví dụ: "buttons/hint_button.png")
    Texture texture = new Texture(Gdx.files.internal("images/btn/" + buttonName + ".png"));
    TextureRegionDrawable drawable = new TextureRegionDrawable(texture);

    ImageButton button = new ImageButton(drawable);

    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        soundManager.playSound("click");
        clickListener.clicked(event, x, y);
      }
    });
    return button;
  }

  public ImageButton createButtonWood(String buttonName, ClickListener clickListener) {
    // Tạo Texture từ đường dẫn (ví dụ: "buttons/hint_button.png")
    Texture texture = new Texture(Gdx.files.internal("images/ui/" + buttonName + ".png"));
    TextureRegionDrawable drawable = new TextureRegionDrawable(texture);

    ImageButton button = new ImageButton(drawable);
    button.setSize(texture.getWidth(), texture.getHeight());
    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        soundManager.playSound("click");
        clickListener.clicked(event, x, y);
      }
    });
    return button;
  }

  public Button createButtonTick(ClickListener clickListener) {
    String nameNon = "check_square_grey";
    String nameCheck = "check_square_grey_checkmark";

    Texture textureUp = new Texture(Gdx.files.internal("images/ui/" + nameNon + ".png"));
    Texture textureCheck = new Texture(Gdx.files.internal("images/ui/" + nameCheck + ".png"));

    TextureRegionDrawable drawable = new TextureRegionDrawable(textureUp);
    TextureRegionDrawable drawable2 = new TextureRegionDrawable(textureCheck);

    Button.ButtonStyle style = new Button.ButtonStyle();
    style.checked = drawable2;
    style.up = drawable;

    Button button = new Button(style);
    button.setSize(textureUp.getWidth(), textureUp.getHeight());

    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        soundManager.playSound("click");
        clickListener.clicked(event, x, y);
      }
    });
    return button;
  }

  // Tạo nút Hint
  public ImageButton createHintButton(final Player player, final Board board) {
    return createButtonBtn("hint", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (player.useHint()) {
          board.showAnimationHint();
        }
      }
    });
  }

  // Tạo nút Shuffle
  public ImageButton createShuffleButton(final Player player, final Board board) {
    return createButtonBtn("shuffle", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (player.useShuffle()) {
          board.shuffle(); // Gọi hàm shuffle() để xáo trộn bảng
          Gdx.app.log("ButtonFactory", "Board shuffled");
        }
      }
    });
  }

  // Tạo nút Undo
  public ImageButton createUndoButton(final Player player, Board board) {
    return createButton2("next", new Runnable() {
      @Override
      public void run() {
        if (player.useUndo()) {
          // TODO: Thực hiện hoàn tác (cần lưu trạng thái trước đó trong Board hoặc GameScreen)
          Gdx.app.log("ButtonFactory", "Undo action performed");
        }
      }
    });
  }

  public ImageButton createCloseButton(final Player player, final PikachuGame game) {
    return createButton2("close", new Runnable() {
      @Override
      public void run() {
        game.setScreen(game.getHomeScreen());
      }
    });
  }

  public ImageButton createProfileButton(final Player player) {
    return createButton2("circle", new Runnable() {
      @Override
      public void run() {

      }
    });
  }

  public ImageButton createSettingsButton(Runnable switchToSettingsScreen) {
    return createButton("settings_button", switchToSettingsScreen);
  }

  // Tạo nút Toggle Music
  public ImageButton createMusicToggleButton(final SoundManager soundManager) {
    return createButton("music_button", new Runnable() {
      @Override
      public void run() {
        soundManager.setMusicMuted(!soundManager.isMusicMuted());
        Gdx.app.log("ButtonFactory", "Music " + (soundManager.isMusicMuted() ? "muted" : "unmuted"));
      }
    });
  }

  // Tạo nút Toggle Sound
  public ImageButton createSoundToggleButton(final SoundManager soundManager) {
    return createButton("sound_button", new Runnable() {
      @Override
      public void run() {
        soundManager.setSoundMuted(!soundManager.isSoundMuted());
        Gdx.app.log("ButtonFactory", "Sound " + (soundManager.isSoundMuted() ? "muted" : "unmuted"));
      }
    });
  }
}