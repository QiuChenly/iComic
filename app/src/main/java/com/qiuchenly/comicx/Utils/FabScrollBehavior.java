package com.qiuchenly.comicx.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * 该类继承自移动类并实现了View的动态隐藏
 */
public class FabScrollBehavior extends MoveUpwardBehavior {

    // 因为需要在布局xml中引用，所以必须实现该构造方法
    public FabScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout,
            @NonNull View child,
            @NonNull View directTargetChild,
            @NonNull View target,
            int axes,
            int type) {
        // 确保滚动方向为垂直方向
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout,
            @NonNull View child,
            @NonNull View target,
            int dxConsumed,
            int dyConsumed,
            int dxUnconsumed,
            int dyUnconsumed,
            int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 0) { // 向下滑动
            animateOut(child);
        } else if (dyConsumed < 0) { // 向上滑动
            animateIn(child);
        }
    }

    // FAB移出屏幕动画（隐藏动画）
    private void animateOut(View fab) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        int bottomMargin = layoutParams.bottomMargin;
        fab.animate().translationY(fab.getHeight() + bottomMargin).setInterpolator(new LinearInterpolator()).start();
    }

    // FAB移入屏幕动画（显示动画）
    private void animateIn(View fab) {
        fab.animate().translationY(0).setInterpolator(new LinearInterpolator()).start();
    }
}
