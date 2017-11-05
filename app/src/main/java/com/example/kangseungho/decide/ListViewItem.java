package com.example.kangseungho.decide;

/**
 * Created by KangSeungho on 2017-07-13.
 */

public class ListViewItem {
    private int list_id;
    private String list_name;
    private String list_menu;
    private String list_price;
    private String list_category;

    public void setId(int id) { list_id = id; }
    public void setName(String name) { list_name = name; }
    public void setMenu(String menu) { list_menu = menu; }
    public void setPrice(String price) { list_price = price; }
    public void setCategory(String category) { list_category = category; }

    public int getId() { return this.list_id; }
    public String getName() { return this.list_name; }
    public String getMenu() { return this.list_menu; }
    public String getPrice() { return this.list_price; }
    public String getCategory() { return this.list_category; }
}