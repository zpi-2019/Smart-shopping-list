package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;


public class MyGroupRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupRecyclerViewAdapter.ViewHolder> {

    private final List<Group> mValues;
    AppViewModel appViewModel;

    MyGroupRecyclerViewAdapter(List<Group> items, AppViewModel appViewModel) {
        mValues = items;
        this.appViewModel = appViewModel;
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
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mValues.get(holder.getAdapterPosition()).IDGroup;
                mValues.remove(holder.getAdapterPosition());
                appViewModel.deleteGroupItemByIDGroup(id);
                appViewModel.deleteGroup(id);
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
        ConstraintLayout foreground;
        ConstraintLayout background;
        ImageButton btDelete;

        ViewHolder(final View view) {
            super(view);
            tvName = view.findViewById(R.id.list_of_groups_item_textView);
            foreground = view.findViewById(R.id.list_of_groups_item_view_foreground);
            background = view.findViewById(R.id.list_of_groups_item_view_background);
            btDelete = view.findViewById(R.id.list_of_groups_item_imageButton_delete);
            foreground.setOnClickListener(new View.OnClickListener() {
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
}
