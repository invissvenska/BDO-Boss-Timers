package nl.invissvenska.bdobosstimers.logging;

import android.annotation.SuppressLint;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

@SuppressLint("LogNotTimber")
public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {
        if (priority == Log.ERROR) {
            Log.e("BDO", message, t);
        }
        if (priority == Log.WARN) {
            Log.w("BDO", message);
        }
    }
}
