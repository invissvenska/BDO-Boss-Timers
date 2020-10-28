package nl.invissvenska.bdobosstimers.list;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import nl.invissvenska.bdobosstimers.util.Boss;

public class BossDiffUtil extends DiffUtil.Callback {

    List<Boss> oldBosses;
    List<Boss> newBosses;

    public BossDiffUtil(List<Boss> newBosses, List<Boss> oldBosses) {
        this.newBosses = newBosses;
        this.oldBosses = oldBosses;
    }

    @Override
    public int getOldListSize() {
        return oldBosses.size();
    }

    @Override
    public int getNewListSize() {
        return newBosses.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBosses.get(oldItemPosition).getName().equals(newBosses.get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBosses.get(oldItemPosition).getTimeSpawn().equals(newBosses.get(newItemPosition).getTimeSpawn());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
