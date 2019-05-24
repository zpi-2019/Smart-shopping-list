package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Objects;


public class AddListFragment extends Fragment {
    AppViewModel appViewModel;

    public AddListFragment() {
    }

    public static AddListFragment newInstance() {
        return new AddListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_list, container, false);
        Button btCreateList = view.findViewById(R.id.add_list_button_add);
        final EditText etName = view.findViewById(R.id.add_list_editText_name);
        btCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appViewModel.insertNewList(new Lists(etName.getText().toString(), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));
                int listID = appViewModel.getListID(etName.getText().toString());
                StartActivity.currentListID = listID;
                appViewModel.insertNewListUser(new ListUser(listID, StartActivity.currentUserID));
                SingleListFragment nextFrag = SingleListFragment.newInstance(listID);
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
