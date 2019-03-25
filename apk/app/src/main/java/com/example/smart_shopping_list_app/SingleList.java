package com.example.smart_shopping_list_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class SingleList extends AppCompatActivity {
    RecyclerView productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    public void initRecyclerView(){
        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        productsList.setAdapter(new BasicListAdapter());
    }

}
