package com.github.zuofengzhang.flake.client.focus.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FocusPaneTest extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("title");
        FocusPane focusPane = new FocusPane();
        Scene scene = new Scene(focusPane);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
