package com.qiuchenly.comicx.UI.BaseImp

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import java.util.*

/**
 * Activity管理类
 * 作者:网络
 */
class AppManager {

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        mStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity {
        return mStack!!.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = mStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null && mStack!!.contains(activity)) {
            mStack!!.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null && mStack!!.contains(activity)) {
            mStack!!.remove(activity)
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in mStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = mStack!!.size
        while (i < size) {
            if (null != mStack!![i]) {
                finishActivity(mStack!![i])
            }
            i++
        }
        mStack!!.clear()
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {

        try {
            finishAllActivity()
            // System.exit(0);
        } catch (e: Exception) {
        }

        // 获取packagemanager的实例
        try {
            val activityMgr = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.killBackgroundProcesses(context.packageName)
            mStack = null
            Appm = null
            System.exit(0)
        } catch (e: Exception) {
            System.exit(0)
        }

    }

    companion object {
        private var mStack: Stack<Activity>? = null
        private var Appm: AppManager? = null

        val appm: AppManager
            get() {
                if (mStack == null) mStack = Stack()
                if (Appm == null) Appm = AppManager()
                return Appm!!
            }


        @TargetApi(19)
        fun setStatusBarColor(activity: Activity, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                val decorViewGroup = activity.window.decorView as ViewGroup
                //获取自己布局的根视图
                val rootView = (decorViewGroup.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
                //预留状态栏位置
                rootView.fitsSystemWindows = true

                //添加状态栏高度的视图布局，并填充颜色
                val statusBarTintView = View(activity)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    getInternalDimensionSize(activity.resources, "status_bar_height")
                )
                params.gravity = Gravity.TOP
                statusBarTintView.layoutParams = params
                statusBarTintView.setBackgroundColor(color)
                decorViewGroup.addView(statusBarTintView)
            }
        }

        fun getInternalDimensionSize(res: Resources, key: String): Int {
            var result = 0
            val resourceId = res.getIdentifier(key, "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
            return result
        }


        /**
         * 单一实例
         */
        val appManager: AppManager
            get() {
                if (Appm == null) {
                    Appm = AppManager()
                }

                if (mStack == null) {
                    mStack = Stack()
                }

                return Appm!!
            }

        /**
         * 获取指定的Activity
         *
         * @author kymjs
         */
        fun getActivity(cls: Class<*>): Activity? {
            if (mStack != null)
                for (activity in mStack!!) {
                    if (activity.javaClass == cls) {
                        return activity
                    }
                }
            return null
        }
    }

}
