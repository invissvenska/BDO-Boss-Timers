package nl.invissvenska.bdobosstimers;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nl.invissvenska.bdobosstimers.helper.BossHelper;
import nl.invissvenska.bdobosstimers.helper.BossSettings;
import nl.invissvenska.bdobosstimers.list.BossAdapter;
import nl.invissvenska.bdobosstimers.service.BossAlertService;
import nl.invissvenska.bdobosstimers.util.Boss;
import nl.invissvenska.bdobosstimers.util.BossRefresher;

import static nl.invissvenska.bdobosstimers.Constants.EMPTY;
import static nl.invissvenska.bdobosstimers.Constants.UPDATE_MESSAGE;

public class MainActivity extends AppCompatActivity implements SynchronizedActivity {

    private BossRefresher refresher = null;
    private CountDownTimer timer = null;
    private BossAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        checkAndRunAlertService();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new BossAdapter();
        recyclerView.setAdapter(adapter);
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
                3,
                3,
                60,
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

//        BossRefresher refresher = createSynchronize();
//        refresher.execute();
        new BossRefresher(this).execute();
    }

//    private BossRefresher createSynchronize() {
//        if (refresher == null){
//            return refresher = new BossRefresher(this);
//        }
//        refresher.cancel(true);
//        return refresher = new BossRefresher(this);
//    }

    private void updateBoss() {

        final Boss nextBoss = BossHelper.getInstance().getNextBoss(0);
        final Boss previousBoss = BossHelper.getInstance().getPreviousBoss();

        adapter.clear();
        adapter.add(previousBoss);
        adapter.add(nextBoss);

        for (int i = 1; i <= 7; i++) {
            Boss boss = BossHelper.getInstance().getNextBoss(i);
            if (!boss.getName().equals(EMPTY)) {
                adapter.add(boss);
            }
        }

        timer = new CountDownTimer(nextBoss.getMinutesToSpawn() * 60 * 1000, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                // nextBossTitle2.setText(TimeHelper.getInstance().secondsToHoursAndMinutesAndSeconds(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                updateBoss();
            }
        };
        timer.start();

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
