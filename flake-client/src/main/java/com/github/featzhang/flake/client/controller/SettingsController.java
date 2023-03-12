package com.github.featzhang.flake.client.controller;

import com.github.featzhang.flake.client.consts.FlakeSettings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author zhangzuofeng1
 */
@Component
@FxmlView("settings.fxml")
@Slf4j
public class SettingsController implements Initializable {
    public Slider focusTimeSlider;

    public Slider restTimeSlider;
    public CheckBox showDeletedCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        long focusTimeInSeconds = settings.getFocusTimeInSeconds();
        long napTimeInSeconds = settings.getNapTimeInSeconds();
        boolean showDeletedTask = settings.getShowDeletedTask();
        log.info("get: {}=>{}", focusTimeInSeconds, napTimeInSeconds);
        focusTimeSlider.setValue(focusTimeInSeconds/60);
        restTimeSlider.setValue(napTimeInSeconds/60);
        showDeletedCheckBox.setSelected(showDeletedTask);
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
        settings.setNapTimeInSecs((long) restTimeSliderValue*60);
        settings.setFocusTimeInSecs((long) focusTimeSliderValue*60);
        settings.setShowDeletedTask(showDeletedCheckBox.isSelected());
        log.info("set {}=>{}", focusTimeSliderValue, restTimeSliderValue);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
