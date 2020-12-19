package com.myapp.groceryapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ItemViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String [] nameOfItems;
    private String [] amountOfItems;

    public ItemViewAdapter (Context context, ArrayList <String> itemNames, ArrayList <String> itemAmounts) {
        this.context = context;

        arraylistToArray(itemNames, itemAmounts);

        if (nameOfItems == null) {
            Log.d ("red", "length of itemNames is: " + nameOfItems.length);
        }

        if (amountOfItems == null) {
            Log.d ("red", "length of itemNames is: " + amountOfItems.length);
        }
    }
    @Override
    public int getCount() {
        if (nameOfItems == null) {
            Log.d ("red", "itemNames is null");

        }

        return nameOfItems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_view_layout, null);
        }

        TextView nameOfItem = convertView.findViewById(R.id.item_amount);
        TextView amountOfItem = convertView.findViewById(R.id.item_name);

        nameOfItem.setText(nameOfItems [position]);
        amountOfItem.setText(amountOfItems [position]);

        return convertView;
    }

    public void arraylistToArray (ArrayList <String> itemNames, ArrayList <String> itemAmounts) {
        nameOfItems = new String[itemNames.size()];
        amountOfItems = new String [itemAmounts.size()];

        for (int i = 0; i < nameOfItems.length; i++) {
            nameOfItems [i] = itemNames.get(i);
            Log.d ("blue", nameOfItems [i]);
        }

        for (int i = 0; i < amountOfItems.length; i++) {
            amountOfItems [i] = itemAmounts.get(i);
            Log.d ("blue", amountOfItems [i]);

        }

        Log.d ("red", "arrayListToArray has ran");
    }
}
