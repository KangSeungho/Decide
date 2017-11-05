package com.example.kangseungho.decide;
import android.provider.BaseColumns;

/**
 * Created by KangSeungho on 2017-07-13.
 */

public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String _TABLENAME = "house_list";
        public static final String NAME = "name";
        public static final String MENU = "menu";
        public static final String PRICE = "price";
        public static final String CATEGORY = "category";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        + _ID + " integer primary key autoincrement, "
                        + NAME + " text unique , "
                        + MENU + " text not null , "
                        + PRICE + " integer not null , "
                        + CATEGORY + ");";
    }
}