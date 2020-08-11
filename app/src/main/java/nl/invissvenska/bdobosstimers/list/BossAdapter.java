package nl.invissvenska.bdobosstimers.list;

import android.os.CountDownTimer;
import android.util.Log;
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
        bosses.add(boss);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        bosses.remove(index);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BossViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BossViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BossViewHolder holder, int position) {
        try {
            if (position == 0) {
                holder.boss1.setImageAlpha(70);
                holder.boss2.setImageAlpha(70);
                holder.timeLeft.setText(holder.boss1.getContext().getString(R.string.spawned));
                holder.name.setAlpha(0.4f);
                holder.spawnTime.setAlpha(0.4f);
                holder.timeLeft.setAlpha(0.4f);
            } else {
                holder.boss1.setImageAlpha(255);
                holder.boss2.setImageAlpha(255);
                holder.name.setAlpha(1f);
                holder.spawnTime.setAlpha(1f);
                holder.timeLeft.setAlpha(1f);
            }
            final Boss boss = bosses.get(position);
            holder.name.setText(boss.getName().replace("&", " & "));
            holder.spawnTime.setText(boss.getTimeSpawn());
            holder.boss1.setImageResource(boss.getBossOneImageResource());
            if (boss.getBossTwoImageResource() != null) {
                holder.boss2.setImageResource(boss.getBossTwoImageResource());
                holder.boss2.setVisibility(View.VISIBLE);
            } else {
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
            }
        } catch (NullPointerException e) {
            Log.e("BDO", "something was null: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return bosses.size();
    }
}
