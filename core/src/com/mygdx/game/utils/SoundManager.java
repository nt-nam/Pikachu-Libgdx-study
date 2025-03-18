package com.mygdx.game.utils;

import static com.mygdx.game.utils.GameConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
  private Music backgroundMusic;                  // Nhạc nền
  private Map<String, Sound> soundEffects;        // Các hiệu ứng âm thanh
  private float musicVolume;                      // Âm lượng nhạc nền
  private float soundVolume;                      // Âm lượng hiệu ứng âm thanh
  private boolean musicMuted;                     // Trạng thái tắt nhạc
  private boolean soundMuted;                     // Trạng thái tắt âm thanh

  // Constructor
  public SoundManager() {
    this.soundEffects = new HashMap<>();
    this.musicVolume = MUSIC_VOLUME_DEFAULT;
    this.soundVolume = SOUND_VOLUME_DEFAULT;
    this.musicMuted = false;
    this.soundMuted = false;
    loadSounds(); // Tải âm thanh khi khởi tạo
  }

  // Tải tất cả âm thanh
  private void loadSounds() {
    // Tải nhạc nền
    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_PATH));
    backgroundMusic.setLooping(true); // Lặp lại nhạc nền
    backgroundMusic.setVolume(musicMuted ? 0 : musicVolume);

    // Tải hiệu ứng âm thanh
    soundEffects.put("match", Gdx.audio.newSound(Gdx.files.internal(MATCH_SOUND_PATH)));
    soundEffects.put("shuffle", Gdx.audio.newSound(Gdx.files.internal(SHUFFLE_SOUND_PATH)));
    soundEffects.put("click", Gdx.audio.newSound(Gdx.files.internal(CLICK_SOUND_PATH)));
  }

  // Phát nhạc nền
  public void playBackgroundMusic() {
    if (!musicMuted && !backgroundMusic.isPlaying()) {
      backgroundMusic.play();
    }
  }

  // Dừng nhạc nền
  public void stopBackgroundMusic() {
    if (backgroundMusic.isPlaying()) {
      backgroundMusic.stop();
    }
  }

  // Phát hiệu ứng âm thanh
  public void playSound(String soundName) {
    if (!soundMuted) {
      Sound sound = soundEffects.get(soundName);
      if (sound != null) {
        sound.play(soundVolume);
      } else {
        Gdx.app.log("SoundManager", "Sound not found: " + soundName);
      }
    }
  }

  // Điều chỉnh âm lượng nhạc nền
  public void setMusicVolume(float volume) {
    if (volume >= 0f && volume <= 1f) {
      this.musicVolume = volume;
      if (!musicMuted) {
        backgroundMusic.setVolume(volume);
      }
    }
  }

  // Điều chỉnh âm lượng hiệu ứng âm thanh
  public void setSoundVolume(float volume) {
    if (volume >= 0f && volume <= 1f) {
      this.soundVolume = volume;
    }
  }

  // Bật/tắt nhạc nền
  public void setMusicMuted(boolean muted) {
    this.musicMuted = muted;
    backgroundMusic.setVolume(muted ? 0 : musicVolume);
    if (muted) {
      stopBackgroundMusic();
    } else {
      playBackgroundMusic();
    }
  }

  // Bật/tắt hiệu ứng âm thanh
  public void setSoundMuted(boolean muted) {
    this.soundMuted = muted;
  }

  // Getter
  public float getMusicVolume() {
    return musicVolume;
  }

  public float getSoundVolume() {
    return soundVolume;
  }

  public boolean isMusicMuted() {
    return musicMuted;
  }

  public boolean isSoundMuted() {
    return soundMuted;
  }

  // Giải phóng tài nguyên
  public void dispose() {
    if (backgroundMusic != null) {
      backgroundMusic.dispose();
    }
    for (Sound sound : soundEffects.values()) {
      sound.dispose();
    }
    soundEffects.clear();
  }
}
