package com.madi.msdztest.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.madi.msdztest.R;
import com.madi.msdztest.main.HomeFragment;
import com.madi.msdztest.main.MainActivity;
import com.madi.msdztest.signup.SignupForm;

public class Login extends AppCompatActivity {
    private EditText Email, MotDePasse;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = findViewById(R.id.CreerCompte);
        mAuth = FirebaseAuth.getInstance();


        Email = findViewById(R.id.inputEmail);
        MotDePasse = findViewById(R.id.inputMdp);

        mAuth = FirebaseAuth.getInstance();


        Button button = findViewById(R.id.buttonConnecter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = Email.getText().toString();
                String textMdp = MotDePasse.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(Login.this, "Veuillez entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    Email.setError("E-mail est obligatoire");
                    Email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(Login.this, "Veuillez entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    Email.setError("Un e-mail valide est requis");
                    Email.requestFocus();

                } else if (TextUtils.isEmpty(textMdp)) {
                    Toast.makeText(Login.this, "Veuillez entrer votre mot de passe !", Toast.LENGTH_SHORT).show();
                    MotDePasse.setError("mot de passe est obligatoire");
                    MotDePasse.requestFocus();
                } else {
                    loginUser(textEmail, textMdp);
                }
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignupForm.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String Mdp) {
        mAuth.signInWithEmailAndPassword(email, Mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Bienvenue ! ", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(Login.this, MainActivity.class);
                    startActivity(intent2);
                    finish();
                } else {
                    // Check the specific error code
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        String errorCode = ((FirebaseAuthInvalidUserException) task.getException()).getErrorCode();

                        if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                            // User does not exist
                            Toast.makeText(Login.this, "Utilisateur introuvable.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Échec de la connexion!,veuillez vérifier vos informations ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
