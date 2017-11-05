package com.example.kangseungho.decide;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by KangSeungho on 2017-07-25.
 */

public class InsertActivity extends AppCompatActivity {
    Button insert_save, insert_cancel;
    EditText insert_name, insert_menu, insert_price, insert_category;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity);

        DBHelper = new DatabaseHelper(this);
        db = DBHelper.getWritableDatabase();

        insert_save = (Button) findViewById(R.id.insert_save);
        insert_cancel = (Button) findViewById(R.id.insert_cancel);

        insert_name = (EditText) findViewById(R.id.insert_name);
        insert_menu = (EditText) findViewById(R.id.insert_menu);
        insert_price = (EditText) findViewById(R.id.insert_price);
        insert_category = (EditText) findViewById(R.id.insert_category);

        // Insert_page 버튼
        insert_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = insert_name.getText().toString();
                    String menu = insert_menu.getText().toString();
                    String price = insert_price.getText().toString();
                    String category = insert_category.getText().toString();

                    insert_name.setText("");
                    insert_menu.setText("");
                    insert_price.setText("");
                    insert_category.setText("");

                    db_insert(name, menu, price, category);
                    finish();
                    //switch_page("list");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "입력 사항을 모두 기입하여 주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        insert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void db_insert(String name, String menu, String price, String category) throws SQLException {
        int i_price = Integer.parseInt(price);
        db.execSQL("INSERT INTO house_list(name, menu, price, category) VALUES('" + name + "', '" + menu + "', " + i_price + ", '" + category + "');");
    }
}
