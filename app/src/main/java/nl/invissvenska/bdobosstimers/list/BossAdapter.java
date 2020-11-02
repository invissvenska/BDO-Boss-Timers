package nl.invissvenska.bdobosstimers.list;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nl.invissvenska.bdobosstimers.R;
import nl.invissvenska.bdobosstimers.helper.TimeHelper;
import nl.invissvenska.bdobosstimers.util.Boss;

public class BossAdapter extends RecyclerView.Adapter<BossViewHolder> {

    private List<Boss> bosses = new ArrayList<>();

    public void add(Boss boss) {
        bosses.add(bosses.size(), boss);
        notifyItemInserted(bosses.size());
    }

    public void clear() {
        bosses.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        bosses.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public BossViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BossViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BossViewHolder holder, int position) {
        final Boss boss = bosses.get(position);
        holder.name.setText(boss.getName().replace("&", " & "));
        holder.spawnTime.setText(boss.getTimeSpawn());
        if (boss.getBossTwoImageResource() != null) {
            holder.boss1.setImageResource(boss.getBossOneImageResource());
            holder.boss2.setImageResource(boss.getBossTwoImageResource());
            holder.bossSingle.setVisibility(View.GONE);
            holder.boss1.setVisibility(View.VISIBLE);
            holder.boss2.setVisibility(View.VISIBLE);
        } else {
            holder.bossSingle.setImageResource(boss.getBossOneImageResource());
            holder.bossSingle.setVisibility(View.VISIBLE);
            holder.boss1.setVisibility(View.GONE);
            holder.boss2.setVisibility(View.GONE);
        }
        if (holder.timer != null) {
            holder.timer.cancel();
        }
        if (boss.getMinutesToSpawn() > 0 && position != 0) {
            holder.timer = new CountDownTimer(boss.getMinutesToSpawn() * 60 * 1000, 1000L) {
                @Override
                public void onTick(long millisUntilFinished) {
                    holder.timeLeft.setText(TimeHelper.getInstance().secondsToHoursAndMinutesAndSeconds(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    holder.timeLeft.setText(holder.boss1.getContext().getString(R.string.spawned));
                }
            };
            holder.timer.start();
        } else if (boss.getMinutesToSpawn() == 0) {
            holder.timeLeft.setText(holder.boss1.getContext().getString(R.string.spawning));
        }
        if (position == 0) {
            holder.timeLeft.setText(holder.boss1.getContext().getString(R.string.spawned));
            holder.name.setAlpha(0.4f);
            holder.timeLeft.setAlpha(0.4f);
            holder.detailsPane.setAlpha(0.5f);
        } else {
            holder.name.setAlpha(1f);
            holder.timeLeft.setAlpha(1f);
            holder.detailsPane.setAlpha(1f);
        }
    }

    @Override
    public int getItemCount() {
        return bosses.size();
    }
}
