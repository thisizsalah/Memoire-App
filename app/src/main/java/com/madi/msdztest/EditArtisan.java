package com.madi.msdztest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.madi.msdztest.login.Login;
import com.madi.msdztest.managers.FirebaseStorageManager;
import com.madi.msdztest.managers.FirestoreManager;
import com.madi.msdztest.models.Artisan;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditArtisan extends AppCompatActivity {
    private EditText ArtisanNom, ArtisanPrenom, ArtisanEmail, ArtisanTelphone, ArtisanDescription;
    Button Upload_image, Save,Delete;
    Uri imgProfile;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    FirebaseUser currentUser = auth.getCurrentUser();

    private final String userId = currentUser.getUid();
    ImageView imageView;
    private static final int REQUEST_IMAGE_PICK = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_artisan);


        ArtisanNom = findViewById(R.id.EditArtisanNom);
        ArtisanPrenom = findViewById(R.id.EditArtisanPrenom);
        ArtisanEmail = findViewById(R.id.EditArtisanEmail);
        ArtisanTelphone = findViewById(R.id.EditArtisanNumeroTlf);
        ArtisanDescription = findViewById(R.id.EditArtisanDescription);
        imageView = findViewById(R.id.EditImageView);
        Upload_image =findViewById(R.id.upload_button);
        Save = findViewById(R.id.Button_Sauvegarder);
        Delete =findViewById(R.id.buttonDeleteProfile);

        Upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_IMAGE_PICK);
            }
        });

        FirestoreManager firestoreManager = new FirestoreManager();
        firestoreManager.getArtisansByID(userId, new FirestoreManager.GetArtisanByIdCallback() {
            @Override
            public void onSuccess(Artisan artisan) {
                if (artisan.getImageProfile() != null) {

                Uri uri = Uri.parse(artisan.getImageProfile());
                if (uri != null) {
                    Glide.with(EditArtisan.this).load(uri).into(imageView);
                }
            }
                ArtisanNom.setText(artisan.getNom());
                ArtisanPrenom.setText(artisan.getPrénom());
                ArtisanEmail.setText(artisan.getEmail());
                ArtisanTelphone.setText(artisan.getTelephone());
                ArtisanDescription.setText(artisan.getDescription());

            }

            @Override
            public void onFailure(Exception e) {

            }

        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String textNom = ArtisanNom.getText().toString();
                String textPrenom = ArtisanPrenom.getText().toString();
                String textEmail = ArtisanEmail.getText().toString();
                String textTelephone = ArtisanTelphone.getText().toString();
                String textDescription = ArtisanDescription.getText().toString();



                String NumeroX = "[0][5-7][0-9]{8}";
                Matcher NumeroMatcher;
                Pattern NumeroPattern = Pattern.compile(NumeroX);
                NumeroMatcher = NumeroPattern.matcher(textTelephone);



                if (TextUtils.isEmpty(textNom)) {
                    Toast.makeText(EditArtisan.this, "Veuillez entrer votre Nom !", Toast.LENGTH_SHORT).show();
                    ArtisanNom.setError("Nom est obligatoire");
                    ArtisanNom.requestFocus();
                } else if (TextUtils.isEmpty(textPrenom)) {
                    Toast.makeText(EditArtisan.this, "Veuillez entrer votre Prénom !", Toast.LENGTH_SHORT).show();
                    ArtisanPrenom.setError("Prénom est obligatoire");
                    ArtisanPrenom.requestFocus();


                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(EditArtisan.this, "Veuillez entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ArtisanEmail.setError("E-mail est obligatoire");
                    ArtisanEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(EditArtisan.this, "Veuillez re-entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ArtisanEmail.setError("Un email valide est requis");
                    ArtisanEmail.requestFocus();
                } else if (TextUtils.isEmpty(textTelephone)) {
                    Toast.makeText(EditArtisan.this, "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ArtisanTelphone.setError("Numéro de téléphone est obligatoire");
                    ArtisanTelphone.requestFocus();
                } else if (ArtisanTelphone.length() != 10) {
                    Toast.makeText(EditArtisan.this, "Veuillez re-entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ArtisanTelphone.setError("Numéro de téléphone doit comporter 10 chiffres");
                    ArtisanTelphone.requestFocus();

                } else if (!NumeroMatcher.find()) {
                    Toast.makeText(EditArtisan.this, "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ArtisanTelphone.setError("Numéro de téléphone invalide");
                    ArtisanTelphone.requestFocus();

                } else if (TextUtils.isEmpty(textDescription)) {
                    Toast.makeText(EditArtisan.this, "Veuillez entrer une descripton de votre travail !", Toast.LENGTH_SHORT).show();
                    ArtisanDescription.setError("description est obligatoire");
                    ArtisanDescription.requestFocus();
                } else if (ArtisanDescription.length() < 30) {
                    ArtisanDescription.setError("Votre description est trop courte");
                    ArtisanDescription.requestFocus();
                } else {

                     updatedArtisan(textNom,textPrenom, textEmail, textTelephone, textDescription);
                    FirebaseStorageManager storageManager = new FirebaseStorageManager();
                    storageManager.uploadProfileImage(imgProfile, new FirebaseStorageManager.UploadCallback() {
                        @Override
                        public void onUploadProgress(int progress) {

                        }

                        @Override
                        public void onUploadComplete() {
                            Log.d(TAG, "onUploadComplete: uploaded image");

                        }

                        @Override
                        public void onUploadFailure(Exception e) {

                        }
                    });



                }


            }
        });








    }

    private void updatedArtisan(String textNom, String textPrenom, String textEmail, String textTelephone, String textDescription) {
        CollectionReference usersCollection = db.collection("Artisans");
        DocumentReference userDocument = usersCollection.document(userId);

        Map<String, Object> artisan = new HashMap<>();
        artisan.put("Nom",textNom);
        artisan.put("Prénom", textPrenom);
        artisan.put("Telephone", textTelephone);
        artisan.put("Email", textEmail);
        artisan.put("Description", textDescription);

        userDocument.update(artisan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: updated");
                Toast.makeText(EditArtisan.this,"Profile est à jour",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditArtisan.this, ArtisanProfileActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ",e);
            }
        });

                }

    public void DeleteProfile(View view) {
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention!");
        builder.setMessage("Êtes-vous sûr de supprimer le compte");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CollectionReference usersCollection = db.collection("Artisans");
                DocumentReference userDocument = usersCollection.document(userId);
                userDocument.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: deleted artisan");
                        Toast.makeText(EditArtisan.this,"Votre Compte est supprimé !",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditArtisan.this, Login.class);
                        startActivity(intent);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: ",e);

                            }
                        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
              imgProfile = data.getData();
            imageView.setImageURI(imgProfile);

        }


    }




}