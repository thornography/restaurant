package com.gencprogramcilar.model;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private int id_restaurant;
    private int id_category;
    private Restaurant restaurant;
    private Category category;
    private String name;
    private float prica;
    private byte[] picture;

    public Food() {
    }

    public Food(int id, Restaurant restaurant, Category category, String name, float prica, byte[] picture) {
        this.id = id;
        this.restaurant = restaurant;
        this.category = category;
        this.name = name;
        this.prica = prica;
        this.picture = picture;
    }

    public Food(int id, int id_restaurant, int id_category, String name, float prica, byte[] picture)
    {
        this.id = id;
        this.id_restaurant = id_restaurant;
        this.id_category = id_category;
        this.name = name;
        this.prica = prica;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return prica;
    }

    public void setPrica(float prica) {
        this.prica = prica;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
