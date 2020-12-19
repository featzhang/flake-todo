package com.github.zuofengzhang.flake.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class FlackClientDashboard extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource("fxml/dashboard.fxml");
        InputStream inputStream = classLoader.getResourceAsStream("fxml/dashboard.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("flake-client");
        BorderPane gridPane = fxmlLoader.load(url, resourceBundle);
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(800);
        stage.show();
    }
}
