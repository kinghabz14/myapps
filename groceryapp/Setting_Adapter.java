package com.myapp.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Setting_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String [] categories;

    public Setting_Adapter (Context context, String [] categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.length;
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
                    convertView = layoutInflater.inflate(R.layout.category_view_layout, null);
        }

        TextView nameOfCategory = convertView.findViewById(R.id.category_name);

        nameOfCategory.setText(categories [position]);

        return convertView;


    }

}
