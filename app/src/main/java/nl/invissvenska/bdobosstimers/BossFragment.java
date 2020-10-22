package nl.invissvenska.bdobosstimers;

import android.content.Intent;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.List;

import nl.invissvenska.bdobosstimers.helper.BossHelper;
import nl.invissvenska.bdobosstimers.helper.BossSettings;
import nl.invissvenska.bdobosstimers.list.BossAdapter;
import nl.invissvenska.bdobosstimers.service.BossAlertService;
import nl.invissvenska.bdobosstimers.util.Boss;
import nl.invissvenska.bdobosstimers.util.PreferenceUtil;

public class BossFragment extends Fragment implements SynchronizedActivity {

    private CountDownTimer timer = null;
    private BossAdapter adapter;

    public BossFragment() {
        //keep default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getActivity().startService(serviceIntent);
    }

    private void checkAndRunAlertService() {
        Intent intent = new Intent(getContext(), BossAlertService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().startForegroundService(intent);
        } else {
            getActivity().startService(intent);
        }
    }

    @Override
    public void onDetach() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        synchronize();
        communicateWithService();
    }

    @Override
    public void synchronize() {
        if (timer != null) {
            timer.cancel();
        }
        updateBoss();
    }

    private void updateBoss() {
        BossSettings bossSettings = PreferenceUtil.getInstance(getContext()).getSettings();

        List<Boss> bosses = BossHelper.getInstance().getNextBosses(bossSettings.getSelectedServer(), 0, new ArrayList<>(), bossSettings.getMaxBosses());
        if (adapter.getItemCount() == 0) {
            initializeOverview(bosses);
        } else {
            renewOverview(bosses);
        }

        timer = new CountDownTimer((bosses.get(0).getMinutesToSpawn() + 1) * 60 * 1000, 1000L) {
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

    private void initializeOverview(List<Boss> bosses) {
        BossSettings bossSettings = PreferenceUtil.getInstance(getContext()).getSettings();
        adapter.add(BossHelper.getInstance().getPreviousBoss(bossSettings.getSelectedServer(), 0));
        for (Boss boss : bosses) {
            adapter.add(boss);
        }
    }

    private void renewOverview(List<Boss> bosses) {
        BossSettings bossSettings = PreferenceUtil.getInstance(getContext()).getSettings();
        adapter.remove(0);
        adapter.add(bosses.get(bossSettings.getMaxBosses() - 1));
    }
}
