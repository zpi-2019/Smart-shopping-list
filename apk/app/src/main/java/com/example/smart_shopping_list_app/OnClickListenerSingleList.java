package com.example.smart_shopping_list_app;

import android.view.View;

public class OnClickListenerSingleList implements View.OnClickListener {
    private MyListRecyclerViewAdapter.ViewHolder holder;
    private String text;
    private int color;
    private boolean isUndo;

    OnClickListenerSingleList(MyListRecyclerViewAdapter.ViewHolder holder, String text, int color, boolean isUndo) {
        this.holder = holder;
        this.text = text;
        this.color = color;
        this.isUndo = isUndo;
    }
    @Override
    public void onClick(View v) {
        if(isUndo) {
            holder.status.setVisibility(View.INVISIBLE);
            holder.btUndo.setVisibility(View.INVISIBLE);
        }
        else{
            holder.status.setText(text);
            holder.status.setTextColor(color);
            holder.status.setVisibility(View.VISIBLE);
            holder.btUndo.setVisibility(View.VISIBLE);
        }
    }
}
