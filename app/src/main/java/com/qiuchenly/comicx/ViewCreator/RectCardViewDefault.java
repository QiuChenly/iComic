package com.qiuchenly.comicx.ViewCreator;


import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class RectCardViewDefault extends RectCardView {

    public RectCardViewDefault(@NonNull Context context) {
        super(context);
    }

    public RectCardViewDefault(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectCardViewDefault(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getViewHeight(int widthMeasureSpec, int heightMeasureSpec) {
        return widthMeasureSpec;
    }
}