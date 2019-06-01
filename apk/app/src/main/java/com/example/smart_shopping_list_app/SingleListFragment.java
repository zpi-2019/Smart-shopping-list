package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

public class SingleListFragment extends Fragment {
    AppViewModel appViewModel;
    int listID;
    MySingleListRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public SingleListFragment() { }

    public static SingleListFragment newInstance(int listID) {
        SingleListFragment fragment = new SingleListFragment();
        fragment.listID = listID;
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
        View view = inflater.inflate(R.layout.single_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.single_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MySingleListRecyclerViewAdapter(appViewModel);
        adapter.setmValues(appViewModel.getAllProductsFromList(StartActivity.currentListID));
        recyclerView.setAdapter(adapter);
        initButton(view);
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

    private void initButton(View view) {
        FloatingActionButton fabAdd = view.findViewById(R.id.single_list_fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddListItemFragment nextFrag = AddListItemFragment.newInstance(adapter.getmValues());
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
