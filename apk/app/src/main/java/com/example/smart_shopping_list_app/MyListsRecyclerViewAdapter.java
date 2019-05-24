package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import java.util.Objects;


public class MyListsRecyclerViewAdapter extends RecyclerView.Adapter<MyListsRecyclerViewAdapter.ViewHolder> {

    private List<ListUserAndList> mValues;

    MyListsRecyclerViewAdapter() { }

    void setmValues(List<ListUserAndList> list) {
        mValues = list;
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
        holder.listID = mValues.get(holder.getAdapterPosition()).listUser.IDList;
        holder.tvListTitle.setText(String.valueOf(mValues.get(holder.getAdapterPosition()).userList.get(0).Name));
        holder.tvCounter.setText(String.valueOf(holder.listID));
    }

    @Override
    public int getItemCount() {
        if (mValues != null) {
            return mValues.size();
        }
        else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListTitle;
        TextView tvCounter;
        int listID;

        ViewHolder(final View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartActivity.currentListID = listID;
                    if(view.getContext() instanceof StartActivity) {
                        SingleListFragment nextFrag = SingleListFragment.newInstance(listID);
                        Objects.requireNonNull((StartActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame1, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
            tvListTitle = itemView.findViewById(R.id.list_title);
            tvCounter = itemView.findViewById(R.id.counter);
        }
    }

    int removeItem(int position) {
        int id = mValues.get(position).userList.get(0).IDList;
        mValues.remove(position);
        notifyItemRemoved(position);
        return id;
    }
}
