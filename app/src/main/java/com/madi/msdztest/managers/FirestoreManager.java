package com.madi.msdztest.managers;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.madi.msdztest.ArtisanProfileActivity;
import com.madi.msdztest.login.Login;
import com.madi.msdztest.main.MainActivity;
import com.madi.msdztest.models.Artisan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreManager {
    public interface LoginCallback {
        void userLogged();
        void artisanLogged();
    }
    public void checkUserRole(String userId , LoginCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Check if the user exists in the "Artisans" collection
        DocumentReference artisanRef = db.collection("Artisans").document(userId);
        artisanRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot artisanDocument = task.getResult();
                    if (artisanDocument != null && artisanDocument.exists()) {
                        // User is an artisan
                        callback.artisanLogged();

                    } else {
                        // User is not an artisan, check the "Particuliers" collection
                        DocumentReference clientRef = db.collection("Particuliers").document(userId);
                        clientRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot clientDocument = task.getResult();
                                    if (clientDocument != null && clientDocument.exists()) {
                                        // User is a client
                                        callback.userLogged();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    // get artisans
    public interface GetArtisansCallback {
        void onSuccess(List<Artisan> artisans);
        void onFailure(Exception e);
    }

    public void getArtisansByCategory(String category, GetArtisansCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Artisans").whereEqualTo("Catégorie", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Artisan> artisans = new ArrayList<>();
                            if (task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Artisan artisan = document.toObject(Artisan.class);
                                    artisan.setKey(document.getId());
                                    artisans.add(artisan);
                                }
                            }
                            callback.onSuccess(artisans);
                        } else {
                            callback.onFailure(task.getException());
                        }
                    }
                });
    }

    public interface GetArtisanByIdCallback {
        void onSuccess(Artisan artisan);
        void onFailure(Exception e);
    }

    public void getArtisansByID(String userId, GetArtisanByIdCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Artisans").document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Artisan artisan = task.getResult().toObject(Artisan.class);
                            callback.onSuccess(artisan);
                        } else {
                            callback.onFailure(task.getException());
                        }
                    }
                });
    }

    public interface SaveCallback {
        void userSaved();
    }
    public void saveArtisanData(String userId,Artisan artisanObj ,SaveCallback callback ){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference usersCollection = db.collection("Artisans");
        DocumentReference userDocument = usersCollection.document(userId);

        Map<String, Object> artisan = new HashMap<>();
        artisan.put("Nom", artisanObj.getNom());
        artisan.put("Prénom", artisanObj.getPrénom());
        artisan.put("Email", artisanObj.getEmail());
        userDocument.update(artisan).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                       callback.userSaved();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
