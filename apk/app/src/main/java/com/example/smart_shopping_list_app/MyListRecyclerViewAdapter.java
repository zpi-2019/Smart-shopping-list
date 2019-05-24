package com.example.smart_shopping_list_app;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MyListRecyclerViewAdapter extends RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder> {

    private List<ListItem> mValues;

    MyListRecyclerViewAdapter() {
    }

    void setmValues(List<ListItem> list) {
        mValues = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_list_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvItemName.setText(mValues.get(position).ProductName);
        holder.amount.setText(String.format("%s %s", mValues.get(position).Amount, mValues.get(position).Unit));
        holder.btUndo.setOnClickListener(new OnClickListenerSingleList(holder, "", Color.BLACK, true));
    }

    @Override
    public int getItemCount() {
        if(mValues != null)
            return mValues.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView categoryName;
        TextView amount;
        TextView status;
        ConstraintLayout foreground;
        Button btUndo;

        ViewHolder(View view) {
            super(view);
            tvItemName = itemView.findViewById(R.id.product_name);
            categoryName = itemView.findViewById(R.id.product_category);
            amount = itemView.findViewById(R.id.product_amount);
            status = itemView.findViewById(R.id.product_status);
            foreground = itemView.findViewById(R.id.view_foreground);
            btUndo = itemView.findViewById(R.id.button_undo);
        }
    }
}
