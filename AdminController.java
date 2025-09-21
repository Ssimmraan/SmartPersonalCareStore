package com.personalcare.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AdminController {

    @FXML
    public void initialize() {
        // placeholder: Real admin functionality (add/edit/delete products) can be added here
    }

    @FXML
    public void handleManageProducts() {
        new Alert(Alert.AlertType.INFORMATION, "Admin product management not implemented in skeleton.").showAndWait();
    }
}
