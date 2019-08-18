package com.qiuchenly.comicx.UI.BaseImp;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ItemDecoration
 * Created by meikai on 2017/11/07.
 */
public abstract class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    public abstract boolean needFixed(int position);

    @Override
    public void getItemOffsets(Rect outRect, View child, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, child, parent, state);
        int pos = parent.getChildAdapterPosition(child);
        if (needFixed(pos)) {
            outRect.set(7, 0, 7, 5);
        }
    }
}