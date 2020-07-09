package nl.invissvenska.bdobosstimers.list;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.invissvenska.bdobosstimers.R;

public class BossViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView spawnTime;
    TextView timeLeft;
    ImageView boss1;
    ImageView boss2;
    CountDownTimer timer;

    public BossViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.boss_name);
        spawnTime = itemView.findViewById(R.id.spawn_time);
        timeLeft = itemView.findViewById(R.id.time_left);
        boss1 = itemView.findViewById(R.id.main_image_boss_one);
        boss2 = itemView.findViewById(R.id.main_image_boss_two);
    }
}
