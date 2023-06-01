package com.madi.msdztest.models;

import java.util.List;
public class Artisan {
    private String Catégorie;
    private String Email;
    private String Nom;
    private String Numero_de_téléphone;
    private String Prénom;
    private String Wilaya;
    private List<String> images;

    private String key;
    public Artisan() {
        // Required empty constructor for Firestore serialization
    }

    public Artisan(String catégorie, String email, String nom, String numero_de_téléphone, String prénom, String wilaya, List<String> images) {
        this.Catégorie = catégorie;
        this.Email = email;
        this.Nom = nom;
        this.Numero_de_téléphone = numero_de_téléphone;
        this.Prénom = prénom;
        this.Wilaya = wilaya;
        this.images = images;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCatégorie() {
        return Catégorie;
    }

    public String getEmail() {
        return Email;
    }

    public String getNom() {
        return Nom;
    }

    public String getNumero_de_téléphone() {
        return Numero_de_téléphone;
    }

    public String getPrénom() {
        return Prénom;
    }

    public String getWilaya() {
        return Wilaya;
    }

    public List<String> getImages() {
        return images;
    }
}
