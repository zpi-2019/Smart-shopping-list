package com.example.smart_shopping_list_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;


public class StartFragment extends Fragment {
    public StartFragment() {
    }

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_fragment, container, false);
        initButtons(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void initButtons(View view) {
        Button btLastList = view.findViewById(R.id.fragment_start_button_last_list);
        btLastList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleListFragment nextFrag = SingleListFragment.newInstance(StartActivity.currentListID);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button btListOfLists = view.findViewById(R.id.fragment_start_button_all_lists);
        btListOfLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListOfListsFragment nextFrag = ListOfListsFragment.newInstance();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        FloatingActionButton fabAddNewList = view.findViewById(R.id.fragment_start_button_add_list);
        fabAddNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddListFragment nextFrag = AddListFragment.newInstance();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
