package com.example.smart_shopping_list_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class SingleList extends AppCompatActivity {
    RecyclerView productsList;
    BasicListAdapter productsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (productsListAdapter != null) {
            productsListAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (productsListAdapter != null) {
            productsListAdapter.restoreStates(savedInstanceState);
        }
    }

    public void initRecyclerView(){
        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        productsList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        productsListAdapter = new BasicListAdapter();
        productsList.setAdapter(productsListAdapter);
    }

}
