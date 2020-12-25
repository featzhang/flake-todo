package com.github.zuofengzhang.flake.client;

import com.github.zuofengzhang.flake.client.constraints.FlakeLabel;
import com.github.zuofengzhang.flake.client.controller.DashboardController;
import com.github.zuofengzhang.flake.client.controller.SettingsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.slf4j.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;

public class FlackClientDashboard extends Application {

    private static final Logger logger = getLogger(FlackClientDashboard.class);
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(FlakeClientApplication.class)
                .run(args);
    }

    private FxWeaver fxWeaver;
    private ResourceBundle resourceBundle;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        fxWeaver = applicationContext.getBean(FxWeaver.class);
        resourceBundle = ResourceBundle.getBundle("flake-client");

        Parent gridPane = fxWeaver.loadView(DashboardController.class, resourceBundle);
        DashboardController controller = fxWeaver.loadController(DashboardController.class, resourceBundle);
        controller.setOnSetting(actionEvent -> onShowSetting());
        logger.info("start FlackClientDashboard...");
        if (gridPane == null) {
            logger.error("root is null");
            return;
        }
        Scene scene = new Scene(gridPane);
        stage.setTitle(FlakeLabel.application_name);
        stage.setScene(scene);
        stage.setWidth(830);
        stage.setHeight(850);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        this.applicationContext.close();
        Platform.exit();
    }

    private void onShowSetting() {
        BorderPane borderPane = fxWeaver.loadView(SettingsController.class, resourceBundle);
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initOwner(primaryStage);
        stage.setScene(scene);
        stage.show();
    }

}
