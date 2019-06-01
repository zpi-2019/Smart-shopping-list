package com.example.smart_shopping_list_app;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MySingleListRecyclerViewAdapter extends RecyclerView.Adapter<MySingleListRecyclerViewAdapter.ViewHolder> {

    private List<ListItem> mValues;
    private AppViewModel appViewModel;

    MySingleListRecyclerViewAdapter(AppViewModel appViewModel) {
        this.appViewModel = appViewModel;
    }

    void setmValues(List<ListItem> list) {
        mValues = list;
        notifyDataSetChanged();
    }

    List<ListItem> getmValues() {
        return mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_list_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ListItem item =  mValues.get(position);
        holder.idListItem = item.IDListItem;
        holder.tvItemName.setText(item.ProductName);
        holder.tvAmount.setText(String.format("%s %s", item.Amount, item.Unit));
        if(item.Status.equals(StartActivity.Status.bought.toString()))
            holder.ivStatus.setImageResource(R.drawable.green_shape);
        else if(item.Status.equals(StartActivity.Status.lack.toString()))
            holder.ivStatus.setImageResource(R.drawable.yellow_shape);
        holder.btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValues.get(holder.getAdapterPosition()).Status = StartActivity.Status.bought.toString();
                holder.ivStatus.setImageResource(R.drawable.green_shape);
                appViewModel.updateListItem(mValues.get(holder.getAdapterPosition()));
            }
        });
        holder.btLack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValues.get(holder.getAdapterPosition()).Status = StartActivity.Status.lack.toString();
                holder.ivStatus.setImageResource(R.drawable.yellow_shape);
                appViewModel.updateListItem(mValues.get(holder.getAdapterPosition()));
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mValues.get(holder.getAdapterPosition()).IDListItem;
                mValues.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                appViewModel.deleteSingleListItem(index);
            }
        });
        holder.ivColor.setImageResource(getShape(item.color));
    }

    @Override
    public int getItemCount() {
        if(mValues != null)
            return mValues.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvAmount;
        ConstraintLayout foreground;
        ConstraintLayout background;
        ImageButton btBuy;
        ImageButton btDelete;
        ImageButton btLack;
        ImageView ivStatus;
        ImageView ivColor;
        int idListItem;

        ViewHolder(View view) {
            super(view);
            tvItemName = itemView.findViewById(R.id.single_list_item_product_name);
            tvAmount = itemView.findViewById(R.id.single_list_item_product_amount);
            foreground = itemView.findViewById(R.id.single_list_item_view_foreground);
            background = itemView.findViewById(R.id.single_list_item_view_background);
            btBuy = itemView.findViewById(R.id.single_list_item_imageButton_buy);
            btDelete = itemView.findViewById(R.id.single_list_item_imageButton_delete);
            btLack = itemView.findViewById(R.id.single_list_item_imageButton_lack);
            ivStatus = itemView.findViewById(R.id.single_list_item_imageView_status);
            ivColor = itemView.findViewById(R.id.single_list_item_imageView_color);
            ivColor.setImageResource(R.drawable.white_shape);
            ivStatus.setImageResource(R.drawable.white_shape);
        }
    }

    private int getShape(String color){
        int shape = R.drawable.white_shape;
        if(color.equals(StartActivity.GroupColors.Black.toString()))
            shape = R.drawable.black_shape;
        if(color.equals(StartActivity.GroupColors.Blue.toString()))
            shape = R.drawable.blue_shape;
        if(color.equals(StartActivity.GroupColors.Red.toString()))
            shape = R.drawable.red_shape;
        if(color.equals(StartActivity.GroupColors.Pink.toString()))
            shape = R.drawable.pink_shape;
        if(color.equals(StartActivity.GroupColors.Green.toString()))
            shape = R.drawable.green_shape;
        return shape;
    }
}
