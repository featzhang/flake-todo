package com.github.zuofengzhang.flake.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author zhangzuofeng1
 */
public class FlakeClientDashboard extends Application {

    private static final Logger                         logger = getLogger(FlakeClientDashboard.class);
    public static        ConfigurableApplicationContext context;

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
//        this.primaryStage = stage;
////        fxWeaver = context.getBean(FxWeaver.class);
//
//        Parent gridPane = fxWeaver.loadView(DashboardController.class, resourceBundle);
////        DashboardController controller = fxWeaver.loadController(DashboardController.class, resourceBundle);
////        controller.setOnSetting(actionEvent -> onShowSetting());
//        logger.info("start FlackClientDashboard...");
//        if (gridPane == null) {
//            logger.error("root is null");
//            return;
//        }
//        Scene scene = new Scene(gridPane);
//        stage.setTitle(FlakeLabel.application_name);
//        stage.setScene(scene);
//        stage.setWidth(950);
//        stage.setHeight(850);
//        stage.show();
    }

    @Override
    public void stop() throws Exception {
        context.close();
        Platform.exit();
    }


}
