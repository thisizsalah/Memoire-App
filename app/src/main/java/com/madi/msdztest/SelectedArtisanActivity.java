package com.madi.msdztest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectedArtisanActivity extends AppCompatActivity {

    ImageView Imgprofile;
    TextView Nom, Catégorie, Descripton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_artisan);
    }
}