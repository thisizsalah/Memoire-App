package com.madi.msdztest.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.madi.msdztest.R;
import com.madi.msdztest.login.Login;


public class ProfileFragment extends Fragment {
    private TextView textViewFullName, textViewNom, textViewPrenom, textViewEmail, textViewNumeroTlf;
    private ImageView imageView;
    private String userId;


    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private Button Logout, Modifier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
         Modifier = rootView.findViewById(R.id.button_modifier);
         Logout = rootView.findViewById(R.id.buttonDeconnecter);

        textViewFullName = rootView.findViewById(R.id.full_name);
        textViewNom = rootView.findViewById(R.id.nom);
        textViewPrenom = rootView.findViewById(R.id.prenom);
        textViewEmail = rootView.findViewById(R.id.email);
        textViewNumeroTlf = rootView.findViewById(R.id.numTlf);
        imageView = rootView.findViewById(R.id.profile_picture);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Particuliers").document(userId);
        documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String name = value.getString("Nom");
                String prenom = value.getString("Prénom");
                String fullName = name + " " + prenom;

                textViewFullName.setText(fullName);
                textViewNom.setText(value.getString("Nom"));
                textViewPrenom.setText(value.getString("Prénom"));
                textViewEmail.setText(value.getString("Email"));
                textViewNumeroTlf.setText(value.getString("Numero de téléphone"));
            }
        });






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