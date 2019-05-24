package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
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

public class ListOfGroupsFragment extends Fragment {
    RecyclerView recyclerView;
    AppViewModel appViewModel;
    MyGroupRecyclerViewAdapter adapter;

    public ListOfGroupsFragment() {
    }

    public static ListOfGroupsFragment newInstance() {
        return new ListOfGroupsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_groups_fragment, container, false);
        recyclerView = view.findViewById(R.id.list_of_groups_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MyGroupRecyclerViewAdapter(appViewModel.selectAllGroups());
        recyclerView.setAdapter(adapter);
        initButton(view);
        addItemTouchHelper();
        return view;
    }

    void initButton(View view) {
        FloatingActionButton fabAdd = view.findViewById(R.id.list_of_groups_button_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGroupFragment nextFrag = AddGroupFragment.newInstance();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void addItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getActivity(), "Removed ", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                int id = adapter.removeItem(position);
                appViewModel.deleteGroupItemByIDGroup(id);
                appViewModel.deleteGroup(id);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
