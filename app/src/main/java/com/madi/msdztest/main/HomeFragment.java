package com.madi.msdztest.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madi.msdztest.ListActivity;
import com.madi.msdztest.R;
import com.madi.msdztest.models.Category;


public class HomeFragment extends Fragment {
    ImageView plombier, peintre, macon, electricien, soudeur ,install_prbl, reparation_toit, climatisation, demenagement;




    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        plombier = rootView.findViewById(R.id.ic_plombier);
        peintre = rootView.findViewById(R.id.ic_peintre);
        macon = rootView.findViewById(R.id.ic_maçon);
        electricien = rootView.findViewById(R.id.ic_electricien);
        soudeur = rootView.findViewById(R.id.ic_soudeur);
        install_prbl = rootView.findViewById(R.id.ic_parabole);
        reparation_toit = rootView.findViewById(R.id.ic_toit);
        climatisation = rootView.findViewById(R.id.ic_climat);
        demenagement = rootView.findViewById(R.id.ic_demenagement);


        plombier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_plombier);
                startActivity(intent);
            }
        });
        peintre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_peintre);
                startActivity(intent);
            }
        });
        macon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_maçon);
                startActivity(intent);
            }
        });
        electricien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_electricien);
                startActivity(intent);
            }
        });
        soudeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_soudeur);
                startActivity(intent);
            }
        });
        install_prbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_parabole);
                startActivity(intent);
            }
        });
        reparation_toit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_toit);
                startActivity(intent);
            }
        });
        climatisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_climat);
                startActivity(intent);
            }
        });
        demenagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra("category", Category.cat_demenagement);
                startActivity(intent);
            }
        });

    return rootView;
    }
}