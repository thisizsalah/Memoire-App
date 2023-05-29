package com.madi.msdztest.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.madi.msdztest.ArtisanProfileActivity;
import com.madi.msdztest.R;
import com.madi.msdztest.login.Login;


public class ProfileFragment extends Fragment {

    private Button Logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Logout = rootView.findViewById(R.id.buttonDeconnecter);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(),"Déconnecté!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}