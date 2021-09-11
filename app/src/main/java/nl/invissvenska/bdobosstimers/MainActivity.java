package nl.invissvenska.bdobosstimers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.jakewharton.threetenabp.AndroidThreeTen;

import nl.invissvenska.bdobosstimers.logging.ReleaseTree;
import timber.log.Timber;

import static nl.invissvenska.bdobosstimers.Constants.ACTION.NOTIFICATION_CLICKED;

public class MainActivity extends AppCompatActivity {

    ReviewManager reviewManager;
    ReviewInfo reviewInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidThreeTen.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }

        getReviewInfo();
        onNewIntent(getIntent());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, new BossFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(NOTIFICATION_CLICKED, false)){
            startReviewFlow();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    private void getReviewInfo() {
        reviewManager = ReviewManagerFactory.create(getApplicationContext());
        Task<ReviewInfo> manager = reviewManager.requestReviewFlow();
        manager.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            } else {
                Timber.e("In App ReviewFlow failed to start");
                Toast.makeText(getApplicationContext(), "In App ReviewFlow failed to start", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void startReviewFlow() {
        if (reviewInfo != null) {
            Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
            flow.addOnCompleteListener(task -> Toast.makeText(getApplicationContext(), "In App Rating complete", Toast.LENGTH_LONG).show());
        }
        else {
            Timber.e("In App Rating failed");
            Toast.makeText(getApplicationContext(), "In App Rating failed", Toast.LENGTH_LONG).show();
        }
    }
}
