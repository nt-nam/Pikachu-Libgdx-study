package com.mygdx.game.utils;

import java.util.ArrayList;

public class GameConstants {
  // Kích thước màn hình
  public static final int SCREEN_WIDTH = 480;    // Chiều rộng màn hình (pixels)
  public static final int SCREEN_HEIGHT = 840;   // Chiều cao màn hình (pixels)

  // Kích thước lưới mặc định
  public static final int DEFAULT_ROWS = 6;      // Số hàng mặc định
  public static final int DEFAULT_COLS = 5;     // Số cột mặc định

  // Kích thước ô (tile)
  public static final int TILE_SIZE = SCREEN_WIDTH / DEFAULT_COLS;        // Kích thước mỗi ô (64x64 pixels)
  public static final int TILE_MARGIN = 2;       // Khoảng cách giữa các ô (pixels)

  // Số loại hình thú (animal types)
  public static final int ANIMAL_TYPES = 2;     // Số lượng hình thú khác nhau (Pikachu, Charmander, v.v.)

  // Buffer mặc định
  public static final int DEFAULT_HINTS = 3;     // Số lần gợi ý ban đầu
  public static final int DEFAULT_SHUFFLES = 2;  // Số lần xáo trộn ban đầu
  public static final int DEFAULT_UNDOS = 2;     // Số lần hoàn tác ban đầu

  // Giá trị phần thưởng và chi phí
  public static final int POINTS_PER_MATCH = 100; // Điểm cho mỗi lần nối thành công
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
  public static final String BACKGROUND_PATH = "images/background.png";
  public static final String TILE_ATLAS_PATH = "atlas/game.atlas";
  public static final String MUSIC_PATH = "audio/music/background_music.ogg";
  public static final String MATCH_SOUND_PATH = "audio/sfx/match.ogg";
  public static final String SHUFFLE_SOUND_PATH = "audio/sfx/shuffle.ogg";
  public static final String CLICK_SOUND_PATH = "audio/sfx/click.ogg";
  
  // Skin
  public static final int DEFAULT_SKIN = 0;
  public static final String[] LIST_SKIN_ANIMAL = {"ani1.atlas","ani2.atlas","ani3.atlas","ani4.atlas"};
  public static final String[] LIST_SKIN_UI = {"ui1.atlas","ui2.atlas","ui3.atlas","ui4.atlas"};


  // ngăn việc  khởi tạo constructor
  private GameConstants() {
  }
}
