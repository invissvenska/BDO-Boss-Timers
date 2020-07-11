package nl.invissvenska.bdobosstimers.util;

import android.os.AsyncTask;
import android.util.Log;

import nl.invissvenska.bdobosstimers.SynchronizedActivity;

public class BossRefresher extends AsyncTask<Void, Void, Void> {

    private SynchronizedActivity synchronizedActivity;

    public BossRefresher(SynchronizedActivity synchronizedActivity) {
        this.synchronizedActivity = synchronizedActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            Log.e("BDO", "Thread interrupted: ", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
     //   synchronizedActivity.synchronize();
    }
}