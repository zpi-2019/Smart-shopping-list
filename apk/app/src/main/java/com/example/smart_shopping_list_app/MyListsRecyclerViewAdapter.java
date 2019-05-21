package com.example.smart_shopping_list_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.List;


public class MyListsRecyclerViewAdapter extends RecyclerView.Adapter<MyListsRecyclerViewAdapter.ViewHolder> {

    private final List<Integer> mValues;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    MyListsRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_lists_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));
        holder.tvListTitle.setText("Nowa lista" + position);
        holder.tvCounter.setText("0/24");
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        TextView tvListTitle;
        TextView tvCounter;
        ImageButton btDelete;

        ViewHolder(View view) {
            super(view);
            tvListTitle = itemView.findViewById(R.id.list_title);
            tvCounter = itemView.findViewById(R.id.counter);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout_2);
            btDelete = itemView.findViewById(R.id.button_delete);
        }
    }

    void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }}
