package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;


public class AddGroupItemFragment extends Fragment {
    AppViewModel appViewModel;
    Button btAll;
    Button btAdd;
    EditText etName;
    EditText etAmount;
    Spinner spUnit;
    int currentGroupID;

    public AddGroupItemFragment() {

    }

    public static AddGroupItemFragment newInstance(int idGroup) {
        AddGroupItemFragment fragment = new AddGroupItemFragment();
        fragment.currentGroupID = idGroup;
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
        View view = inflater.inflate(R.layout.add_group_item_fragment, container, false);
        initButtons(view);
        initTexts(view);
        initSpinner(view);
        return view;
    }

    private void initButtons(View view) {
        btAll = view.findViewById(R.id.add_group_button_all);
        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleGroupFragment nextFrag = SingleGroupFragment.newInstance(currentGroupID);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        btAdd = view.findViewById(R.id.add_group_button_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etName.getText().toString().equals("") && !etAmount.getText().toString().equals("")) {
                    String name = etName.getText().toString();
                    double amount = Double.parseDouble(etAmount.getText().toString());
                    String unit = spUnit.getSelectedItem().toString();
                    appViewModel.insertNewGroupItem(new GroupItem(currentGroupID, name, amount, unit));
                    etAmount.setText("");
                    etName.setText("");
                    Toast.makeText(v.getContext(), name + " added.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initTexts(View view) {
        etName = view.findViewById(R.id.add_group_editText_name);
        etAmount = view.findViewById(R.id.add_group_editText_amount);
    }

    private void initSpinner(View view){
        spUnit = view.findViewById(R.id.add_group_spinner_unit);
        spUnit.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.spinner_item2, StartActivity.Unit.values()));
    }
}
