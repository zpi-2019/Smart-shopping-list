package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MyRecomRecyclerViewAdapter extends RecyclerView.Adapter<MyRecomRecyclerViewAdapter.ViewHolder> {
    private List<AddListItemFragment.Recommendations> mValues;
    private EditText etName;

    MyRecomRecyclerViewAdapter(List<AddListItemFragment.Recommendations> list, EditText editText){
        mValues = list;
        etName = editText;
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
    public void onBindViewHolder(@NonNull final MyRecomRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.tvName.setText(mValues.get(position).name);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(mValues.get(holder.getAdapterPosition()).name);
                mValues.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
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
        ConstraintLayout layout;
        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.recommendation_item_layout);
            tvName = itemView.findViewById(R.id.recommendation_item_textView);
        }
    }
}
