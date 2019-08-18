package com.qiuchenly.comicx.ViewCreator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ZoomPiCaRecyclerView extends RecyclerView {
    private static final int INVALID_POINTER_ID = -1;
    private static final String TAG = "ZoomableRecyclerView";
    static float gx;
    static float gy;
    Matrix matrix = new Matrix();
    private float centerX;
    private float centerY;
    private float defaultScaleFactor = 1.0f;
    private boolean isPagerEnabled;
    private boolean isVertical;
    private int mActivePointerId = -1;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private ScaleGestureDetector mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    private float mScaleFactor = 1.0f;
    private int screenWidth = 1080;
    private float totalMovementX;
    private float totalMovementY;

    public ZoomPiCaRecyclerView(Context context) {
        super(context);
    }

    public ZoomPiCaRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ZoomPiCaRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(this.mPosX, this.mPosY);
        canvas.scale(this.mScaleFactor, this.mScaleFactor, this.centerX, this.centerY);
        canvas.restore();
    }

    /* Access modifiers changed, original: protected */
    public void dispatchDraw(Canvas canvas) {
        canvas.save();
        this.isPagerEnabled = this.mScaleFactor <= this.defaultScaleFactor;
        if (this.mScaleFactor == this.defaultScaleFactor) {
            this.mPosX = 0.0f;
            this.mPosY = 0.0f;
        }
        canvas.translate(this.mPosX, this.mPosY);
        canvas.scale(this.mScaleFactor, this.mScaleFactor);
        super.dispatchDraw(canvas);
        canvas.restore();
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        this.mScaleDetector.onTouchEvent(motionEvent);
        int i = action & 255;
        int i2 = 0;
        if (i != 6) {
            float x;
            float y;
            switch (i) {
                case 0:
                    x = motionEvent.getX();
                    y = motionEvent.getY();
                    this.mLastTouchX = x;
                    this.mLastTouchY = y;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    break;
                case 1:
                    this.mActivePointerId = -1;
                    break;
                case 2:
                    i = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (i != -1) {
                        x = motionEvent.getX(i);
                        float y2 = motionEvent.getY(i);
                        y = x - this.mLastTouchX;
                        float f = y2 - this.mLastTouchY;
                        if (this.isVertical) {
                            this.mPosX += y * this.mScaleFactor;
                            if (this.mPosX > 0.0f) {
                                this.mPosX = 0.0f;
                            }
                            if ((-this.mPosX) / (this.mScaleFactor - 1.0f) > ((float) getWidth())) {
                                this.mPosX = ((float) (-getWidth())) * (this.mScaleFactor - 1.0f);
                            }
                        } else {
                            this.mPosY += f * this.mScaleFactor;
                            if (this.mPosY > 0.0f) {
                                this.mPosY = 0.0f;
                            }
                            if ((-this.mPosY) / (this.mScaleFactor - 1.0f) > ((float) getHeight())) {
                                this.mPosY = ((float) (-getHeight())) * (this.mScaleFactor - 1.0f);
                            }
                        }
                        this.mLastTouchX = x;
                        this.mLastTouchY = y2;
                        invalidate();
                        break;
                    }
                    break;
                case 3:
                    this.mActivePointerId = -1;
                    break;
            }
        } else {
            action = (action & 0xFF00) >> 8;
            if (motionEvent.getPointerId(action) == this.mActivePointerId) {
                if (action == 0) {
                    i2 = 1;
                }
                this.mLastTouchX = motionEvent.getX(i2);
                this.mLastTouchY = motionEvent.getY(i2);
                this.mActivePointerId = motionEvent.getPointerId(i2);
            }
        }
        return true;
    }

    public boolean fling(int i, int i2) {
        if (!this.isPagerEnabled || this.isVertical) {
            return super.fling(i, i2);
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        View findViewByPosition = linearLayoutManager.findViewByPosition(linearLayoutManager.findFirstVisibleItemPosition());
        View findViewByPosition2 = linearLayoutManager.findViewByPosition(findLastVisibleItemPosition);
        if (findViewByPosition2 == null || findViewByPosition == null) {
            return super.fling(i, i2);
        }
        i2 = (this.screenWidth - findViewByPosition2.getWidth()) / 2;
        findLastVisibleItemPosition = ((this.screenWidth - findViewByPosition.getWidth()) / 2) + findViewByPosition.getWidth();
        int left = findViewByPosition2.getLeft() - i2;
        findLastVisibleItemPosition -= findViewByPosition.getRight();
        if (i > 0) {
            smoothScrollBy(left, 0);
        } else {
            smoothScrollBy(-findLastVisibleItemPosition, 0);
        }
        return true;
    }

    public boolean isVertical() {
        return this.isVertical;
    }

    public void setVertical(boolean z) {
        this.isVertical = z;
        this.defaultScaleFactor = 1.0f;
        this.mScaleFactor = 1.0f;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public void setScreenWidth(int i) {
        this.screenWidth = i;
    }

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            ZoomPiCaRecyclerView.this.mScaleFactor = ZoomPiCaRecyclerView.this.mScaleFactor * scaleGestureDetector.getScaleFactor();
            ZoomPiCaRecyclerView.this.centerX = scaleGestureDetector.getFocusX();
            ZoomPiCaRecyclerView.this.centerY = scaleGestureDetector.getFocusY();
            ZoomPiCaRecyclerView.this.mScaleFactor = Math.max(ZoomPiCaRecyclerView.this.defaultScaleFactor, Math.min(ZoomPiCaRecyclerView.this.mScaleFactor, 3.0f));
            ZoomPiCaRecyclerView.this.invalidate();
            return true;
        }
    }
}