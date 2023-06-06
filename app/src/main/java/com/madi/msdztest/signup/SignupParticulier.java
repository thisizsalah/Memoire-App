package com.madi.msdztest.signup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.madi.msdztest.R;
import com.madi.msdztest.login.Login;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupParticulier extends Fragment {
    private ProgressDialog progressDialog;
    private EditText ClientNom, ClientPrenom, ClientEmail, ClientNumeroTlf, ClientMdp, ClientReMdp;
    private static final String TAG="SignupParticulier";
    public SignupParticulier() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_paticulier, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Création du compte");
        progressDialog.setMessage("Veuillez patienter...");
        progressDialog.setCanceledOnTouchOutside(false);

        ClientNom = view.findViewById(R.id.Client_Nom);
        ClientPrenom = view.findViewById(R.id.Client_Prenom);
        ClientEmail = view.findViewById(R.id.Client_Email);
        ClientNumeroTlf = view.findViewById(R.id.Client_Numero_Tlf);
        ClientMdp = view.findViewById(R.id.Client_Mdp);
        ClientReMdp = view.findViewById(R.id.inputEmail);
        Button BtnCreer = view.findViewById(R.id.Button_Creer_Compte);
        BtnCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNom = ClientNom.getText().toString();
                String textPrenom = ClientPrenom.getText().toString();
                String textEmail = ClientEmail.getText().toString();
                String textNumeroTlf = ClientNumeroTlf.getText().toString();
                String textMdp = ClientMdp.getText().toString();
                String textReMdp = ClientReMdp.getText().toString();

                String NumeroX = "[0][5-7][0-9]{8}";
                Matcher NumeroMatcher;
                Pattern NumeroPattern = Pattern.compile(NumeroX);
                NumeroMatcher = NumeroPattern.matcher(textNumeroTlf);

                if (TextUtils.isEmpty(textNom)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Nom !", Toast.LENGTH_SHORT).show();
                    ClientNom.setError("Nom est obligatoire");
                    ClientNom.requestFocus();
                } else if (TextUtils.isEmpty(textPrenom)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Prénom !", Toast.LENGTH_SHORT).show();
                    ClientPrenom.setError("Prénom est obligatoire");
                    ClientPrenom.requestFocus();

                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ClientEmail.setError("E-mail est obligatoire");
                    ClientEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(getContext(), "Veuillez re-entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ClientEmail.setError("Valider E-mail est obligatoire");
                    ClientEmail.requestFocus();
                } else if (TextUtils.isEmpty(textNumeroTlf)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ClientNumeroTlf.setError("Numéro de téléphone est obligatoire");
                    ClientNumeroTlf.requestFocus();
                } else if (textNumeroTlf.length() != 10) {
                    Toast.makeText(getContext(), "Veuillez re-entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ClientNumeroTlf.setError("Numéro de téléphone doit comporter 10 chiffres");
                    ClientNumeroTlf.requestFocus();

                }else if (!NumeroMatcher.find()) {
                        Toast.makeText(getContext(), "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                        ClientNumeroTlf.setError("Numéro de téléphone invalide");
                        ClientNumeroTlf.requestFocus();
                } else if (TextUtils.isEmpty(textMdp)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre mot de passe !", Toast.LENGTH_SHORT).show();
                    ClientMdp.setError("mot de passe est obligatoire");
                    ClientMdp.requestFocus();
                } else if (textMdp.length() < 6) {
                    Toast.makeText(getContext(), "Mot de passe doit comporter au moins 6 chiffres", Toast.LENGTH_SHORT).show();
                    ClientMdp.setError("Mot de passe trés faible");
                    ClientMdp.requestFocus();
                } else if (TextUtils.isEmpty(textReMdp)) {
                    Toast.makeText(getContext(), "Veuillez re-entrer votre mot de passe !", Toast.LENGTH_SHORT).show();
                    ClientReMdp.setError("Mot de passe confirmation est obligatoire");
                    ClientReMdp.requestFocus();
                } else if (!textMdp.equals(textReMdp)) {
                    Toast.makeText(getContext(), "Les mots de passe ne correspondesnt pas !", Toast.LENGTH_SHORT).show();
                    ClientReMdp.setError("Mot de passe confirmation est obligatoire");
                    ClientReMdp.requestFocus();

                    ClientMdp.clearComposingText();
                    ClientReMdp.clearComposingText();
                } else {
                    registerUser(textNom, textPrenom, textEmail, textNumeroTlf, textMdp);

                }
            }
        });
        return view;
    }

    private void registerUser(String textNom, String textPrenom, String textEmail, String textNumeroTlfn, String textMdp) {
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textMdp).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(),"Votre compte est créer avec succés",Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();


                    firebaseUser.sendEmailVerification();

                    String userId = firebaseUser.getUid();
                    CollectionReference usersCollection = db.collection("Particuliers");
                    DocumentReference userDocument = usersCollection.document(userId);

                    Map<String, Object> user = new HashMap<>();
                    user.put("Nom", textNom);
                    user.put("Prénom", textPrenom);
                    user.put("Email", textEmail);
                    user.put("Telephone", textNumeroTlfn);
                    userDocument.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"Votre compte est créer avec succés");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG,"Erreur !", e);
                                }
                            });

                    Intent intent = new Intent(getContext(), Login.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        ClientMdp.setError("Votre mot de passe est faible. Veuillez choisir un mot de passe plus fort");
                        ClientMdp.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        ClientEmail.setError("Adresse e-mail invalide. Veuillez entrer un email valide");
                        ClientEmail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        ClientEmail.setError("L'e-mail que vous avez saisi est déjà associé à un autre compte. Veuillez utiliser une adresse e-mail différente");
                        ClientEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(getContext(),e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}