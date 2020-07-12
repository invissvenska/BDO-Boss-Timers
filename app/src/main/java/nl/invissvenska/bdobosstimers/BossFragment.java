package nl.invissvenska.bdobosstimers;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nl.invissvenska.bdobosstimers.helper.BossHelper;
import nl.invissvenska.bdobosstimers.list.BossAdapter;
import nl.invissvenska.bdobosstimers.service.BossAlertService;
import nl.invissvenska.bdobosstimers.util.Boss;
import nl.invissvenska.bdobosstimers.util.BossRefresher;
import nl.invissvenska.bdobosstimers.util.PreferenceUtil;

import static nl.invissvenska.bdobosstimers.Constants.EMPTY;
import static nl.invissvenska.bdobosstimers.Constants.UPDATE_MESSAGE;

public class BossFragment extends Fragment implements SynchronizedActivity {

    private static final Integer MAX_BOSS_COUNT = 6;

    private BossRefresher refresher = null;
    private CountDownTimer timer = null;
    private BossAdapter adapter;
    private PreferenceUtil preferences;

    public BossFragment() {
        //keep default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceUtil.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BossAdapter();
        recyclerView.setAdapter(adapter);
        checkAndRunAlertService();

        FloatingActionButton fb = view.findViewById(R.id.fab);
        fb.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, new SettingsFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        return view;
    }

    private void communicateWithService() {
        Intent serviceIntent = new Intent(getContext(), BossAlertService.class);
        serviceIntent.putExtra(UPDATE_MESSAGE, preferences.getSettings());
        getActivity().startService(serviceIntent);
    }

    private void checkAndRunAlertService() {
        Intent intent = new Intent(getContext(), BossAlertService.class);
        getActivity().startForegroundService(intent);
    }


    @Override
    public void onDetach() {
        cancelRefresher();
        if (timer != null) {
            timer.cancel();
        }
        super.onDetach();
    }

    @Override
    public void onResume() {
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
        new BossRefresher(this).execute();
    }

    private void updateBoss() {
        final Boss nextBoss = BossHelper.getInstance().getNextBoss(0);
        if (adapter.getItemCount() == 0) {
            initializeOverview(nextBoss);
        } else {
            renewOverview();
        }

        timer = new CountDownTimer((nextBoss.getMinutesToSpawn() + 1) * 60 * 1000, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                updateBoss();
            }
        };
        timer.start();
    }

    private void initializeOverview(Boss nextBoss) {
        adapter.add(BossHelper.getInstance().getPreviousBoss());
        adapter.add(nextBoss);

        for (int i = 1; i <= MAX_BOSS_COUNT; i++) {
            Boss boss = BossHelper.getInstance().getNextBoss(i);
            if (!boss.getName().equals(EMPTY)) {
                adapter.add(boss);
            }
        }
    }

    private void renewOverview() {
        adapter.remove(0);
        Boss boss = BossHelper.getInstance().getNextBoss(MAX_BOSS_COUNT);
        if (!boss.getName().equals(EMPTY)) {
            adapter.add(boss);
        }
    }
}
