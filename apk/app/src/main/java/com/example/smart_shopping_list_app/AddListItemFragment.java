package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Objects;


public class AddListItemFragment extends Fragment {
    AppViewModel appViewModel;
    RecyclerView recyclerView;
    Button btAll;
    Button btAdd;
    EditText etName;
    EditText etAmount;
    Spinner spUnit;
    Spinner spColor;

    public AddListItemFragment() { }

    public static AddListItemFragment newInstance() {
        return new AddListItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_fragment, container, false);
        initButtons(view);
        initTexts(view);
        initRecyclerView(view);
        initSpinner(view);
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

    private void initButtons(View view) {
        btAll = view.findViewById(R.id.add_item_button_all);
        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleListFragment nextFrag = SingleListFragment.newInstance(StartActivity.currentListID);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        btAdd = view.findViewById(R.id.add_item_button_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etAmount.getText().toString().equals("") && !etAmount.getText().toString().equals("")) {
                    String name = etName.getText().toString();
                    double amount = Double.parseDouble(etAmount.getText().toString());
                    String unit = spUnit.getSelectedItem().toString();
                    String color = spColor.getSelectedItem().toString();
                    int productID = appViewModel.selectProductID(name);
                    if (productID != 0)
                        appViewModel.insertNewListItem(new ListItem(StartActivity.currentListID, name, StartActivity.Status.toBuy.toString(), amount, unit, color));
                    else {
                        appViewModel.insertNewListItem(new ListItem(StartActivity.currentListID, name, StartActivity.Status.toBuy.toString(), amount, unit, color));
                    }
                    etAmount.setText("");
                    etName.setText("");
                    //reload recycleView data
                }
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.add_item_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
    }

    private void initTexts(View view) {
        etName = view.findViewById(R.id.add_item_editText_name);
        etAmount = view.findViewById(R.id.add_item_editText_amount);
    }

    private void initSpinner(View view){
        spUnit = view.findViewById(R.id.add_item_spinner_unit);
        spUnit.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, StartActivity.Unit.values()));
        spColor = view.findViewById(R.id.add_item_spinner_color);
        spColor.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, StartActivity.GroupColors.values()));
    }
}
