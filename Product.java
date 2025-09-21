package com.personalcare.models;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String imagePath;
    private double rating;

    public Product(int id, String name, double price, int quantity, String category, String imagePath, double rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imagePath = imagePath;
        this.rating = rating;
    }

    // getters & setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }
    public String getImagePath() { return imagePath; }
    public double getRating() { return rating; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return name + " - $" + price + " (Stock: " + quantity + ")";
    }
}
