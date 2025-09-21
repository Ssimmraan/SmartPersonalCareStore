package com.personalcare.controllers;

import com.personalcare.models.CartItem;
import com.personalcare.services.CartService;
import com.personalcare.services.PDFService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class CartController {

    @FXML private TableView<CartItem> cartTable;
    @FXML private TableColumn<CartItem, String> productCol;
    @FXML private TableColumn<CartItem, Integer> qtyCol;
    @FXML private TableColumn<CartItem, Double> priceCol;
    @FXML private TableColumn<CartItem, Double> totalCol;
    @FXML private Label totalAmountLabel;

    private final CartService cartService = CartService.getInstance();
    private final ObservableList<CartItem> cartItemsObs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getProduct().getName()));
        qtyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getProduct().getPrice()).asObject());
        totalCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getTotalPrice()).asObject());

        cartTable.setItems(cartItemsObs);
        refreshCart();
    }

    public void refreshCart() {
        List<CartItem> items = cartService.getCartItems();
        cartItemsObs.setAll(items);
        totalAmountLabel.setText("Total: $" + String.format("%.2f", cartService.getTotalAmount()));
    }

    @FXML
    public void handleRemove() {
        CartItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        cartService.removeFromCart(selected);
        refreshCart();
    }

    @FXML
    public void handleCheckout() {
        if (cartItemsObs.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Cart is empty").showAndWait();
            return;
        }

        double total = cartService.getTotalAmount();
        String file = PDFService.generateInvoiceFile(cartService.getCartItems(), total);
        cartService.clearCart();
        refreshCart();
        new Alert(Alert.AlertType.INFORMATION, "Checkout complete. Invoice saved: " + file).showAndWait();

        // Navigate back to Store
        try {
            Stage stage = (Stage) totalAmountLabel.getScene().getWindow();
            if (stage == null) throw new Exception("Stage is null!");
            AppController app = new AppController(stage);
            app.showStore();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Navigation error: " + e.getMessage()).showAndWait();
        }
    }
}
