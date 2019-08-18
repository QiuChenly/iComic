package com.qiuchenly.comicx.UI.BaseImp;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class SuperImageView extends AppCompatImageView {
    public SuperImageView(Context context) {
        super(context);
    }

    public SuperImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
