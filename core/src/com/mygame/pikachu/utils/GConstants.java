package com.mygame.pikachu.utils;


public class GConstants {
  // Kích thước màn hình
  public static final int SCREEN_WIDTH = 480;
  public static final int SCREEN_HEIGHT = 840;

  // Kích thước lưới mặc định
  public static final int DEFAULT_ROWS = 8;
  public static final int DEFAULT_COLS = 8;

  // Max level
  public static final int MAX_LEVEL = 100;

  // Kích thước ô (tile)
  public static final int TILE_SIZE = Math.min(Math.max(SCREEN_WIDTH / DEFAULT_COLS, SCREEN_HEIGHT / DEFAULT_COLS),96);
  public static final int TILE_MARGIN = 2;

  // Số loại hình thú (animal types)
  public static final int ANIMAL_TYPES = 6;

  // Buffer mặc định
  public static final int DEF_HINTS                  = 3;
  public static final int DEF_SHUFFLES               = 4;
  public static final int DEF_UNDOS                  = 3;
  public static final int DEF_ROCKETS                = 3;

  // Giá trị phần thưởng và chi phí
  // Thông số chung của level
  public static final int SCORE_PER_PAIR             = 1;   // Điểm cho mỗi ngoi sao
  public static final int TIME_BONUS_SCORE_STAR      = 5;   // Điểm thưởng dựa trên thời gian còn lại
  public static final int COINS_PER_MATCH            = 2;   // Điểm cho mỗi lần nối thành công
  public static final int COIN_PER_LEVEL             = 50;  // Số xu nhận được sau mỗi cấp
  public static final int HINT_COST                  = 20;  // Giá mua gợi ý (xu)
  public static final int SHUFFLE_COST               = 30;  // Giá mua xáo trộn (xu)
  public static final int UNDO_COST                  = 25;  // Giá mua hoàn tác (xu)

  // Cài đặt âm thanh
  public static final float MUSIC_VOLUME_DEFAULT = 0.5f; // Âm lượng nhạc mặc định (0.0f - 1.0f)
  public static final float SOUND_VOLUME_DEFAULT = 0.8f; // Âm lượng hiệu ứng âm thanh mặc định


  // Đường dẫn tài nguyên (assets)
  public static final String DEFAULT_CXL                          = "cucxilau1";
  public static final String DEFAULT_BACKGROUND_PATH              = "images/bg.png";
  public static final String DEFAULT_ANIMAL_ATLAS_PATH            = "atlas/game.atlas";
  public static final String MUSIC_PATH                           = "music/chill.mp3";
  public static final String MATCH_SOUND_PATH                     = "sound/bubble_switch.mp3";
  public static final String SHUFFLE_SOUND_PATH                   = "sound/bubble_switch.mp3";
  public static final String CLICK_SOUND_PATH                     = "sound/bubble_switch.mp3";
  public static final String DEFAULT_ATLAS_ANIMAL                 = "textureAtlas/ani/ani0.atlas";
  public static final String DEFAULT_ATLAS_UI                     = "textureAtlas/ui/ui0.atlas";
  public static final String DEFAULT_ATLAS_UI_WOOD                = "textureAtlas/ui/UiWood.atlas";
  public static final String DEFAULT_ATLAS_BTN                    = "textureAtlas/ui/btn_pikachu.atlas";
  public static final String DEFAULT_ATLAS_LEADER_BOARD           = "atlas/leaderboard.atlas";
  public static final String DEFAULT_ATLAS_ANIMALS                = "atlas/animals.atlas";
  public static final String DEFAULT_ATLAS_COMMON                 = "atlas/common.atlas";
  public static final String DEFAULT_ATLAS_BG                     = "atlas/bg.atlas";
  public static final String DEFAULT_ATLAS_MENU                   = "atlas/menu.atlas";
  public static final String DEFAULT_ATLAS_CROSS                  = "atlas/crossPanel.atlas";
  public static final String DEFAULT_ATLAS_PARTICLE               = "atlas/particleatlas.atlas";
  public static final String DEFAULT_ATLAS_PLAY                   = "atlas/play.atlas";
  public static final String DEFAULT_ATLAS_NEWPIKA                   = "atlas/newpika.atlas";

  public static final String BMF                                  = "font/arial_uni_30";
  public static final String DEFAULT_ANIMAL                       = "textureAtlas/ani/";
  public static final String DEFAULT_UI                           = "textureAtlas/ui/";
  public static final int DEFAULT_SKIN                            = 0;

  public static final String[] LIST_SKIN_ANIMAL                   = {"ani0.atlas","ani1.atlas","ani2.atlas","ani3.atlas","ani4.atlas"};
  public static final String[] LIST_SKIN_UI                       = {"ui0.atlas","ui1.atlas","ui2.atlas","ui3.atlas","ui4.atlas"};
  public static final String DEFAULT_BG                           = "atlas/bg/";
  public static final String DEFAULT_ANIMAL_P                     = "atlas/bg/";
  public static final String DEFAULT_COMMON                       = "atlas/common/";
  public static final String DEFAULT_CROSS_PANEL                  = "atlas/crossPanel/";
  public static final String DEFAULT_MENU                         = "atlas/menu/";
  public static final String DEFAULT_PLAY                         = "atlas/play/";


  private GConstants() {
  }

}
