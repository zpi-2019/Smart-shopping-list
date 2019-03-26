package com.example.smart_shopping_list_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AllLists extends AppCompatActivity {
    RecyclerView listOfLists;
    ListOfListsAdapter listOfListsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lists);
        initRecyclerView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (listOfListsAdapter != null) {
            listOfListsAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (listOfListsAdapter != null) {
            listOfListsAdapter.restoreStates(savedInstanceState);
        }
    }

    public void initRecyclerView(){
        listOfLists = findViewById(R.id.list_of_lists);
        listOfLists.setHasFixedSize(true);
        listOfLists.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listOfListsAdapter = new ListOfListsAdapter();
        listOfLists.setAdapter(listOfListsAdapter);
    }
}
