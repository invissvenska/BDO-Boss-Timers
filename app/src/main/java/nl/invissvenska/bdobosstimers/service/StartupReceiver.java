package nl.invissvenska.bdobosstimers.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            Intent serviceIntent = new Intent(context, BossAlertService.class);
            context.startService(serviceIntent);
        }
    }
}
