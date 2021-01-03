package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity{
    String scores;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        dbrw = new MyDBHelper(this).getWritableDatabase();
        Bundle b = getIntent().getExtras().getBundle("key");

        int score = b.getInt("check_point");

        String name = b.getString("name");
        int debug = b.getInt("debug");

        if(debug == 1)
        {
            scores = Integer.toString(score);
        }
        else {
            scores = "0";
        }

            //TextView tv_score = findViewById(R.id.tv_score);
            dbrw.execSQL("INSERT INTO myTable(book, score) VALUES(?,?)"
                    , new Object[]{name, scores});

            Cursor c;
            if (name.length() < 1) {
                c = dbrw.rawQuery("SELECT * FROM myTable",null);
            }else {
                c = dbrw.rawQuery("SELECT * FROM myTable WHERE book LIKE '"+ name + "'",null);
            }
            c.moveToFirst();
            items.clear();
            Toast.makeText(MainActivity3.this, "共有" + c.getCount()
                    + "筆資料", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < c.getCount(); i++) {
                items.add("玩家:" + c.getString(0) +
                        "\t\t\t\t 分數:" + c.getString(1));
                c.moveToNext();
            }
            adapter.notifyDataSetChanged();
            c.close();
            debug = 1;

        Button btn_back = findViewById(R.id.back);

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity3.this,
                        MainActivity.class), 1);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbrw.close();
    }
}