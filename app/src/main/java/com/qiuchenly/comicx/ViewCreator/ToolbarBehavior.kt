package com.qiuchenly.comicx.ViewCreator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.qiuchenly.comicx.Utils.MoveUpwardBehavior

class ToolbarBehavior : MoveUpwardBehavior {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }


    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type
        )
//        println(""+dyConsumed + " " + dyUnconsumed)

        when {
            dyConsumed > 0 -> {

            }
            dyConsumed < 0 -> {

            }
        }
    }
}