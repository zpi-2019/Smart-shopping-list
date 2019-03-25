package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.MyViewHolder> {
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView categoryName;
        TextView amount;
        TextView status;
        //Label

        MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.product_name);
            categoryName = itemView.findViewById(R.id.product_category);
            amount = itemView.findViewById(R.id.product_amount);
            status = itemView.findViewById(R.id.product_status);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basic_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
