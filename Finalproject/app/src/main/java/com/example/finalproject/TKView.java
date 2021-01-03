package com.example.finalproject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static androidx.core.content.ContextCompat.startActivity;

public class TKView extends View{

    private Paint paint;
    private final Random random = new Random();
    private int bombSpeed = 20;
    private int enemySpeed = 10;
    private int plantSpeed = 10;
    private int width;
    private int height;
    private boolean isStart = true;
    private Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tk);
    private Bitmap bomb = BitmapFactory.decodeResource(getResources(), R.mipmap.bomb);
    private Bitmap enemy = BitmapFactory.decodeResource(getResources(), R.mipmap.enemy);
    private Bitmap blast = BitmapFactory.decodeResource(getResources(), R.mipmap.blast);
    private int tkX;
    private int tkY;
    private boolean isMove = true;
    private boolean Move_init = true;
    private float touchX;
    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Point> enemys = new ArrayList<Point>();//blast
    private ArrayList<Point> blasts = new ArrayList<Point>();
    private int index;
    private static int score = 0;
    private int enemey_num  = 15;
    private int blood = 5;
    private int currentSetting = 1;
    private TKResultListener tkResultListener;

    public int bomb_num = 10;

    public TKView(Context context) {
        super(context, null);
    }
    public TKView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public TKView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);

    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#ff4358"));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(5);
            }
        }, 0, 40);
    }

    public void setTkResultListener(TKResultListener tkResultListener) {
        this.tkResultListener = tkResultListener;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        if (isStart) {
            if (Move_init) {
                Move_init = false;
                tkX = width / 2;
                tkY = height - 20;

            }

            canvas.drawBitmap(bitmap, tkX - 24, tkY - 50, paint);

            for (int i = 0; i < points.size(); i++) {
                canvas.drawBitmap(bomb, points.get(i).x, points.get(i).y, paint);
            }

            for (int i = 0; i < enemys.size(); i++) {
                canvas.drawBitmap(enemy, enemys.get(i).x - 24, enemys.get(i).y, paint);
            }
            for (int i = 0; i < blasts.size(); i++) {
                canvas.drawBitmap(blast, blasts.get(i).x - 64, blasts.get(i).y, paint);
                blasts.remove(i);
                score++;
                i--;
            }
            if (tkResultListener != null) {
                tkResultListener.onScoreListener(score);
            }
            if (tkResultListener != null) {
                tkResultListener.onGameOver(blood);
            }
        }
        else{
            showMessageDialog();
        }
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       switch (event.getAction()) {
           case MotionEvent.ACTION_DOWN:
                isMove = true;
                touchX = event.getX();
                if (touchX > tkX) {
                    moveToRight();
                } else {
                    moveToLeft();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                touchX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                break;
        }
        return super.onTouchEvent(event);
    }
*/
    public void stepsLeft(){
        if(!isMove || touchX!=0) {
            handler.removeMessages(1);
            isMove = true;
            touchX = 0;
            handler.sendEmptyMessage(1);
        }
    }

    public void stepsRight(){
        if(!isMove || touchX!=width) {
            handler.removeMessages(1);
            isMove = true;
            touchX = width;
            handler.sendEmptyMessage(1);
        }
    }

    private void moveToRight() {
        if (tkX < width - plantSpeed && tkX < touchX) {
            tkX += plantSpeed;
            invalidate();
        }
    }

    private void moveToLeft() {
        if (tkX > plantSpeed && tkX > touchX) {
            tkX -= plantSpeed;
            invalidate();
        }
    }
    private void gameover(int num){
        if(num <= 0)
        {
            isStart = false;
            for (int i = 0; i < enemys.size(); i++) {
                if (enemys.get(i).y >= 0) {
                    enemys.remove(i);
                    i--;
                }
            }
            for (int j = 0; j < points.size(); j++) {
                if(points.get(j).y >= 0)
                    points.remove(j);
                j--;
            }
        }
    }
    private void showMessageDialog() {
        post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getContext()).setMessage("Game " + "Over!")
                        .setCancelable(false)
                        .setPositiveButton("重新開始", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                reStartGame();
                            }
                        })
                        .setNegativeButton("結算成績",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                score = 0;
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

   /* public void stepsDown(){
        if(!isMove || touchY!=height) {
            handler.removeMessages(2);
            isMove = true;
            touchY = 0;
            handler.sendEmptyMessage(2);
        }
    }

    private void moveToDown() {
        if (tkY < height - plantSpeed && tkY < touchY) {
            tkY = tkY + plantSpeed;
            invalidate();
        }
    }
    public void stepsTop(){
        if(!isMove || touchY!=0) {
            handler.removeMessages(2);
            isMove = true;
            touchY = height-24;
            handler.sendEmptyMessage(2);
        }
    }

    private void moveToTop(){
        if (tkY > plantSpeed && tkY > touchY) {
            tkY -= plantSpeed;
            invalidate();
        }
    }*/
    public void setHealthPoint(int idx) {
         currentSetting = idx;
         setHealthPoint();
    }

    private void setHealthPoint() {
        switch (currentSetting) {
            case 0:
                blood = 10;
                break;
            case 1:
                blood = 5;
                break;
            case 2 :
                blood = 3;
                break;
        }
    }

    public void stopMove(){
        isMove = false;
    }

    public void continuedMove() {
        handler.sendEmptyMessage(1);
    }
    public void reStartGame() {
        setHealthPoint();
        score = 0;
        tkX = width / 2 -32;
        tkY = height - 84;
        isStart = true;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            //bomb_num = bbb/aaa;
            if (isStart) {
                switch (message.what) {
                    case 1:
                        if (touchX > tkX) {
                            moveToRight();
                        } else {
                            moveToLeft();
                        }
                        if (isMove) {
                            handler.sendEmptyMessageDelayed(1, 10);
                        }
                        break;
                /*case 2:
                    if(touchY > tkY){
                        moveToDown();
                    }else{
                        moveToTop();
                    }if (isMove) {
                    handler.sendEmptyMessageDelayed(2, 10);
                }
                    break;*/
                    case 5:
                        //敵人下降
                        for (int i = 0; i < enemys.size(); i++) {
                            int x = enemys.get(i).x;
                            int y = enemys.get(i).y + enemySpeed;
                            enemys.get(i).x = x;
                            enemys.get(i).y = y;
                            if (enemys.get(i).y >= height) {
                                enemys.remove(i);
                                blood--;
                                gameover(blood);
                                i--;
                            } else {
                                for (int j = 0; j < points.size(); j++) {
                                    Point p = getCollidePoint(x, y, points.get(j).x, points.get(j).y);
                                    if (p != null) {
                                        blasts.add(new Point((x + points.get(j).x) / 2, (y + points.get(j).y) / 2));
                                        points.remove(j);
                                        j--;
                                        enemys.remove(i);
                                        i--;
                                        break;
                                    }
                                }
                            }
                        }
                        //子彈上升
                        for (int i = 0; i < points.size(); i++) {
                            Point p = points.get(i);
                            points.get(i).x = p.x;
                            points.get(i).y = p.y - bombSpeed;
                            if (points.get(i).y <= 0) {
                                points.remove(i);
                                i--;
                            }
                        }
                        index++;
                        //子彈數量
                        if (index % bomb_num == 0) {
                            points.add(new Point(tkX, (tkY - 60)));
                        }
                        //敵人產生
                        if (index % enemey_num == 0) {
                            int x = random.nextInt(width);
                            enemys.add(new Point(x, 0));
                        }
                        invalidate();
                        break;
                }
            }
            return false;
        }

    });

    public Point getCollidePoint(int x, int y, int tx, int ty) {
        //碰撞点初始为0
        Point p = null;
        //得到第一个碰撞精灵位图的RectF类
        RectF rectF1 = new RectF(x, y, x + 24f, y + 24f);
        //得到第二个碰撞精灵位图的RectF类
        RectF rectF2 = new RectF(tx, ty, tx + 24f, ty + 24f);
        //新的RectF
        RectF rectF = new RectF();
        //通过setIntersect()方法得到两精灵是否相交的布尔值
        boolean isIntersect = rectF.setIntersect(rectF1, rectF2);
        //如果两精灵相交
        if (isIntersect) {
            //得到交点
            p = new Point(Math.round(rectF.centerX()), Math.round(rectF.centerY()));
        }
        //返回交点
        return p;
    }

}
