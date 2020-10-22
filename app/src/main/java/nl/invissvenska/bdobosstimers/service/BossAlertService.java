package nl.invissvenska.bdobosstimers.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import nl.invissvenska.bdobosstimers.R;
import nl.invissvenska.bdobosstimers.helper.BossHelper;
import nl.invissvenska.bdobosstimers.helper.BossSettings;
import nl.invissvenska.bdobosstimers.util.Boss;
import nl.invissvenska.bdobosstimers.util.PreferenceUtil;

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
        bossAlertRefresher = new BossAlertRefresher(mediaPlayer, vibrator, this);
        bossAlertRefresher.soundsPlayed = 0;
        bossAlertRefresher.execute();
        return START_STICKY;
    }

    static class BossAlertRefresher extends AsyncTask<Void, Void, Void> {
        private MediaPlayer mediaPlayer;
        private Vibrator vibrator;
        @SuppressLint("StaticFieldLeak")
        private Context context;
        private Integer soundsPlayed = 0;

        public BossAlertRefresher(MediaPlayer mediaPlayer, Vibrator vibrator, Context context) {
            this.mediaPlayer = mediaPlayer;
            this.vibrator = vibrator;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                BossSettings bossSettings = PreferenceUtil.getInstance(context).getSettings();
                Integer limitMin = bossSettings.getAlertBefore() != null ? bossSettings.getAlertBefore() : 15;

                Boss nextBoss = BossHelper.getInstance().getBoss(bossSettings.getSelectedServer(), 0, new ArrayList<>(), 1).get(0);
                if (BossHelper.getInstance().checkAlertAllowed(nextBoss, bossSettings, soundsPlayed)) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    showNotification(bossSettings, nextBoss);
                    soundsPlayed++;
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

        private void showNotification(BossSettings bossSettings, Boss boss) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String NOTIFICATION_CHANNEL_ID = "bdo_boss_spawn_channel_0";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "BDO Boss Spawn Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                // Configure the notification channel.
                notificationChannel.setDescription("Alerts for when a boss spawns");
                if ((bossSettings.getVibration() != null ? bossSettings.getVibration() : false)) {
                    notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                    notificationChannel.enableVibration(true);
                }
                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), boss.getBossOneImageResource()))
                    .setContentTitle("BDO World boss")
                    .setContentText(boss.getName() + " will spawn in " + boss.getMinutesToSpawn() + " minutes at " + boss.getTimeSpawn());

            notificationManager.notify(/*notification id*/1, notificationBuilder.build());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
