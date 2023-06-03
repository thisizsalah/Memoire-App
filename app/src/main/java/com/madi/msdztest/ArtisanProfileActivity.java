package com.madi.msdztest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.madi.msdztest.login.Login;
import com.squareup.picasso.Picasso;

public class ArtisanProfileActivity extends AppCompatActivity {
    private TextView textViewFullName, textViewNom, textViewPrenom, textViewEmail, textViewNumeroTlf, textViewCategorie;
    private String userId;
    private ImageView imageView;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private Button Logout, Modifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artisan_profile);

        textViewFullName = findViewById(R.id.full_name);
        textViewNom = findViewById(R.id.nom);
        textViewPrenom = findViewById(R.id.prenom);
        textViewEmail = findViewById(R.id.email);
        textViewNumeroTlf = findViewById(R.id.numTlf);
        textViewCategorie = findViewById(R.id.categorie);
        imageView = findViewById(R.id.profile_picture);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Artisans").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String imageUrl = value.getString("imgProfile");
                if (imageUrl != null) {
                    Picasso.get().load(imageUrl).into(imageView);
                }
                textViewFullName.setText(value.getString("Nom") +" " + value.getString("Prénom"));
                textViewNom.setText(value.getString("Nom"));
                textViewPrenom.setText(value.getString("Prénom"));
                textViewEmail.setText(value.getString("Email"));
                textViewNumeroTlf.setText(value.getString("Telephone"));
                textViewCategorie.setText(value.getString("Catégorie"));


            }
        });


        Modifier = findViewById(R.id.button_modifier);
        Logout = findViewById(R.id.buttonDeconnecter);
        Modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtisanProfileActivity.this, EditArtisan.class);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ArtisanProfileActivity.this, "Déconnecté!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ArtisanProfileActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }


}