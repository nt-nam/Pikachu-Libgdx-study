package com.mygdx.game.utils;


public class GameConstants {
  // Kích thước màn hình
  public static final int SCREEN_WIDTH = 480;
  public static final int SCREEN_HEIGHT = 840;

  // Kích thước lưới mặc định
  public static final int DEFAULT_ROWS = 8;
  public static final int DEFAULT_COLS = 8;

  // Max level
  public static final int MAX_LEVEL = 100;

  // Kích thước ô (tile)
  public static final int TILE_SIZE = SCREEN_WIDTH / DEFAULT_COLS;
  public static final int TILE_MARGIN = 2;

  // Số loại hình thú (animal types)
  public static final int ANIMAL_TYPES = 6;

  // Buffer mặc định
  public static final int DEFAULT_HINTS = 3;     // Số lần gợi ý ban đầu
  public static final int DEFAULT_SHUFFLES = 4;  // Số lần xáo trộn ban đầu
  public static final int DEFAULT_UNDOS = 2;     // Số lần hoàn tác ban đầu

  // Giá trị phần thưởng và chi phí
  public static final int POINTS_PER_MATCH = 5; // Điểm cho mỗi lần nối thành công
  public static final int COIN_PER_LEVEL = 50;    // Số xu nhận được sau mỗi cấp
  public static final int HINT_COST = 20;         // Giá mua gợi ý (xu)
  public static final int SHUFFLE_COST = 30;      // Giá mua xáo trộn (xu)
  public static final int UNDO_COST = 25;         // Giá mua hoàn tác (xu)

  // Cài đặt âm thanh
  public static final float MUSIC_VOLUME_DEFAULT = 0.5f; // Âm lượng nhạc mặc định (0.0f - 1.0f)
  public static final float SOUND_VOLUME_DEFAULT = 0.8f; // Âm lượng hiệu ứng âm thanh mặc định

  // Thời gian
  public static final int LEVEL_TIME_SECONDS = 120; // Thời gian mỗi cấp (giây)

  // Đường dẫn tài nguyên (assets)
  public static final String DEFAULT_BACKGROUND_PATH = "images/bg.png";
  public static final String DEFAULT_ANIMAL_ATLAS_PATH = "atlas/game.atlas";
  public static final String MUSIC_PATH = "music/chill.mp3";
  public static final String MATCH_SOUND_PATH = "sound/bubble_switch.mp3";
  public static final String SHUFFLE_SOUND_PATH = "sound/bubble_switch.mp3";
  public static final String CLICK_SOUND_PATH = "sound/bubble_switch.mp3";
  
  public static final int DEFAULT_SKIN = 0;
  public static final String DEFAULT_ANIMAL = "textureAtlas/ani/";
  public static final String DEFAULT_UI = "textureAtlas/ui/";
  public static final String[] LIST_SKIN_ANIMAL = {"ani0.atlas","ani1.atlas","ani2.atlas","ani3.atlas","ani4.atlas"};
  public static final String[] LIST_SKIN_UI = {"ui0.atlas","ui1.atlas","ui2.atlas","ui3.atlas","ui4.atlas"};

  private GameConstants() {
  }

}
