package nl.invissvenska.bdobosstimers.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;

import nl.invissvenska.bdobosstimers.R;
import nl.invissvenska.bdobosstimers.util.Boss;
import nl.invissvenska.bdobosstimers.helper.BossHelper;
import nl.invissvenska.bdobosstimers.helper.BossSettings;

import static nl.invissvenska.bdobosstimers.Constants.UPDATE_MESSAGE;

public class BossAlertService extends Service {

    private Binder binder = new Binder();
    private Vibrator vibrator = null;
    private MediaPlayer mediaPlayer = null;
    private BossAlertRefresher bossAlertRefresher = null;

    @Override
    public void onCreate() {
        super.onCreate();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.inflicted);
        mediaPlayer.setLooping(false);
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        if (bossAlertRefresher != null) {
            bossAlertRefresher.cancel(true);
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BossSettings bossSettings = intent.getParcelableExtra(UPDATE_MESSAGE);
        if (bossSettings != null) {
            bossAlertRefresher.cancel(true);
        }
        bossAlertRefresher = new BossAlertRefresher(mediaPlayer, vibrator, bossSettings);
        bossAlertRefresher.soundsPlayed = 0;
        bossAlertRefresher.execute();
        return START_STICKY;
    }

    static class BossAlertRefresher extends AsyncTask<Void, Void, Void> {
        private MediaPlayer mediaPlayer;
        private Vibrator vibrator;
        private BossSettings bossSettings;
        private Integer soundsPlayed = 0;

        public BossAlertRefresher(MediaPlayer mediaPlayer, Vibrator vibrator, BossSettings bossSettings) {
            this.mediaPlayer = mediaPlayer;
            this.vibrator = vibrator;
            this.bossSettings = bossSettings;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Integer limitMin = bossSettings.getAlertBefore() != null ? bossSettings.getAlertBefore() : 15;
            while (true) {
                Boss nextBoss = BossHelper.getInstance().getNextBoss(0);
                if (BossHelper.getInstance().checkAlertAllowed(nextBoss, bossSettings, soundsPlayed)) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    vibrate();
                    soundsPlayed++;
                    Log.d("BDO", "BOSS ALERT BOSS ALERT");
                } else if (nextBoss.getMinutesToSpawn() > limitMin) {
                    soundsPlayed = 0;
                }
                try {
                    Thread.sleep(1000L * (bossSettings.getAlertDelay() != null ? bossSettings.getAlertDelay() : 10));
                } catch (InterruptedException e) {
                    Log.e("BDO", "Thread interrupted: ", e);
                }
            }
        }

        private void vibrate() {
            if ((bossSettings.getVibration() != null ? bossSettings.getVibration() : false)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
