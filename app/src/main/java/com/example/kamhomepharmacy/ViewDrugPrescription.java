package com.example.kamhomepharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ViewDrugPrescription extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drug_prescription);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewDrugPrescription.this, 1);
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
        adapter = new MyAdapter(ViewDrugPrescription.this, dataList);
        recyclerView.setAdapter(adapter);
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