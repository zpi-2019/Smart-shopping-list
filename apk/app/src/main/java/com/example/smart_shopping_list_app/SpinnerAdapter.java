package com.example.smart_shopping_list_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

class SpinnerAdapter extends BaseAdapter
{
    private ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(R.drawable.black_shape, R.drawable.blue_shape, R.drawable.green_shape, R.drawable.yellow_shape, R.drawable.pink_shape, R.drawable.white_shape));
    private ArrayList<String> names = new ArrayList<>(Arrays.asList("Black", "Blue", "Green", "Yellow", "Pink", "White"));
    Context context;

    SpinnerAdapter(@NonNull Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount()
    {
        return colors.size();
    }
    @Override
    public Object getItem(int arg0)
    {
        return names.get(arg0);
    }
    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent)
    {
        View itemView = view;
        ViewHolder holder;

        if(view == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
            holder = new ViewHolder();
            holder.image = itemView.findViewById(R.id.spinner_image);
            itemView.setTag(holder);
        } else{
            holder = (ViewHolder) itemView.getTag();
        }
        holder.image.setImageResource(colors.get(pos));

        return itemView;
    }

    static class ViewHolder{
        ImageView image;
    }
}
