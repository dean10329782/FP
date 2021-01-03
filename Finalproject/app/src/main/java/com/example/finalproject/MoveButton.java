package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.Nullable;


public class MoveButton extends View{
    private Paint paint;
    private int width;
    private int height;
    private int radius;
    private boolean isStart = true;
    private float cx;
    private float cy;
    private RockerTrackListener trackListener;

    public MoveButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setTrackListener(RockerTrackListener trackListener) {
        this.trackListener = trackListener;
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        radius = width / 4;
        paint.setColor(Color.argb(220, 255, 255, 255));
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setColor(Color.argb(66, 136, 136, 136));
        canvas.drawCircle(width / 2, height / 2, radius - 10, paint);
        paint.setColor(Color.argb(90, 68, 136, 68));
        if (isStart) {
            cx = width / 2;
            cy = height / 2;
            isStart = false;
        } else {

        }
        canvas.drawCircle(cx, cy, 60, paint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                setMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                cx = width / 2;
                cy = height / 2;
                if(trackListener!=null){
                    trackListener.onStopMove();
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setMove(float x, float y) {
        double d = Math.sqrt((x - width / 2) * (x - width / 2) + (y - height / 2) * (y - height / 2));
        if (d < radius) {
            cx = x;
            cy = y;
            if (trackListener != null) {
                if (x > width / 2) {
                    trackListener.onToRight();
                }else {
                    trackListener.onToLeft();
                }
                /*if (y > height/2) {
                    trackListener.onToTop();
                }else {
                    trackListener.onToDown();
                }*/
            }
            invalidate();
        }
    }
}
