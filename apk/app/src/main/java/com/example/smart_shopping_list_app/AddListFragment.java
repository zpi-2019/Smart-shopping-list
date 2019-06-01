package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    EditText etName;

    public AddListFragment() {
    }

    public static AddListFragment newInstance() {
        return new AddListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_list_fragment, container, false);
        etName = view.findViewById(R.id.add_list_editText_name);
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

    void initButton(View view) {
        Button btCreateList = view.findViewById(R.id.add_list_button_add);
        btCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText() != null) {
                    appViewModel.insertNewList(new Lists(etName.getText().toString(), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis(), 1));
                    int listID = appViewModel.getListID(etName.getText().toString());
                    StartActivity.currentListID = listID;
                    appViewModel.insertNewListUser(new ListUser(listID, StartActivity.currentUserID));
                    SingleListFragment nextFrag = SingleListFragment.newInstance(listID);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame1, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}
