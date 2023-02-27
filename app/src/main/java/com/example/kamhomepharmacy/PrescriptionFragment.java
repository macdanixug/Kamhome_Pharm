package com.example.kamhomepharmacy;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionFragment extends Fragment {
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PrescriptionFragment.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        androidData = new DataClass("Cortem", R.string.cortem1, "Shs. 5000", R.drawable.cortem1);
        dataList.add(androidData);
        androidData = new DataClass("Covidex", R.string.covidex, "Shs. 5000", R.drawable.covidex);
        dataList.add(androidData);
        androidData = new DataClass("Probeta", R.string.probeta, "Shs. 5000", R.drawable.probeta);
        dataList.add(androidData);
        androidData = new DataClass("Piliton", R.string.piliton, "Shs. 5000", R.drawable.piliton);
        dataList.add(androidData);
        androidData = new DataClass("Panadol", R.string.panadol1, "Shs. 5000", R.drawable.panadol1);
        dataList.add(androidData);
        androidData = new DataClass("Duocotexin", R.string.duocotexin, "Shs. 5000", R.drawable.duocotexin);
        dataList.add(androidData);
        androidData = new DataClass("Mebendazole", R.string.mebendazole, "Shs. 5000", R.drawable.mebendazole);
        dataList.add(androidData);
        adapter = new MyAdapter(PrescriptionFragment.this, dataList);
        recyclerView.setAdapter(adapter);


        return view;

    }
    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }
}