package com.madi.msdztest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.madi.msdztest.databinding.ActivityMainBinding;

public class  MainActivity extends AppCompatActivity {
 ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragement(new Fragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.Acceuil:
                    replaceFragement(new Fragment() );
                    break;
                case R.id.Messages:
                    replaceFragement(new MessagesFragment());
                    break;
                case R.id.Paramètres:
                    replaceFragement(new SettingsFragment());
                    break;

            }

            return true;
        });
    }
    private void replaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainConstraint,fragment);
        fragmentTransaction.commit();
    }
}