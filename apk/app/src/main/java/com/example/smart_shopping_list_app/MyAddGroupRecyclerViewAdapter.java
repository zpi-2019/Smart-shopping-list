package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAddGroupRecyclerViewAdapter extends RecyclerView.Adapter<MyAddGroupRecyclerViewAdapter.ViewHolder>{
    private final List<Group> mValues;
    AppViewModel appViewModel;

    MyAddGroupRecyclerViewAdapter(List<Group> items, AppViewModel appViewModel) {
        mValues = items;
        this.appViewModel = appViewModel;
    }

    @NonNull
    @Override
    public MyAddGroupRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_group_to_list_item, parent, false);
        return new MyAddGroupRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAddGroupRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.GroupID = mValues.get(position).IDGroup;
        holder.tvName.setText(mValues.get(position).Name);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GroupItem> list = appViewModel.selectAllGroupItemFromGroup(holder.GroupID);
                for(GroupItem item : list){
                    appViewModel.insertNewListItem(new ListItem(StartActivity.currentListID, item.ProductName, StartActivity.Status.toBuy.toString(), item.Amount, item.Unit, ""));
                }
                mValues.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        int GroupID = 0;
        ConstraintLayout layout;

        ViewHolder(final View view) {
            super(view);
            tvName = view.findViewById(R.id.add_group_to_list_item_textView);
            layout = view.findViewById(R.id.add_group_to_list_item_view_foreground);
        }
    }
}
