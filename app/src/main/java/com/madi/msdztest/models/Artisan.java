package com.madi.msdztest.models;


import java.util.List;

public class Artisan {
    private String Catégorie;
    private String Email;
    private String Nom;

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    private String imageProfile;

    public void setCatégorie(String catégorie) {
        Catégorie = catégorie;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public void setPrénom(String prénom) {
        Prénom = prénom;
    }

    public void setWilaya(String wilaya) {
        Wilaya = wilaya;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public void setImages(List<String> images) {
        this.Images = images;
    }

    private String Telephone;
    private String Prénom;
    private String Wilaya;

    private String Description;
    private List<String> Images;




    private String key;

    public Artisan() {
        // Required empty constructor for Firestore serialization
    }

    public Artisan(String catégorie, String email, String nom, String telephone, String prénom, String wilaya, String description, List<String> images, String imageProfile) {
        this.Catégorie = catégorie;
        this.Email = email;
        this.Nom = nom;
        this.Telephone = telephone;
        this.Prénom = prénom;
        this.Wilaya = wilaya;
        this.Images = images;
        this.Description = description;
        this.imageProfile = imageProfile;
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

    public String getTelephone() {
        return Telephone;
    }

    public String getPrénom() {
        return Prénom;
    }

    public String getWilaya() {
        return Wilaya;
    }

    public List<String> getImages() {
        return Images;
    }

    public String getDescription() {
        return Description;
    }
}
