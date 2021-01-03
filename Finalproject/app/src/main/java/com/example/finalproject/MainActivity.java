package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    private EditText ed_name;
    private SQLiteDatabase dbrw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_name = findViewById(R.id.ed_name);

        Button btn_easy = findViewById(R.id.btn_easy);
        Button btn_standard = findViewById(R.id.btn_standard);
        Button btn_hard = findViewById(R.id.btn_hard);
        Bundle b = new Bundle();
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("key", b);
        btn_easy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ed_name.length() < 1)
                    Toast.makeText(MainActivity.this, "欄位請勿留空",
                            Toast.LENGTH_SHORT).show();
                else {
                    b.putString("name", ed_name.getText().toString());
                    b.putInt("debug", 0);
                    b.putInt("setting", 0);
                    startActivity(intent);
                }
            }
        });
        btn_standard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ed_name.length() < 1)
                    Toast.makeText(MainActivity.this, "欄位請勿留空",
                            Toast.LENGTH_SHORT).show();
                else {
                    b.putString("name", ed_name.getText().toString());
                    b.putInt("debug", 0);
                    b.putInt("setting", 1);
                    startActivity(intent);
                }
            }
        });
        btn_hard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ed_name.length() < 1)
                    Toast.makeText(MainActivity.this, "欄位請勿留空",
                            Toast.LENGTH_SHORT).show();
                else {
                    b.putString("name", ed_name.getText().toString());
                    b.putInt("debug", 0);
                    b.putInt("setting", 2);
                    startActivity(intent);
                }
            }
        });
    }
}