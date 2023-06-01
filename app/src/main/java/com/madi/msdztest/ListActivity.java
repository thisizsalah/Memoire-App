package com.madi.msdztest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.madi.msdztest.managers.FirestoreManager;
import com.madi.msdztest.models.Artisan;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<Artisan> listData = new ArrayList<>();
    RecyclerView recyclerView;
    ArtisanListRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        adapter = new ArtisanListRecyclerViewAdapter( ListActivity.this,  listData);
        recyclerView.setAdapter(adapter);

        String category = getIntent().getStringExtra("category");

        setListData(category);
    }
    private void setListData(String category){
        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.getArtisansByCategory(category, new FirestoreManager.GetArtisansCallback() {
            @Override
            public void onSuccess(List<Artisan> artisans) {
                // Handle successful retrieval of artisans
                Log.i("artisans",artisans.toString());
                listData.addAll(artisans);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure
                e.printStackTrace();
            }
        });

    }
}