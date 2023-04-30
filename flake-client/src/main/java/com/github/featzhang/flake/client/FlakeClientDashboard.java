package com.github.featzhang.flake.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * The dashboard application entry
 */
public class FlakeClientDashboard extends Application {

    private static final Logger logger = getLogger(FlakeClientDashboard.class);
    public static ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        context = new SpringApplicationBuilder()
                .sources(FlakeClientApplication.class)
                .run(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() throws Exception {
        context.close();
        Platform.exit();
    }


}
