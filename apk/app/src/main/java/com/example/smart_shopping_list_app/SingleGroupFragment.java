package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;


public class SingleGroupFragment extends Fragment {
    RecyclerView recyclerView;
    int currentGroupID;
    AppViewModel appViewModel;
    MySingleGroupRecyclerViewAdapter adapter;

    public SingleGroupFragment() {
    }

    public static SingleGroupFragment newInstance(int id) {
        SingleGroupFragment fragment = new SingleGroupFragment();
        fragment.currentGroupID = id;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_group_fragment, container, false);
        recyclerView = view.findViewById(R.id.single_group_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new MySingleGroupRecyclerViewAdapter(appViewModel.selectAllGroupItemFromGroup(currentGroupID), appViewModel);
        recyclerView.setAdapter(adapter);
        initButton(view);
        return view;
    }

    private void initButton(View view) {
        FloatingActionButton fabAdd = view.findViewById(R.id.single_group_fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGroupItemFragment nextFrag = AddGroupItemFragment.newInstance(currentGroupID);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
