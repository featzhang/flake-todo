package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author zhangzuofeng1
 */
@Component
@FxmlView("settings.fxml")
public class SettingsController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);
    public Slider focusTimeSlider;

    public Slider restTimeSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        long focusTimeInSeconds = settings.getFocusTimeInSeconds();
        long napTimeInSeconds = settings.getNapTimeInSeconds();
        log.info("get: {}=>{}", focusTimeInSeconds, napTimeInSeconds);
        focusTimeSlider.setValue(focusTimeInSeconds);
        restTimeSlider.setValue(napTimeInSeconds);
    }

    public void onCancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private final FlakeSettings settings = FlakeSettings.getInstance();

    public void onSure(ActionEvent actionEvent) {
        double focusTimeSliderValue = focusTimeSlider.getValue();
        double restTimeSliderValue = restTimeSlider.getValue();
        settings.setNapTimeInSecs((long) restTimeSliderValue);
        settings.setFocusTimeInSecs((long) focusTimeSliderValue);
        log.info("set {}=>{}", focusTimeSliderValue, restTimeSliderValue);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
