package com.personalcare.controllers;

import com.personalcare.services.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final DatabaseService dbService = new DatabaseService();

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter username and password").showAndWait();
            return;
        }

        String role = dbService.verifyUserReturnRole(username, password);
        if (role == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid credentials!").showAndWait();
            return;
        }

        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            if (stage == null) {
                new Alert(Alert.AlertType.ERROR, "Stage not found! Cannot navigate.").showAndWait();
                return;
            }

            AppController app = new AppController(stage);

            if (role.equalsIgnoreCase("admin")) {
                app.showAdmin();
            } else {
                app.showStore();
            }

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Navigation error: " + e.getMessage()).showAndWait();
        }
    }
}
