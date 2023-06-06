package com.madi.msdztest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;
import com.madi.msdztest.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Veuillez attendre");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.buttonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });





            }
    private String email = "";
    private void validateData() {

        email = binding.inputEmail.getText().toString().trim();

        if (email.isEmpty()){
            Toast.makeText(this,"Entre votre email",Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format",Toast.LENGTH_SHORT).show();

        }else {
                recoverPassword();
        }

    }

    private void recoverPassword() {

        progressDialog.setMessage("Envoi des instructions de récupération du mot de passe à " +email);
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this,"Instructions pour réinitialiser le mot de passe envoyé à "+email,Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this,"Échec de l'envoi car l'email n'existe pas ou l'utilisateur peut etre effac"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}