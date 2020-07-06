package nl.invissvenska.bdobosstimers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import nl.invissvenska.bdobosstimers.helper.BossHelper;
import nl.invissvenska.bdobosstimers.helper.BossSettings;
import nl.invissvenska.bdobosstimers.helper.TimeHelper;
import nl.invissvenska.bdobosstimers.service.BossAlertService;

import static nl.invissvenska.bdobosstimers.function.Constants.UPDATE_MESSAGE;

public class MainActivity extends AppCompatActivity implements SynchronizedActivity {

    private BossRefresher refresher = null;
    private CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkAndRunAlertService();
    }


    private BossSettings createTestSettings() {
        return new BossSettings(true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                2,
                3,
                3,
                3,
                3,
                3,
                3,
                1730,
                1731,
                74,
                3,
                10,
                true);
    }

    private void communicateWithService() {
        Intent serviceIntent = new Intent(getApplicationContext(), BossAlertService.class);
        serviceIntent.putExtra(UPDATE_MESSAGE, createTestSettings());
        startService(serviceIntent);
    }

    private void checkAndRunAlertService() {
        Intent intent = new Intent(getApplicationContext(), BossAlertService.class);
        startForegroundService(intent);
    }

    @Override
    protected void onPause() {
        cancelRefresher();
        if (timer != null) {
            timer.cancel();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        synchronize();
        communicateWithService();
    }

    private void cancelRefresher() {
        if (refresher != null) {
            refresher.cancel(true);
            refresher = null;
        }
    }

    @Override
    public void synchronize() {
        if (timer != null) {
            timer.cancel();
        }
        updateBoss();
        refresher = new BossRefresher(this);
        refresher.execute();
    }

    private void updateBoss() {

        final BossHelper.Boss nextBoss = BossHelper.getInstance().getNextBoss();
        BossHelper.Boss previousBoss = BossHelper.getInstance().getPreviousBoss();

        //previous boss
        TextView previousBossTitle = findViewById(R.id.main_text_boss_title_previous);
        previousBossTitle.setText(getResources().getString(R.string.previous_boss_announce, TimeHelper.getInstance().minutesToHoursAndMinutes(previousBoss.getMinutesToSpawn() * -1)));
        ImageView previousBossImageOne = findViewById(R.id.main_image_boss_previous_one);
        ImageView previousBossImageTwo = findViewById(R.id.main_image_boss_previous_two);
        previousBossImageOne.setImageResource(previousBoss.getBossOneImageResource());
        if (previousBoss.getBossTwoImageResource() != null) {
            previousBossImageTwo.setVisibility(View.VISIBLE);
            previousBossImageTwo.setImageResource(previousBoss.getBossTwoImageResource());
        } else {
            previousBossImageTwo.setVisibility(View.GONE);
        }

        //next boss
        final TextView nextBossTitle = findViewById(R.id.main_text_boss_title);
        ImageView nextBossImageOne = findViewById(R.id.main_image_boss_one);
        ImageView nextBossImageTwo = findViewById(R.id.main_image_boss_two);
        nextBossImageOne.setImageResource(nextBoss.getBossOneImageResource());
        if (nextBoss.getBossTwoImageResource() != null) {
            nextBossImageTwo.setVisibility(View.VISIBLE);
            nextBossImageTwo.setImageResource(nextBoss.getBossTwoImageResource());
        } else {
            nextBossImageTwo.setVisibility(View.GONE);
        }

        timer = new CountDownTimer(nextBoss.getMinutesToSpawn() * 60 * 1000, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                nextBossTitle.setText(nextBoss.getName() + " " + nextBoss.getTimeSpawn() + " " + TimeHelper.getInstance().secondsToHoursAndMinutesAndSeconds(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();

    }

    static class BossRefresher extends AsyncTask<Void, Void, Void> {

        private SynchronizedActivity synchronizedActivity;

        public BossRefresher(SynchronizedActivity synchronizedActivity) {
            this.synchronizedActivity = synchronizedActivity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                Log.e("BDO", "Thread interrupted: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            synchronizedActivity.synchronize();
        }
    }

    public void executeTest(View view) {

    }

    public void onParleyIncreaseButtonClick(View view) {

    }

    public void onParleyReductionButtonClick(View view) {

    }

    public void onBarterButtonClick(View view) {

    }

    public void onDaySettingChanged(View view) {

    }

    public void onBossClicked(View view) {

    }
}
