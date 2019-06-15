package com.senac.pedro.model;

public class Food {
    private String name;
    private float price;
    private String description;
    private int calories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return String.format("{name: '%s', price: %s, description: '%s', calories: %d}", name, Float.toString(price).replace(',', '.'), description, calories);
    }
}
