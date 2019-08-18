package com.qiuchenly.comicx.UI.BaseImp;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

abstract class RectCardView extends CardView {

    public RectCardView(@NonNull Context context) {
        super(context);
    }

    public RectCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取View的高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * @return 返回高度
     */
    abstract int getViewHeight(int widthMeasureSpec, int heightMeasureSpec);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, getViewHeight(widthMeasureSpec, heightMeasureSpec));
    }
}

