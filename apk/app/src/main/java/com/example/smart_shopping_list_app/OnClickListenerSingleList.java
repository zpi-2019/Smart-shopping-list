package com.example.smart_shopping_list_app;

import android.view.View;

public class OnClickListenerSingleList implements View.OnClickListener {
    private BasicListAdapter.MyViewHolder holder;
    private String text;
    private int color;
    private boolean isUndo;

    OnClickListenerSingleList(BasicListAdapter.MyViewHolder holder, String text, int color, boolean isUndo) {
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
            holder.swipeRevealLayout.setLockDrag(false);
        }
        else{
            holder.status.setText(text);
            holder.status.setTextColor(color);
            holder.status.setVisibility(View.VISIBLE);
            holder.btUndo.setVisibility(View.VISIBLE);
            holder.swipeRevealLayout.close(true);
            holder.swipeRevealLayout.setLockDrag(true);
        }
    }
}
