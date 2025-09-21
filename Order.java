package com.personalcare.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private String username;
    private List<CartItem> items;
    private double total;
    private LocalDateTime createdAt;

    public Order(int id, String username, List<CartItem> items, double total) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.total = total;
        this.createdAt = LocalDateTime.now();
    }

    // getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
