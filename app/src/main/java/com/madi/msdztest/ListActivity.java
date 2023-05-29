package com.madi.msdztest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
 ArrayList<ListData> listData = new ArrayList<>();
 int[] listimages = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        setListData();

        ArtisanListRecyclerViewAdapter adapter = new ArtisanListRecyclerViewAdapter(this, listData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setListData(){
      String[] plombierNames = getResources().getStringArray(R.array.items_name);
      String[] plomierPlombier = getResources().getStringArray(R.array.items_cat);
      String[] plombierCity = getResources().getStringArray(R.array.items_city);
      for  (int i=0; i<plombierNames.length;i++){
          listData.add(new ListData(plombierNames[i],plomierPlombier[i],plombierCity[i],listimages[i] ));

      }
    }
}