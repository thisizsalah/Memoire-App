package com.madi.msdztest.managers;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseStorageManager {
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;
    int uploadedImages = 0;

    public FirebaseStorageManager() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public interface UploadCallback {
        void onUploadProgress(int progress);
        void onUploadComplete();
        void onUploadFailure(Exception e);
    }

    public void uploadImages(Uri[] imageUris, UploadCallback callback) {
        int totalImages = imageUris.length;
        uploadedImages = 0;
        for (Uri imageUri : imageUris) {
            uploadImage(imageUri, new UploadCallback() {
                @Override
                public void onUploadProgress(int progress) {
                    callback.onUploadProgress((uploadedImages * 100) / totalImages + progress / totalImages);
                }

                @Override
                public void onUploadComplete() {
                    uploadedImages++;
                    callback.onUploadProgress((uploadedImages * 100) / totalImages);
                    if (uploadedImages == totalImages) {
                        callback.onUploadComplete();
                    }
                }

                @Override
                public void onUploadFailure(Exception e) {
                    callback.onUploadFailure(e);
                }
            });
        }
    }

    public void uploadImage(Uri imageUri, UploadCallback callback) {
        String fileName = UUID.randomUUID().toString();
        StorageReference storageRef = storage.getReference().child("images/" + fileName);

        UploadTask uploadTask = storageRef.putFile(imageUri);
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            callback.onUploadProgress((int) progress);
        }).addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    // Create a new image object
                    Map<String, Object> imageData = new HashMap<>();
                    imageData.put("imageUrl", imageUrl);

                    // Add the image URL to the user's "images" array
                    db.collection("Artisans").document(userId)
                            .update("images", FieldValue.arrayUnion(imageUrl))
                            .addOnSuccessListener(aVoid -> {
                                callback.onUploadComplete();
                            })
                            .addOnFailureListener(e -> {
                                callback.onUploadFailure(e);
                            });
                }

            });
        }).addOnFailureListener(e -> {
            callback.onUploadFailure(e);
        });
    }

    public void uploadImageParticulier(Uri imageUri, UploadCallback callback) {
        String fileName = UUID.randomUUID().toString();
        StorageReference storageRef = storage.getReference().child("imageProfileP/" + fileName);

        UploadTask uploadTask = storageRef.putFile(imageUri);
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            callback.onUploadProgress((int) progress);
        }).addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    // Create a new image object
                    Map<String, Object> imageData = new HashMap<>();
                    imageData.put("imageUrl", imageUrl);

                    // Add the image URL to the user's "images" array
                    db.collection("Particuliers").document(userId)
                            .update("imageProfile", imageUrl)
                            .addOnSuccessListener(aVoid -> {
                                callback.onUploadComplete();
                            })
                            .addOnFailureListener(e -> {
                                callback.onUploadFailure(e);
                            });
                }

            });
        }).addOnFailureListener(e -> {
            callback.onUploadFailure(e);
        });
    }
    public void uploadProfileImage(Uri imageUri, UploadCallback callback) {
        String fileName = UUID.randomUUID().toString();
        StorageReference storageRef = storage.getReference().child("imageProfile/" + fileName);

        UploadTask uploadTask = storageRef.putFile(imageUri);
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            callback.onUploadProgress((int) progress);
        }).addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    // Create a new image object
                    Map<String, Object> imageData = new HashMap<>();
                    imageData.put("imageUrl", imageUrl);

                    // Add the image URL to the user's "images" array
                    db.collection("Artisans").document(userId)
                            .update("imageProfile", imageUrl)
                            .addOnSuccessListener(aVoid -> {
                                callback.onUploadComplete();
                            })
                            .addOnFailureListener(e -> {
                                callback.onUploadFailure(e);
                            });
                }

            });
        }).addOnFailureListener(e -> {
            callback.onUploadFailure(e);
        });
    }
}

