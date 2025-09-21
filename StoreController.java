package com.personalcare.controllers;

import com.personalcare.models.Product;
import com.personalcare.services.CartService;
import com.personalcare.services.DatabaseService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class StoreController {

    @FXML private GridPane productsGrid;

    private final DatabaseService dbService = new DatabaseService();
    private final CartService cartService = CartService.getInstance();

    @FXML
    public void initialize() {
        populateProducts();
    }

    private void populateProducts() {
        List<Product> products = dbService.getAllProducts();
        productsGrid.getChildren().clear();
        productsGrid.setHgap(20);
        productsGrid.setVgap(20);
        productsGrid.setPadding(new Insets(15));

        int col = 0, row = 0;
        for (Product product : products) {
            VBox card = createProductCard(product);
            productsGrid.add(card, col, row);
            col++;
            if (col > 2) { col = 0; row++; }
        }
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(8);
        card.getStyleClass().add("product-card");
        card.setPadding(new Insets(10));

        ImageView imgView;
        try {
            Image img = new Image(getClass().getResourceAsStream(product.getImagePath()));
            imgView = new ImageView(img);
        } catch (Exception e) {
            imgView = new ImageView(); // fallback empty image
        }
        imgView.setFitWidth(140);
        imgView.setFitHeight(140);

        Label name = new Label(product.getName());
        name.getStyleClass().add("product-name");

        Label price = new Label("$" + String.format("%.2f", product.getPrice()));
        price.getStyleClass().add("product-price");

        Label stock = new Label("Stock: " + product.getQuantity());
        stock.getStyleClass().add("product-stock");

        Button addToCart = new Button("Add to Cart");
        addToCart.getStyleClass().add("add-to-cart-btn");
        addToCart.setOnAction(e -> {
            if (product.getQuantity() <= 0) {
                new Alert(Alert.AlertType.WARNING, "Product out of stock").showAndWait();
                return;
            }
            cartService.addToCart(product, 1);
            product.setQuantity(product.getQuantity() - 1); // update stock
            new Alert(Alert.AlertType.INFORMATION, product.getName() + " added to cart").showAndWait();
            populateProducts(); // refresh
        });

        card.getChildren().addAll(imgView, name, price, stock, addToCart);
        return card;
    }

    @FXML
    public void openCart() {
        try {
            Stage stage = (Stage) productsGrid.getScene().getWindow();
            if (stage == null) throw new Exception("Stage is null!");
            AppController app = new AppController(stage);
            app.showCart();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Navigation error: " + e.getMessage()).showAndWait();
        }
    }
}
