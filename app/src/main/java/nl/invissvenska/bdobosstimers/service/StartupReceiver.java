package nl.invissvenska.bdobosstimers.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (context != null && Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BOOT_COMPLETED)) {
                Intent serviceIntent = new Intent(context, BossAlertService.class);
                context.startService(serviceIntent);
            }
        } catch (IllegalStateException e) {
            Log.e("BDO", "backgroundservice not permitted because: " + e.getMessage());
        }
    }
}
