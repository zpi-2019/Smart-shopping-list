package com.example.smart_shopping_list_app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.smart_shopping_list_app.SingleListFragment.OnListFragmentInteractionListener;
import com.example.smart_shopping_list_app.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    MyItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_list_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));
        holder.tvItemName.setText("Jajko");
        holder.categoryName.setText("Nabiał");
        holder.amount.setText("Sztuk: 1");
        holder.btLack.setOnClickListener(new OnClickListenerSingleList(holder, "Brak", Color.RED, false));
        holder.btBuy.setOnClickListener(new OnClickListenerSingleList(holder, "Kupione", Color.GREEN, false));
        holder.btUndo.setOnClickListener(new OnClickListenerSingleList(holder, "", Color.BLACK, true));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView categoryName;
        TextView amount;
        TextView status;
        FrameLayout background;
        ConstraintLayout foreground;
        ImageButton btBuy;
        ImageButton btLack;
        Button btUndo;
        SwipeRevealLayout swipeRevealLayout;

        ViewHolder(View view) {
            super(view);
            tvItemName = itemView.findViewById(R.id.product_name);
            categoryName = itemView.findViewById(R.id.product_category);
            amount = itemView.findViewById(R.id.product_amount);
            status = itemView.findViewById(R.id.product_status);
            foreground = itemView.findViewById(R.id.view_foreground);
            background = itemView.findViewById(R.id.view_background);
            btBuy = itemView.findViewById(R.id.button_buy);
            btLack = itemView.findViewById(R.id.button_lack);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout);
            btUndo = itemView.findViewById(R.id.button_undo);
        }
    }

    void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }
}
