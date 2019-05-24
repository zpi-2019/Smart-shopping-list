package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;


public class MyGroupRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupRecyclerViewAdapter.ViewHolder> {

    private final List<Group> mValues;

    MyGroupRecyclerViewAdapter(List<Group> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_groups_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.GroupID = mValues.get(position).IDGroup;
        holder.tvName.setText(mValues.get(position).Name);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        int GroupID = 0;

        ViewHolder(final View view) {
            super(view);
            tvName = view.findViewById(R.id.list_of_groups_item_textView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(view.getContext() instanceof StartActivity) {
                        SingleGroupFragment nextFrag = SingleGroupFragment.newInstance(GroupID);
                        Objects.requireNonNull((StartActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame1, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
    }

    int removeItem(int position) {
        int id = mValues.get(position).IDGroup;
        mValues.remove(position);
        notifyItemRemoved(position);
        return id;
    }
}
