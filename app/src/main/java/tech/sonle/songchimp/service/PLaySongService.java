package tech.sonle.songchimp.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;

import tech.sonle.songchimp.data.model.Song;
import tech.sonle.songchimp.ui.main.home.HomeFragment;

public class PLaySongService extends Service {

    private final IBinder iBinder = new PLaySongBinder();
    private Song mSong;
    private MediaPlayer mMediaPlayer;
    private SongController mSongController;
    private final Handler handler = new Handler(msg -> {
        if ((boolean) msg.obj) {
            currentTime(msg.what);
            onProcess(msg.what);
        } else {
            currentTime(0);
            mSongController.onStopSong();
        }
        return false;
    });

    public void setSongController(SongController mSongController) {
        this.mSongController = mSongController;
    }

    public class PLaySongBinder extends Binder {
        public PLaySongService getService() {
            return PLaySongService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSong = (Song) intent.getSerializableExtra(HomeFragment.SONG);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
        mMediaPlayer = MediaPlayer.create(this, mSong.getId());
        mMediaPlayer.start();

        new Thread(() -> {
            while (mMediaPlayer != null) {
                try {
                    if (mMediaPlayer.isPlaying()) {
                        Message message = new Message();
                        message.obj = true;
                        message.what = mMediaPlayer.getCurrentPosition();
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.obj = false;
                        handler.sendMessage(message);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return START_STICKY;
    }

    public void onPauseSong() {
        mMediaPlayer.pause();
    }

    public void onContinuesSong() {
        mMediaPlayer.start();
    }

    public void currentTime(long time) {
        if (mSongController != null)
            mSongController.onCurrentTime(milliSecondsToTimer(time));
    }

    public void totalTime() {
        if (mSongController != null)
            mSongController.onTotalTime(milliSecondsToTimer(mMediaPlayer.getDuration()));
    }

    public void onProcess(long currentTime) {
        if (mSongController != null)
            mSongController.updateSeekBar((int) (100 * currentTime / (mMediaPlayer.getDuration())));
    }

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public interface SongController {
        void onCurrentTime(String time);

        void onTotalTime(String time);

        void updateSeekBar(int process);

        void onStopSong();
    }
}
