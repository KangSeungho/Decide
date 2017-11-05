package com.example.kangseungho.decide;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView select_menu;
    Button main_set, main_reset;

    ArrayList<String> menu_list = new ArrayList<String>();
    int count=3;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    CountDownTimer countDownTimer;
    int menu_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add 버튼 작동 메소드
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_page("insert");
            }
        });

        select_menu = (TextView) findViewById(R.id.select_menu);
        main_set = (Button) findViewById(R.id.main_set);
        main_reset = (Button) findViewById(R.id.main_reset);

        DBHelper = new DatabaseHelper(this);
        db = DBHelper.getWritableDatabase();

        list_read();

        main_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    main_set.setEnabled(false);
                    list_read();

                    if(menu_list.size() != 0) {
                        countDownTimer();
                        countDownTimer.start();

                        menu_index = (int)(Math.random()*menu_list.size());
                        select_menu.setText(menu_list.get(menu_index));
                    } else {
                        select_menu.setText("데이터를 입력해주세요.");
                    }
                } catch(Exception e) {
                    select_menu.setText("데이터를 입력해주세요.");
                }
            }
        });

        main_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    countDownTimer.cancel();
                } catch (Exception e) {
                    countDownTimer = null;
                }
                select_menu.setText("");
                main_set.setEnabled(true);
                count=3;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //메뉴 선택 옵션
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_list:
                switch_page("list");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switch_page(String pages) {
        switch(pages) {
            case "list":
                startActivity(new Intent(this, ListActivity.class));
                break;
            case "setting":
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case "insert":
                startActivity(new Intent(this, InsertActivity.class));
                break;
        }
    }

    void countDownTimer(){
        countDownTimer = new CountDownTimer(count*1000, 980) {
            @Override
            public void onTick(long millisUntilFinished) {
                select_menu.setText(String.valueOf(count));
                count--;
            }

            @Override
            public void onFinish() {
                menu_index = (int)(Math.random()*menu_list.size());
                select_menu.setText(menu_list.get(menu_index));
            }
        };
    }

    public void list_read() {
        menu_list= new ArrayList<String>();
        String sql = "select * from house_list;";
        Cursor results = db.rawQuery(sql, null);

        results.moveToFirst();

        while(!results.isAfterLast()) {
            String name = results.getString(1);

            menu_list.add(name);
            results.moveToNext();
        }

        results.close();
    }
}