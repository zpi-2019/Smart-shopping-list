package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONObject;

import java.util.Objects;

public class SingleListFragment extends Fragment {
    AppViewModel appViewModel;
    int listID;
    MySingleListRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    Toolbar toolbar;

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
        toolbar = view.findViewById(R.id.single_list_toolbar);
        recyclerView = view.findViewById(R.id.single_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MySingleListRecyclerViewAdapter(appViewModel);
        adapter.setmValues(appViewModel.getAllProductsFromList(StartActivity.currentListID));
        recyclerView.setAdapter(adapter);
        ((StartActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        initButton(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.start, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        JSONObject json = JSONOperations.writeList(appViewModel.getAllProductsFromList(StartActivity.currentListID), task.getResult().getToken());
                        new API.PushNewListsUpdate().execute(json.toString());
                    }
                });
                return true;
            case R.id.action_share:
                return true;
            case R.id.action_use_again:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        fabAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AddGroupToListFragment nextFrag = AddGroupToListFragment.newInstance();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        });
    }
}
