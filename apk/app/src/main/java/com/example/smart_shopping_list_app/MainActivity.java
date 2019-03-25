package com.example.smart_shopping_list_app;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btLastList;
    Button btAllLists;
    TextView tvHelloText;
    FloatingActionButton fbAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
        initViews("Cisz");
    }

    public void initButtons(){
        btAllLists = findViewById(R.id.all_lists);
        btLastList = findViewById(R.id.last_list);
        fbAddList = findViewById(R.id.add_list);

        btLastList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AllLists.class));
            }
        });

        btAllLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SingleList.class));
            }
        });
    }

    public void initViews(String user_name){
        tvHelloText = findViewById(R.id.hello_text);
        tvHelloText.setText(getString(R.string.Hello).concat(" " + user_name));
    }
}
