package com.github.zuofengzhang.flake.client.controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

public class FlakeScene extends Scene {
    public FlakeScene(Parent root) {
        super(root);
        initStylesheets();
    }

    private void initStylesheets() {
        String cssPath = "css/flake-default.css";
        cssPath = "css/jbootx.css";
        String externalForm = this.getClass().getClassLoader().getResource(cssPath).toExternalForm();
        getStylesheets().add(externalForm);
    }

    public FlakeScene(Parent root, double width, double height) {
        super(root, width, height);
        initStylesheets();

    }

    public FlakeScene(Parent root, Paint fill) {
        super(root, fill);
        initStylesheets();

    }

    public FlakeScene(Parent root, double width, double height, Paint fill) {
        super(root, width, height, fill);
        initStylesheets();

    }

    public FlakeScene(Parent root, double width, double height, boolean depthBuffer) {
        super(root, width, height, depthBuffer);
        initStylesheets();

    }

    public FlakeScene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
        super(root, width, height, depthBuffer, antiAliasing);
        initStylesheets();

    }
}
