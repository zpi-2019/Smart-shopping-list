package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
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

public class AddGroupFragment extends Fragment {
    AppViewModel appViewModel;
    EditText etName;

    public AddGroupFragment() {
    }

    public static AddGroupFragment newInstance() {
        return new AddGroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_group_fragment, container, false);
        etName = view.findViewById(R.id.add_group_editText_name);
        initButton(view);
        return view;
    }

    void initButton(View view) {
        Button btAdd = view.findViewById(R.id.add_group_button_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etName.getText().toString().equals("")) {
                    String name = etName.getText().toString();
                    appViewModel.insertNewGroup(new Group(name));
                    int id = appViewModel.selectGroupID(name);
                    SingleGroupFragment nextFrag = SingleGroupFragment.newInstance(id);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame1, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}
