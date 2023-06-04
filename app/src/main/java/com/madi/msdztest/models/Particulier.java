package com.madi.msdztest.models;

public class Particulier {

    private String Nom;
    private String Prénom;
    private String Email;
    private String Telephone;
    private String imageProfile;

    public Particulier() {
        // Required empty constructor for Firestore serialization
    }

    public Particulier(String nom, String prénom, String email, String telephone, String imageProfile) {
        Nom = nom;
        Prénom = prénom;
        Email = email;
        Telephone = telephone;
        this.imageProfile = imageProfile;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrénom() {
        return Prénom;
    }

    public void setPrénom(String prénom) {
        Prénom = prénom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}
