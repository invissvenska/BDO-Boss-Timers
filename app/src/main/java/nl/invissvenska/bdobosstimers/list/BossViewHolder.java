package nl.invissvenska.bdobosstimers.list;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.invissvenska.bdobosstimers.R;

public class BossViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView spawnTime;
    TextView timeLeft;
    CircleImageView bossSingle;
    CircleImageView boss1;
    CircleImageView boss2;
    CountDownTimer timer;
    ConstraintLayout detailsPane;

    public BossViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.boss_name);
        spawnTime = itemView.findViewById(R.id.spawn_time);
        timeLeft = itemView.findViewById(R.id.time_left);
        bossSingle = itemView.findViewById(R.id.main_image_boss_single);
        boss1 = itemView.findViewById(R.id.main_image_boss_one);
        boss2 = itemView.findViewById(R.id.main_image_boss_two);
        detailsPane = itemView.findViewById(R.id.details_pane);
    }
}
