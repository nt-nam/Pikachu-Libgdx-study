package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.PikachuGame;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.SkinManager;
import com.mygdx.game.utils.SoundManager;

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
        skinManager.getButtonTexture(buttonName)
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

  public ImageButton createButton3(String buttonName, final ClickListener clickListener) {
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

  // Tạo nút Hint
  public ImageButton createHintButton(final Player player, final Board board) {
    return createButton3("hint", new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (player.useHint()) {
          int[] hint = board.findHint();
          if (hint != null) {
            // TODO: Hiển thị gợi ý trên giao diện (ví dụ: làm sáng ô)
            Gdx.app.log("ButtonFactory", "Hint used: (" + hint[0] + "," + hint[1] + ") - (" + hint[2] + "," + hint[3] + ")");
          }
        }
      }
    });
  }

  // Tạo nút Shuffle
  public ImageButton createShuffleButton(final Player player, final Board board) {
    return createButton3("shuffle", new ClickListener() {
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