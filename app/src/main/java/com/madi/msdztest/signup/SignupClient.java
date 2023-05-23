package com.madi.msdztest.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madi.msdztest.R;
import com.madi.msdztest.login.Login;

public class SignupClient extends Fragment {

    private EditText ClientNom, ClientPrenom, ClientEmail, ClientNumeroTlfn, ClientMdp, ClientReMdp;

    public SignupClient() {
        // Required empty public constructor
    }

    public static SignupClient newInstance() {
        return new SignupClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_client, container, false);
        ClientNom = view.findViewById(R.id.Client_Nom);
        ClientPrenom = view.findViewById(R.id.Client_Prenom);
        ClientEmail = view.findViewById(R.id.Client_Email);
        ClientNumeroTlfn = view.findViewById(R.id.Client_Numero_Tlfn);
        ClientMdp = view.findViewById(R.id.Client_Mdp);
        ClientReMdp = view.findViewById(R.id.Client_Re_Mdp);
        Button BtnCreer = view.findViewById(R.id.Button_Creer_Compte);
        BtnCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNom = ClientNom.getText().toString();
                String textPrenom = ClientPrenom.getText().toString();
                String textEmail = ClientEmail.getText().toString();
                String textNumeroTlfn = ClientNumeroTlfn.getText().toString();
                String textMdp = ClientMdp.getText().toString();
                String textReMdp = ClientReMdp.getText().toString();
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
                } else if (TextUtils.isEmpty(textNumeroTlfn)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ClientNumeroTlfn.setError("Numéro de téléphone est obligatoire");
                    ClientNumeroTlfn.requestFocus();
                } else if (textNumeroTlfn.length() != 10) {
                    Toast.makeText(getContext(), "Veuillez re-entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ClientNumeroTlfn.setError("Numéro de téléphone doit comporter 10 chiffres");
                    ClientNumeroTlfn.requestFocus();
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
                    registerUser(textNom, textPrenom, textEmail, textNumeroTlfn, textMdp, textReMdp);

                }
            }
        });
        return view;
    }

    private void registerUser(String textNom, String textPrenom, String textEmail, String textNumeroTlfn, String textMdp, String textReMdp) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textMdp).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Client créer avec succés", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();


                    firebaseUser.sendEmailVerification();

                    Intent intent = new Intent(getContext(), Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                }
            }
        });
    }
}