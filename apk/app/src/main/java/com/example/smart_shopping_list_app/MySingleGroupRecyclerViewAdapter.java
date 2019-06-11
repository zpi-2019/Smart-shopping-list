package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MySingleGroupRecyclerViewAdapter extends RecyclerView.Adapter<MySingleGroupRecyclerViewAdapter.ViewHolder> {

    private final List<GroupItem> mValues;
    AppViewModel appViewModel;

    MySingleGroupRecyclerViewAdapter(List<GroupItem> items, AppViewModel appViewModel) {
        mValues = items;
        this.appViewModel = appViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_group_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.IDGroupItem = mValues.get(position).IDGroupItem;
        holder.tvName.setText(mValues.get(position).ProductName);
        holder.tvAmount.setText(String.format("%s %s", mValues.get(position).Amount, mValues.get(position).Unit));
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mValues.get(holder.getAdapterPosition()).IDGroupItem;
                mValues.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                appViewModel.deleteSingleGroupItem(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues != null)
            return mValues.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int IDGroupItem;
        TextView tvName;
        TextView tvAmount;
        ConstraintLayout foreground;
        ConstraintLayout background;
        Button btDelete;

        ViewHolder(View view) {
            super(view);
            tvAmount = view.findViewById(R.id.group_product_amount);
            tvName = view.findViewById(R.id.group_product_name);
            foreground = view.findViewById(R.id.single_group_item_view_foreground);
            background = view.findViewById(R.id.single_group_item_view_background);
            btDelete = view.findViewById(R.id.single_group_item_imageButton_delete);
            foreground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
