package nl.invissvenska.bdobosstimers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


//        BossHelper.Boss previous = BossHelper.getInstance().getPreviousBoss();
//
//        Log.d("BOSS PREV", previous.getName() + " " + previous.getTimeSpawn() + " " + previous.getMinutesToSpawn());
//
//        BossHelper.Boss boss = BossHelper.getInstance().getNextBoss();
//
//        Log.d("BOSS", boss.getName() + " " + boss.getTimeSpawn() + " " + boss.getMinutesToSpawn());

        checkAndRunAlertService();

    }


    private BossSettings createTestSettings() {
        return new BossSettings(false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                3,
                3,
                3,
                3,
                3,
                3,
                3,
                0,
                2400,
                39,
                1,
                1,
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
        if(timer != null) {
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
        nextBossTitle.setText(nextBoss.getName() + " " + nextBoss.getTimeSpawn()  + " " + TimeHelper.getInstance().minutesToHoursAndMinutes(nextBoss.getMinutesToSpawn()));
        ImageView nextBossImageOne = findViewById(R.id.main_image_boss_one);
        ImageView nextBossImageTwo = findViewById(R.id.main_image_boss_two);
        nextBossImageOne.setImageResource(nextBoss.getBossOneImageResource());
        if (nextBoss.getBossTwoImageResource() != null) {
            nextBossImageTwo.setVisibility(View.VISIBLE);
            nextBossImageTwo.setImageResource(nextBoss.getBossTwoImageResource());
        } else {
            nextBossImageTwo.setVisibility(View.GONE);
        }

        timer = new CountDownTimer(TimeHelper.getInstance().getSecondsToSpawn(nextBoss.getTimeSpawn()), 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                nextBossTitle.setText(nextBoss.getName() + " " + nextBoss.getTimeSpawn()  + " " + millisUntilFinished);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();

    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    class BossRefresher extends AsyncTask<Void, Void, Void> {

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

        //        override fun doInBackground(vararg params: Void?): Void?{
//                Thread.sleep(10000)
//        return null
//        }
//
//        override fun onPostExecute(result: Void?) {
//            synchronizedActivity.synchronize()
//        }
    }

//    fun executeTest(@Suppress("UNUSED_PARAMETER") view: View) {
//        val intent = Intent(this, SettingsActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//        startActivity(intent)
//    }
}