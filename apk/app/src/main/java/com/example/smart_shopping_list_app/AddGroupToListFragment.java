package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;


public class AddGroupToListFragment extends Fragment {
    RecyclerView recyclerView;
    AppViewModel appViewModel;
    MyAddGroupRecyclerViewAdapter adapter;

    public AddGroupToListFragment() { }

    public static AddGroupToListFragment newInstance() {
        return new AddGroupToListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_group_to_list, container, false);
        recyclerView = view.findViewById(R.id.add_group_to_list_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MyAddGroupRecyclerViewAdapter(appViewModel.selectAllGroups(), appViewModel);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.add_group_to_list_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleListFragment nextFrag = SingleListFragment.newInstance(StartActivity.currentListID);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
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
}
