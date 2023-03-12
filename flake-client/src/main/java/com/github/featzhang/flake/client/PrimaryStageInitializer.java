package com.github.featzhang.flake.client;

import com.github.featzhang.flake.client.consts.FlakeLabel;
import com.github.featzhang.flake.client.controller.DashboardController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ResourceBundle;

/**
 */
@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(PrimaryStageInitializer.class);

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Resource
    private ResourceBundle resourceBundle;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        stage.setTitle(FlakeLabel.application_name.value());
        Scene scene = new Scene(fxWeaver.loadView(DashboardController.class, resourceBundle), 1100, 850);
        String externalForm = this.getClass().getClassLoader().getResource("css/flake-default.css").toExternalForm();
        logger.info("load css: {}", externalForm);
        scene.getStylesheets().add(externalForm);
        stage.setScene(scene);
        stage.show();
    }
}
