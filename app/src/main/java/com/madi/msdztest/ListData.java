package com.madi.msdztest;

public class ListData {
    String name;
    String categorie;
    String city;
    int image;

    public ListData(String name, String categorie, String city, int image) {
        this.name = name;
        this.categorie = categorie;
        this.city = city;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getCity() {
        return city;
    }

    public int getImage() {
        return image;
    }
}
