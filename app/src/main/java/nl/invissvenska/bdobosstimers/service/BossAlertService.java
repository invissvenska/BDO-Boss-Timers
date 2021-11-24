package nl.invissvenska.bdobosstimers.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

import nl.invissvenska.bdobosstimers.Constants;
import nl.invissvenska.bdobosstimers.MainActivity;
import nl.invissvenska.bdobosstimers.R;
import nl.invissvenska.bdobosstimers.model.Boss;
import nl.invissvenska.bdobosstimers.preference.BossSettings;
import nl.invissvenska.bdobosstimers.util.BossHelper;
import nl.invissvenska.bdobosstimers.util.PreferenceUtil;
import timber.log.Timber;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;
import static nl.invissvenska.bdobosstimers.Constants.ACTION.NOTIFICATION_CLICKED;

public class BossAlertService extends Service {

    private Binder binder = new Binder();
    private MediaPlayer mediaPlayer = null;
    private BossAlertRefresher bossAlertRefresher = null;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        startForeground();
        mediaPlayer = MediaPlayer.create(this, R.raw.inflicted);
        mediaPlayer.setLooping(false);
    }

    private void startForeground() {
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel("bdo_service_channel_0", "BDO Spawn Service") : "";

        Intent stopNotificationIntent = new Intent(this, BossAlertService.class);
        stopNotificationIntent.setAction(Constants.ACTION.STOP_FOREGROUND_ACTION);
        PendingIntent Intent = PendingIntent.getService(this, 0, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(PRIORITY_MIN)
                .setContentTitle(getResources().getString(R.string.notification_service_title))
                .setContentText(getResources().getString(R.string.notification_service_desc))
                .setCategory(Notification.CATEGORY_SERVICE)
                .addAction(R.drawable.ic_clock, getResources().getString(R.string.notification_service_action), Intent)
                .build();
        startForeground(101, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
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
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Constants.ACTION.STOP_FOREGROUND_ACTION)) {
            stopForeground(true);
            stopSelfResult(startId);
            System.exit(0);
        } else {
            bossAlertRefresher = new BossAlertRefresher(mediaPlayer, this);
            bossAlertRefresher.soundsPlayed = 0;
            bossAlertRefresher.execute();
        }
        return START_STICKY;
    }

    static class BossAlertRefresher extends AsyncTask<Void, Void, Void> {
        private MediaPlayer mediaPlayer;
        @SuppressLint("StaticFieldLeak")
        private Context context;
        private Integer soundsPlayed = 0;

        public BossAlertRefresher(MediaPlayer mediaPlayer, Context context) {
            this.mediaPlayer = mediaPlayer;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                BossSettings bossSettings = PreferenceUtil.getInstance(context).getSettings();
                Integer limitMin = bossSettings.getAlertBefore() != null ? bossSettings.getAlertBefore() : 15;

                Boss nextBoss = BossHelper.getInstance().getNextBosses(bossSettings, 0, new ArrayList<>()).get(0);
                if (BossHelper.getInstance().checkAlertAllowed(nextBoss, bossSettings, soundsPlayed)) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    showNotification(bossSettings, nextBoss);
                    soundsPlayed++;
                } else if (nextBoss.getMinutesToSpawn(bossSettings) > limitMin) {
                    soundsPlayed = 0;
                }
                try {
                    Thread.sleep(1000L * (bossSettings.getAlertDelay() != null ? bossSettings.getAlertDelay() : 10));
                } catch (InterruptedException e) {
                    Timber.e(e, "Thread interrupted: %s", e.getMessage());
                }
            }
        }

        private void showNotification(BossSettings bossSettings, Boss boss) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            final String NOTIFICATION_CHANNEL_ID = "bdo_boss_spawn_channel_0";
            final int NOTIFICATION_ID = 1;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "BDO Boss Spawn Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

                // Configure the notification channel.
                notificationChannel.setDescription("Alerts for when a boss spawns");
                if ((bossSettings.getVibration() != null ? bossSettings.getVibration() : false)) {
                    notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                    notificationChannel.enableVibration(true);
                }
                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(NOTIFICATION_CLICKED, true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), boss.getBossOneImageResource()))
                    .setContentIntent(pendingIntent)
                    .setContentTitle(context.getResources().getString(R.string.notification_title))
                    .setContentText(context.getResources().getString(R.string.notification_content, boss.getName(), boss.getMinutesToSpawn(bossSettings), boss.getTimeSpawn()));

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
