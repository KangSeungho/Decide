package com.example.kangseungho.decide;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-07-25.
 */

public class ListActivity extends AppCompatActivity {
    ListView listview;
    ListViewAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int division;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    ArrayList<String> menu_list = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        //add 버튼 작동 메소드
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InsertActivity.class));
            }
        });

        DBHelper = new DatabaseHelper(this);
        db = DBHelper.getWritableDatabase();

        list_reset();

        // 리스트 뷰 새로고침
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list_reset();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // 리스트 클릭 시 이벤트
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                division = position;
                request();
            }
        });
    }

    // 삭제 Alert
    private void request() {
        String title = "삭제";
        String message = "정말 삭제를 원하십니까?";
        String titleButtonYes = "예";
        String titleButtonNo = "아니오";

        AlertDialog dialog = makeRequestDialog(title, message, titleButtonNo, titleButtonYes);
        dialog.show();
    }
    private AlertDialog makeRequestDialog(CharSequence title, CharSequence message, CharSequence titleButtonNo, CharSequence titleButtonYes) {
        AlertDialog.Builder requestDialog = new AlertDialog.Builder(this);
        requestDialog.setTitle(title);
        requestDialog.setMessage(message);

        //예 클릭 시
        requestDialog.setPositiveButton(titleButtonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    String _id = String.valueOf(adapter.getId(division));

                    String sql = "DELETE FROM house_list WHERE _id=" + _id + ";";
                    db.execSQL(sql);
                    //main_list.remove(adapter.getname(division));
                    list_reset();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "에러났당 고쳐라..", Toast.LENGTH_LONG).show();
                }
            }
        });

        //아니오
        requestDialog.setNegativeButton(titleButtonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        return requestDialog.show();
    }

    public void list_reset() {
        adapter = new ListViewAdapter();

        listview = (ListView) findViewById(R.id.list_page);

        menu_list= new ArrayList<String>();
        String sql = "select * from house_list;";
        Cursor results = db.rawQuery(sql, null);

        results.moveToFirst();

        while(!results.isAfterLast()) {
            int id = Integer.parseInt(results.getString(0));
            String name = results.getString(1);
            String menu = results.getString(2);
            int i_price = results.getInt(3);
            String price = String.valueOf(i_price);
            String category = results.getString(4);

            menu_list.add(name);
            adapter.addItem(id, name, menu, price, category);
            results.moveToNext();
        }
        listview.setAdapter(adapter);

        results.close();
    }
}