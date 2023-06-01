package com.madi.msdztest.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madi.msdztest.ArtisanProfileActivity;
import com.madi.msdztest.R;
import com.madi.msdztest.login.Login;
import com.madi.msdztest.main.MainActivity;
import com.madi.msdztest.managers.FirestoreManager;

public class OnBoarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        Button btn = findViewById(R.id.button_commencer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }
    public void openLogin() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            FirestoreManager firestoreManager = new FirestoreManager();
            firestoreManager.checkUserRole(currentUser.getUid(), new FirestoreManager.LoginCallback() {
                @Override
                public void userLogged() {
                    Intent intent = new Intent(OnBoarding.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void artisanLogged() {
                    Intent intent = new Intent(OnBoarding.this, ArtisanProfileActivity.class);
                    startActivity(intent);
                }
            });

        }else{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
}