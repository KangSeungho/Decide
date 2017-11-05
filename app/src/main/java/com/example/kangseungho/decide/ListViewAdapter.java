package com.example.kangseungho.decide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-07-13.
 */

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.list_name);
        TextView menuTextView = (TextView) convertView.findViewById(R.id.list_menu);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.list_price);
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.list_category);

        ListViewItem listViewItem = listViewItemList.get(position);

        nameTextView.setText(listViewItem.getName());
        menuTextView.setText(listViewItem.getMenu());
        priceTextView.setText(listViewItem.getPrice());
        categoryTextView.setText(listViewItem.getCategory());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ListViewItem getItem(int position) {
        return listViewItemList.get(position);
    }

    public String getname(int position) {
        return listViewItemList.get(position).getName();
    }

    public int getId(int position) {
        return listViewItemList.get(position).getId();
    }

    public void addItem(int id, String name, String menu, String price, String category) {
        ListViewItem item = new ListViewItem();

        item.setId(id);
        item.setName(name);
        item.setMenu(menu);
        item.setPrice(price);
        item.setCategory(category);

        listViewItemList.add(item);
    }
}