package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements TKResultListener, RockerTrackListener {
    String name;
    int debug;
    int setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final com.example.finalproject.MoveButton rkv_rocker = findViewById(R.id.rkv_rocker);
        final com.example.finalproject.TKView tk_01 = findViewById(R.id.tk_01);

        tk_01.setOnClickListener(v ->tk_01.invalidate());
        tk_01.setTkResultListener(this);
        rkv_rocker.setTrackListener(this);

        Bundle b = getIntent().getExtras().getBundle("key");
        name = b.getString("name");
        debug = b.getInt("debug");
        setting = b.getInt( "setting");

        tk_01.setHealthPoint(setting);

        tk_01.setOnLongClickListener(v ->
        {
        tk_01.continuedMove();
        return true;
        });
        rkv_rocker.setOnClickListener(v ->rkv_rocker.invalidate());

    }

    @Override
    public void onToLeft() {
        ((TKView)this.findViewById(R.id.tk_01)).stepsLeft();
    }

    @Override
    public void onToRight() {
        ((TKView)this.findViewById(R.id.tk_01)).stepsRight();
    }

    /*@Override
    public void onToTop() {
        ((TKView)this.findViewById(R.id.tk_01)).stepsTop();
    }

    @Override
    public void onToDown() {
        ((TKView)this.findViewById(R.id.tk_01)).stepsDown();
    }*/

    @Override
    public void onStopMove() {
        ((TKView)this.findViewById(R.id.tk_01)).stopMove();
    }

    @Override
    public void onScoreListener(int score) {
        Bundle b = new Bundle();
        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
        intent.putExtra("key", b);
        Button check_score = findViewById(R.id.check_score);
        check_score.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                debug = 1;
                b.putInt("check_point",score);
                b.putInt("debug", debug);
                b.putString("name", name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onGameOver(int blood) {
        TextView tv_blood = findViewById(R.id.tv_blood);
        String s = Integer.toString(blood);
        tv_blood.setText(s);

    }
/*
    @Override
    public int gogo(int num) {
        int g = num;
        if (g == 1) {
            Intent intent = new Intent();
            intent.setClass(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        }
        return num;
    }*/

    /*public static class Data {
        public Listener mListener;//接口

        public Data(Listener mListener) {
            this.mListener = mListener;
        }
        public void sends(){
            String point = Integer.toString(point_le);
            mListener.send(point);
        }
    }*/
}
