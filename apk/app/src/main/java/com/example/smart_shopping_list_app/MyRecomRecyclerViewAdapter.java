package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecomRecyclerViewAdapter extends RecyclerView.Adapter<MyRecomRecyclerViewAdapter.ViewHolder> {
    private List<AddListItemFragment.Recommendations> mValues;

    MyRecomRecyclerViewAdapter(List<AddListItemFragment.Recommendations> list){
        mValues = list;
    }

    void setmValues(List<AddListItemFragment.Recommendations> list){
        mValues = list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyRecomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommendation_item, parent, false);
        return new MyRecomRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecomRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(mValues.get(position).name);
    }

    @Override
    public int getItemCount() {
        if(mValues.size() <= 6)
            return mValues.size();
        else
            return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.recommendation_item_textView);
        }
    }
}
