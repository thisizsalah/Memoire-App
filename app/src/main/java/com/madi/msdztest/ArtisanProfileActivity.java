package com.madi.msdztest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.madi.msdztest.login.Login;

public class ArtisanProfileActivity extends AppCompatActivity {
    private Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artisan_profile);

        Logout = findViewById(R.id.buttonDeconnecter);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ArtisanProfileActivity.this,"Déconnecter!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ArtisanProfileActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }
}