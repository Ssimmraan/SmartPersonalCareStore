package com.personalcare.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

    private final Stage stage;

    public AppController(Stage stage) {
        this.stage = stage;
    }

    // Load FXML safely
    private void loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showLogin() throws IOException {
        loadFXML("login.fxml");
    }

    public void showStore() throws IOException {
        loadFXML("store.fxml");
    }

    public void showCart() throws IOException {
        loadFXML("cart.fxml");
    }

    public void showAdmin() throws IOException {
        loadFXML("admin.fxml");
    }
}
