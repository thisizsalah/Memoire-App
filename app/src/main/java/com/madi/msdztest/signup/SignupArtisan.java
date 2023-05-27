package com.madi.msdztest.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignupArtisan extends Fragment {

    private EditText ArtisanNom, ArtisanPrenom, ArtisanEmail, ArtisanNumeroTlf, ArtisanMdp, ArtisanReMdp; //Artisan
    Spinner spinnerWilaya,spinnerCat;
    private final static String TAG ="SignupArtisan";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public SignupArtisan() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SignupArtisan newInstance(String param1, String param2) {
        SignupArtisan fragment = new SignupArtisan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinnerWilaya();
        initSpinnerCat();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_artisan, container, false);

        spinnerWilaya = view.findViewById(R.id.spinner_wilaya);
        spinnerCat = view.findViewById(R.id.spinner_categorie);

        ArtisanNom = view.findViewById(R.id.Artisan_Nom);
        ArtisanPrenom = view.findViewById(R.id.Artisan_Prenom);
        ArtisanEmail = view.findViewById(R.id.Artisan_Email);
        ArtisanNumeroTlf = view.findViewById(R.id.Artisan_Numero_Tlf);
        ArtisanMdp = view.findViewById(R.id.Artisan_Mdp);
        ArtisanReMdp = view.findViewById(R.id.Artisan_Re_Mdp);
        Button BtnCreer = view.findViewById(R.id.Button_Creer_Compte);
        BtnCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textNom = ArtisanNom.getText().toString();
                String textPrenom = ArtisanPrenom.getText().toString();
                String textEmail = ArtisanEmail.getText().toString();
                String textNumeroTlf = ArtisanNumeroTlf.getText().toString();
                String textMdp = ArtisanMdp.getText().toString();
                String textReMdp = ArtisanReMdp.getText().toString();
                String wilaya = spinnerWilaya.getSelectedItem().toString();
                String categorie = spinnerCat.getSelectedItem().toString();

                String NumeroX = "[0][5-7][0-9]{8}";
                Matcher NumeroMatcher;
                Pattern NumeroPattern = Pattern.compile(NumeroX);
                NumeroMatcher = NumeroPattern.matcher(textNumeroTlf);

                if (categorie.equals("Catégorie")) {
                    Toast.makeText(getContext(), "Veuillez sélectionner votre catégorie ", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (wilaya.equals("Selectionner Wilaya")) {
                    Toast.makeText(getContext(), "Veuillez sélectionner votre wilaya !", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(textNom)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Nom !", Toast.LENGTH_SHORT).show();
                    ArtisanNom.setError("Nom est obligatoire");
                    ArtisanNom.requestFocus();
                } else if (TextUtils.isEmpty(textPrenom)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Prénom !", Toast.LENGTH_SHORT).show();
                    ArtisanPrenom.setError("Prénom est obligatoire");
                    ArtisanPrenom.requestFocus();

                   

                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ArtisanEmail.setError("E-mail est obligatoire");
                    ArtisanEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(getContext(), "Veuillez re-entrer votre E-mail !", Toast.LENGTH_SHORT).show();
                    ArtisanEmail.setError("Un email valide est requis");
                    ArtisanEmail.requestFocus();
                } else if (TextUtils.isEmpty(textNumeroTlf)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ArtisanNumeroTlf.setError("Numéro de téléphone est obligatoire");
                    ArtisanNumeroTlf.requestFocus();
                } else if (textNumeroTlf.length() != 10) {
                    Toast.makeText(getContext(), "Veuillez re-entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ArtisanNumeroTlf.setError("Numéro de téléphone doit comporter 10 chiffres");
                    ArtisanNumeroTlf.requestFocus();

                } else if (!NumeroMatcher.find()) {
                    Toast.makeText(getContext(), "Veuillez entrer votre Numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    ArtisanNumeroTlf.setError("Numéro de téléphone invalide");
                    ArtisanNumeroTlf.requestFocus();

            } else if (TextUtils.isEmpty(textMdp)) {
                    Toast.makeText(getContext(), "Veuillez entrer votre mot de passe !", Toast.LENGTH_SHORT).show();
                    ArtisanMdp.setError("mot de passe est obligatoire");
                    ArtisanMdp.requestFocus();
                } else if (textMdp.length() < 6) {
                    Toast.makeText(getContext(), "Mot de passe doit comporter au moins 6 chiffres", Toast.LENGTH_SHORT).show();
                    ArtisanMdp.setError("Mot de passe trés faible");
                    ArtisanMdp.requestFocus();
                } else if (TextUtils.isEmpty(textReMdp)) {
                    Toast.makeText(getContext(), "Veuillez re-entrer votre mot de passe !", Toast.LENGTH_SHORT).show();
                    ArtisanReMdp.setError("Mot de passe confirmation est obligatoire");
                    ArtisanReMdp.requestFocus();
                } else if (!textMdp.equals(textReMdp)) {
                    Toast.makeText(getContext(), "Les mots de passe ne correspondesnt pas !", Toast.LENGTH_SHORT).show();
                    ArtisanReMdp.setError("Mot de passe confirmation est obligatoire");
                    ArtisanReMdp.requestFocus();

                    ArtisanMdp.clearComposingText();
                    ArtisanReMdp.clearComposingText();
                } else {
                    registerUser(textNom, textPrenom, textEmail, textNumeroTlf, textMdp,wilaya,categorie);

                }
            }
        });

        return view;
    }
    private void registerUser(String textNom, String textPrenom, String textEmail, String textNumeroTlf, String textMdp, String wilaya, String categorie) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textMdp).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Votre compte est créer avec succés", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();


                    firebaseUser.sendEmailVerification();

                    String userId = firebaseUser.getUid();
                    CollectionReference usersCollection = db.collection("Artisans");
                    DocumentReference userDocument = usersCollection.document(userId);

                    Map<String, Object> user = new HashMap<>();
                    user.put("Nom", textNom);
                    user.put("Prénom", textPrenom);
                    user.put("Email", textEmail);
                    user.put("Numero de téléphone", textNumeroTlf);
                    user.put("Mot de passe", textMdp);
                    user.put("Wilaya", wilaya);
                    user.put("Catégorie", categorie);
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
                        ArtisanMdp.setError("Votre mot de passe est faible. Veuillez choisir un mot de passe plus fort");
                        ArtisanMdp.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        ArtisanEmail.setError("Adresse e-mail invalide. Veuillez entrer un email valide");
                        ArtisanEmail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        ArtisanEmail.setError("L'e-mail que vous avez saisi est déjà associé à un autre compte. Veuillez utiliser une adresse e-mail différente");
                        ArtisanEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(getContext(),e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void initializeView(View rootView){

    }
    public void initSpinnerWilaya() {
        ArrayList<String> wilayas = new ArrayList<String>();
        wilayas.add("Selectionner Wilaya");
        wilayas.add("1. Adrar");
        wilayas.add("2. Chlef");
        wilayas.add("3. Laghouat");
        wilayas.add("4. Oum El Bouaghi");
        wilayas.add("5. Batna");
        wilayas.add("6. Béjaïa");
        wilayas.add("7. Biskra");
        wilayas.add("8. Béchar");
        wilayas.add("9. Blida");
        wilayas.add("10. Bouira");
        wilayas.add("11. Tamanrasset");
        wilayas.add("12. Tébessa");
        wilayas.add("13. Tlemcen");
        wilayas.add("14. Tiaret");
        wilayas.add("15. Tizi Ouzou");
        wilayas.add("16. Alger");
        wilayas.add("17. Djelfa");
        wilayas.add("18. Jijel");
        wilayas.add("19. Sétif");
        wilayas.add("20. Saïda");
        wilayas.add("21. Skikda");
        wilayas.add("22. Sidi Bel Abbès");
        wilayas.add("23. Annaba");
        wilayas.add("24. Guelma");
        wilayas.add("25. Constantine");
        wilayas.add("26. Médéa");
        wilayas.add("27. Mostaganem");
        wilayas.add("28. M'Sila");
        wilayas.add("29. Mascara");
        wilayas.add("30. Ouargla");
        wilayas.add("31. Oran");
        wilayas.add("32. El Bayadh");
        wilayas.add("33. Illizi");
        wilayas.add("34. Bordj Bou Arréridj");
        wilayas.add("35. Boumerdès");
        wilayas.add("36. El Tarf");
        wilayas.add("37. Tindouf");
        wilayas.add("38. Tissemsilt");
        wilayas.add("39. El Oued");
        wilayas.add("40. Khenchela");
        wilayas.add("41. Souk Ahras");
        wilayas.add("42. Tipaza");
        wilayas.add("43. Mila");
        wilayas.add("44. Aïn Defla");
        wilayas.add("45. Naâma");
        wilayas.add("46. Aïn Témouchent");
        wilayas.add("47. Ghardaïa");
        wilayas.add("48. Relizane");
        wilayas.add("49. Timimoun");
        wilayas.add("50. Bordj Badji Mokhtar");
        wilayas.add("51. Ouled Djellal");
        wilayas.add("52. Béni Abbès");
        wilayas.add("53. Ain salah");
        wilayas.add("54. Ain guezzam");
        wilayas.add("55. Touggourt");
        wilayas.add("56. Djanet");
        wilayas.add("57. El Menia");
        wilayas.add("58. Touggourt");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,wilayas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWilaya.setAdapter(adapter);
    }
    public void initSpinnerCat(){
        ArrayList<String> categorie = new ArrayList<String>();
        categorie.add("Catégorie");
        categorie.add("Climatisation");
        categorie.add("Déménagement");
        categorie.add("Electrien");
        categorie.add("Installation Parabole");
        categorie.add("Maçon");
        categorie.add("Peintre");
        categorie.add("Plombier");
        categorie.add("Réparation de toit");
        categorie.add("Soudeur");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,categorie);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter);


    }
}