package com.madi.msdztest.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.madi.msdztest.R;
import com.madi.msdztest.login.Login;

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
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
}
}