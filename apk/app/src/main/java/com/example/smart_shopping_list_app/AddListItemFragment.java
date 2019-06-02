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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    List<ListItem> mValues;
    MyRecomRecyclerViewAdapter adapter;

    public AddListItemFragment() { }

    public static AddListItemFragment newInstance(List<ListItem> list) {
        AddListItemFragment fragment = new AddListItemFragment();
        fragment.mValues = list;
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
                    ListItem item = new ListItem(StartActivity.currentListID, name, StartActivity.Status.toBuy.toString(), amount, unit, color);
                    appViewModel.insertNewListItem(item);
                    etAmount.setText("");
                    etName.setText("");
                    mValues.add(item);
                    adapter.setmValues(calculateDistances());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.add_item_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        adapter = new MyRecomRecyclerViewAdapter(calculateDistances(), etName);
        recyclerView.setAdapter(adapter);
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

    private List<Recommendations> calculateDistances(){
        List<Integer> itemsNotInList = appViewModel.selectAllProductsID();
        List<Integer> itemsInList = new ArrayList<>();
        List<Distance> distances = appViewModel.selectAllDistances();
        List<Recommendations> recom = new ArrayList<>();
        for(ListItem item: mValues){
            int id = appViewModel.selectProductID(item.ProductName);
            if(id != 0) {
                itemsNotInList.remove(Integer.valueOf(id));
                itemsInList.add(id);
            }
        }

        for(int id: itemsNotInList){
            double sum = 0;
            for(Distance distance : distances){
                if(id == distance.IDProduct1 && itemsInList.contains(distance.IDProduct2)){
                    sum += distance.Distance;
                }
                if(id == distance.IDProduct2 && itemsInList.contains(distance.IDProduct1)){
                    sum += distance.Distance;
                }
            }
            recom.add(new Recommendations(appViewModel.selectProductName(id), sum));
        }
        Collections.sort(recom);
        return recom;
    }

    class Recommendations implements Comparable<Recommendations>{
        String name;
        double value;

        Recommendations(String name, double value) {
            this.name = name;
            this.value = value;
        }

        Double getValue(){
            return value;
        }

        @Override
        public int compareTo(@NonNull Recommendations o) {
            return this.getValue().compareTo(o.getValue());
        }
    }
}
