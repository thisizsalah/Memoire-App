package com.madi.msdztest.main;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.madi.msdztest.EditParticulier;
import com.madi.msdztest.R;
import com.madi.msdztest.login.Login;
import com.madi.msdztest.models.Particulier;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    private TextView textViewFullName, textViewNom, textViewPrenom, textViewEmail, textViewNumeroTlf, ChangerMDP;
    private ImageView imageView;
    private String userId;


    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        ChangerMDP = rootView.findViewById(R.id.changer_mdp);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Particuliers").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String imageUrl = value.getString("imageProfile");
                if (imageUrl != null) {
                    Picasso.get().load(imageUrl).into(imageView);
                }

                String name = value.getString("Nom");
                String prenom = value.getString("Prénom");
                String fullName = name + " " + prenom;

                textViewFullName.setText(fullName);
                textViewNom.setText(value.getString("Nom"));
                textViewPrenom.setText(value.getString("Prénom"));
                textViewEmail.setText(value.getString("Email"));
                String phone = value.getString("Telephone");
                String formattedNumber = phone.replaceAll("(\\d{2})", "$1 ").trim();
                textViewNumeroTlf.setText(formattedNumber);
            }
        });


        Modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditParticulier.class);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(getContext(), "Déconnecté!", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getContext(), Login.class);
//                startActivity(intent);
//            }
//        });
        if (ChangerMDP != null) {

            ChangerMDP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendPasswordChangeRequest();
                }
            });
        }

        return rootView;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Attention!");
        builder.setMessage("Êtes-vous sûr de vouloir vous déconnecter?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Déconnecté!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), Login.class);
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

    private void sendPasswordChangeRequest() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        String emailAddress = currentUser.getEmail();
        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password reset email sent successfully
                            Toast.makeText(getContext(), "E-mail de réinitialisation du mot de passe envoyé", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to send password reset email
                            Toast.makeText(getContext(), "Échec de l'envoi de l'e-mail de réinitialisation du mot de passe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}