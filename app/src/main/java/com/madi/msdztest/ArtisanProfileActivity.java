package com.madi.msdztest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.madi.msdztest.login.Login;
import com.squareup.picasso.Picasso;

public class ArtisanProfileActivity extends AppCompatActivity {
    private TextView textViewFullName, textViewNom, textViewPrenom, textViewEmail, textViewNumeroTlf, textViewCategorie, ChangerMDP;
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
        ChangerMDP = findViewById(R.id.changer_mdp);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Artisans").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String imageUrl = value.getString("imageProfile");
                if (imageUrl != null) {
                    Picasso.get().load(imageUrl).into(imageView);
                }
                textViewFullName.setText(value.getString("Nom") + " " + value.getString("Prénom"));
                textViewNom.setText(value.getString("Nom"));
                textViewPrenom.setText(value.getString("Prénom"));
                textViewEmail.setText(value.getString("Email"));


                String phone = value.getString("Telephone");
                String formattedNumber = phone.replaceAll("(\\d{2})", "$1 ").trim();
                textViewNumeroTlf.setText(formattedNumber);
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
                showDialog();
            }
        });

//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(ArtisanProfileActivity.this, "Déconnecté!", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ArtisanProfileActivity.this, Login.class);
//                startActivity(intent);


        ChangerMDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPasswordChangeRequest();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention!");
        builder.setMessage("Êtes-vous sûr de vouloir vous déconnecter?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ArtisanProfileActivity.this, "Déconnecté!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ArtisanProfileActivity.this, Login.class);
                startActivity(intent);
            }


        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void sendPasswordChangeRequest () {
            // Get an instance of the FirebaseAuth object
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            // Send the password reset email to the user
            String emailAddress = currentUser.getEmail();
            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Password reset email sent successfully
                                Toast.makeText(getApplicationContext(), "E-mail de réinitialisation du mot de passe envoyé", Toast.LENGTH_SHORT).show();
                            } else {
                                // Failed to send password reset email
                                Toast.makeText(getApplicationContext(), "Échec de l'envoi de l'e-mail de réinitialisation du mot de passe", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }





