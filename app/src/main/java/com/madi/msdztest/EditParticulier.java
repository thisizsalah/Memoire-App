package com.madi.msdztest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.madi.msdztest.main.MainActivity;
import com.madi.msdztest.main.ProfileFragment;
import com.madi.msdztest.managers.FirebaseStorageManager;
import com.madi.msdztest.managers.FirestoreManager;
import com.madi.msdztest.models.Particulier;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditParticulier extends AppCompatActivity {
    private EditText ParticulierNom, ParticulierPrenom, ParticulierEmail, ParticulierTelphone;
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
        setContentView(R.layout.activity_edit_particulier);

        ParticulierNom = findViewById(R.id.EditParticulierNom);
        ParticulierPrenom = findViewById(R.id.EditParticulierPrenom);
        ParticulierEmail = findViewById(R.id.EditParticulierEmail);
        ParticulierTelphone = findViewById(R.id.EditParticulierNumeroTlf);
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
        firestoreManager.getParticuliersByID(userId, new FirestoreManager.GetParticulierByIdCallback() {
            @Override
            public void onSuccess(Particulier particulier) {
                if (particulier.getImageProfile() != null) {

                Uri uri = Uri.parse(particulier.getImageProfile());
                if (uri != null) {
                    Glide.with(EditParticulier.this).load(uri).into(imageView);
                }
            }
                ParticulierNom.setText(particulier.getNom());
                ParticulierPrenom.setText(particulier.getPrénom());
                ParticulierEmail.setText(particulier.getEmail());
                ParticulierTelphone.setText(particulier.getTelephone());
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "onFailure: failed edit ",e);

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String textNom = ParticulierNom.getText().toString();
                String textPrenom = ParticulierPrenom.getText().toString();
                String textEmail = ParticulierEmail.getText().toString();
                String textTelephone = ParticulierTelphone.getText().toString();


                String NumeroX = "[0][5-7][0-9]{8}";
                Matcher NumeroMatcher;
                Pattern NumeroPattern = Pattern.compile(NumeroX);
                NumeroMatcher = NumeroPattern.matcher(textTelephone);



                if (TextUtils.isEmpty(textNom)) {
                    Toast.makeText(EditParticulier.this, "Veuillez entrer votre Nom !", Toast.LENGTH_SHORT).show();
                    ParticulierNom.setError("Nom est obligatoire");
                    ParticulierNom.requestFocus();
                } else if (TextUtils.isEmpty(textPrenom)) {
                    Toast.makeText(EditParticulier.this, "Veuillez entrer votre Prénom !", Toast.LENGTH_SHORT).show();
                    ParticulierPrenom.setError("Prénom est obligatoire");
                    ParticulierPrenom.requestFocus();


                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(EditParticulier.this, "Veuillez entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ParticulierEmail.setError("E-mail est obligatoire");
                    ParticulierEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(EditParticulier.this, "Veuillez re-entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ParticulierEmail.setError("Un email valide est requis");
                    ParticulierEmail.requestFocus();
                } else if (TextUtils.isEmpty(textTelephone)) {
                    Toast.makeText(EditParticulier.this, "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ParticulierTelphone.setError("Numéro de téléphone est obligatoire");
                    ParticulierTelphone.requestFocus();
                } else if (ParticulierTelphone.length() != 10) {
                    Toast.makeText(EditParticulier.this, "Veuillez re-entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ParticulierTelphone.setError("Numéro de téléphone doit comporter 10 chiffres");
                    ParticulierTelphone.requestFocus();

                } else if (!NumeroMatcher.find()) {
                    Toast.makeText(EditParticulier.this, "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ParticulierTelphone.setError("Numéro de téléphone invalide");
                    ParticulierTelphone.requestFocus();

                }  else {

                    updatedParticulier(textNom, textPrenom, textEmail, textTelephone);
                    if (imgProfile != null) {

                    FirebaseStorageManager firebaseStorageManager = new FirebaseStorageManager();
                    firebaseStorageManager.uploadImageParticulier(imgProfile, new FirebaseStorageManager.UploadCallback() {
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


            }
        });
    }

    private void updatedParticulier(String textNom, String textPrenom, String textEmail, String textTelephone) {

        CollectionReference usersCollection = db.collection("Particuliers");
        DocumentReference userDocument = usersCollection.document(userId);

        Map<String, Object> particulier = new HashMap<>();
        particulier.put("Nom",textNom);
        particulier.put("Prénom", textPrenom);
        particulier.put("Telephone", textTelephone);
        particulier.put("Email", textEmail);
        currentUser.updateEmail(textEmail);
        userDocument.update(particulier).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: updated");
                Toast.makeText(EditParticulier.this,"Profile est à jour",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditParticulier.this, MainActivity.class);
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
                                Log.d(TAG, "onSuccess: deleted particulier");
                                Toast.makeText(EditParticulier.this,"Votre Compte est supprimé !",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditParticulier.this, Login.class);
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