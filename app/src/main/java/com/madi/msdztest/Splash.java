package com.madi.msdztest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madi.msdztest.main.MainActivity;
import com.madi.msdztest.managers.FirestoreManager;
import com.madi.msdztest.onboarding.OnBoarding;

public class Splash extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                        if (currentUser != null) {
                            FirestoreManager firestoreManager = new FirestoreManager();
                            firestoreManager.checkUserRole(currentUser.getUid(), new FirestoreManager.LoginCallback() {
                                @Override
                                public void userLogged() {
                                    Intent intent = new Intent(Splash.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void artisanLogged() {
                                    Intent intent = new Intent(Splash.this, ArtisanProfileActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }else{
                            Intent intent = new Intent(Splash.this, OnBoarding.class);
                            startActivity(intent);
                        }


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
    }
}