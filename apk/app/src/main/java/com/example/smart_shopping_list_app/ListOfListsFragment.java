package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class ListOfListsFragment extends Fragment {
    AppViewModel appViewModel;
    MyListsRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public ListOfListsFragment() {
    }

    public static ListOfListsFragment newInstance() {
        ListOfListsFragment fragment = new ListOfListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_lists_fragment, container, false);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list_of_lists_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyListsRecyclerViewAdapter();
        adapter.setmValues(appViewModel.getAllUsersLists(StartActivity.currentUserID));
        recyclerView.setAdapter(adapter);
        addItemTouchHelper();
        FloatingActionButton fabAdd = view.findViewById(R.id.list_of_lists_button_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddListFragment nextFrag = AddListFragment.newInstance();
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

    public void addItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getActivity(), "Remove ", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                int id = adapter.removeItem(position);
                appViewModel.deleteListUser(id);
                appViewModel.deleteListItemByIDList(id);
                appViewModel.deleteList(id);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
