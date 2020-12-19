package com.myapp.groceryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.io.File;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.util.Log;

public class GroceryAdapter extends RecyclerView.Adapter <GroceryAdapter.ViewHolder> {

        private Context context;
        private Cursor cursor;
        private OnItemClickListener listener;
        public File file;
        public String info_text;

        public GroceryAdapter (Context context, Cursor cursor) {
            this.context = context;
            this.cursor = cursor;

            Log.d ("red", "GroceryAdapter constructor has ran");
        }

        public GroceryAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            CardView cv = (CardView) inflater.inflate(R.layout.cardview, parent, false);

            Log.d ("red", "onCreateViewHolder has ran");

            return new ViewHolder (cv, listener);

        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!cursor.moveToPosition(position)) {
                return;
            }

            info_text = cursor.getString (0);
            holder.textView.setText(info_text);


            String imagePath = cursor.getString(1);
            Log.d ("red", imagePath);
            file = new File (imagePath);

            if (file.exists()) {
                Glide.with(context).load (file).into(holder.imageView);
            }

            Log.d ("red", "onBindViewHolder has ran");

        }

        public int getItemCount() {

            Log.d ("red", "getItemCount has ran");

            return cursor.getCount();

        }

        public void setOnItemClickListener (OnItemClickListener listener) {
            this.listener = listener;

            Log.d ("red", "setOnItemClickListener has ran");
        }

        public interface OnItemClickListener {
            void onItemClick (int position);


            void setFileName (int position);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;

            public ViewHolder(View view, final OnItemClickListener listener) {
                super(view);

                imageView = view.findViewById(R.id.info_image);
                textView = view.findViewById(R.id.info_text);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAbsoluteAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.setFileName(position);
                                listener.onItemClick(position);
                            }
                        }
                    }
                });

                Log.d ("red", "viewHolder constructor has ran");

            }

        }

    }

