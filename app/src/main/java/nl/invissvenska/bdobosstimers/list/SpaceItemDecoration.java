package nl.invissvenska.bdobosstimers.list;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final static int MARGIN_IN_DIPS = 16;
    private final int margin;

    public SpaceItemDecoration(@NonNull Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_IN_DIPS, metrics);
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, @NotNull RecyclerView.State state) {
        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }
        if ((parent.getAdapter() != null) && (itemPosition != parent.getAdapter().getItemCount() - 1)) {
            outRect.bottom = margin;
        }
    }
}
