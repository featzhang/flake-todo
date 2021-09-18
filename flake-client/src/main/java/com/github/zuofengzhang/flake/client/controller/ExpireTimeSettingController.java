package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.utils.ExpirationUtil;
import com.github.zuofengzhang.flake.client.utils.FlakeDateUtil;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.PopOver;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author zuofengzhang
 */
@Component
@FxmlView("expire-time-setting.fxml")
@Slf4j
public class ExpireTimeSettingController implements Initializable {

    private final DatePicker datePicker = new DatePicker();
    public Spinner<Integer> hourSpinner;
    public Spinner<Integer> minuteSpinner;
    @FXML
    private BorderPane datePane;
    private ExpireTimeSettingEntity entity;
    private Integer currentHour;
    private Integer currentMinute;

    public void setEntity(ExpireTimeSettingEntity expireTimeSettingEntity) {
        this.entity = expireTimeSettingEntity;
        log.info("set entity: {}", entity);
        Preconditions.checkNotNull(entity);
        Integer expirationDay = entity.getExpirationDay();
        Integer expirationTime = entity.getExpirationTime();
        // REPETITION_TYPE repetition = entity.getRepetition();
        if (ExpirationUtil.isNotDefaultValue(expirationDay)) {
            LocalDate localDate = FlakeDateUtil.fromDayId(expirationDay);
            datePicker.setValue(localDate);
        }
        if (ExpirationUtil.isNotDefaultValue(expirationTime)) {
            int minute = expirationTime % 100;
            int hour = expirationTime / 100;

            hourSpinner.getValueFactory().setValue(hour);
            minuteSpinner.getValueFactory().setValue(minute);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        datePane.setCenter(popupContent);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            log.info("set date, old:{}, new:{}", oldValue, newValue);
            if (newValue != null) {
                int pureDayId = FlakeDateUtil.pureDayId(newValue);
                entity.setExpirationDay(pureDayId);
            } else {
                entity.setExpirationDay(null);
            }
        });
        hourSpinner.valueProperty().addListener((observable, oldValue, newHour) -> {
            currentHour = newHour;
            refreshTimeValue();
        });
        minuteSpinner.valueProperty().addListener((observable, oldValue, minuteValue) -> {
            currentMinute = minuteValue;
            refreshTimeValue();
        });
    }


    private void refreshTimeValue() {
        int hour = currentHour == null ? 0 : currentHour;
        int minute = currentMinute == null ? 0 : currentMinute;
        int time = hour * 100 + minute;
        entity.setExpirationTime(time);
        log.info("refreshTimeValue, newHour:{}, newMinute:{}, time: {}", currentHour, currentMinute, time);
    }

    public void onSureButtonClicked(ActionEvent actionEvent) {
        log.info("onSureButtonClicked");
        LocalDate newValue = datePicker.getValue();

        if (newValue != null) {
            int pureDayId = FlakeDateUtil.pureDayId(newValue);
            Integer oldExpirationDay = entity.getExpirationDay();
            if (!Objects.equal(pureDayId, oldExpirationDay)) {
                entity.setExpirationDay(pureDayId);
            }
        } else {
            entity.setExpirationDay(null);
        }
        Button button = (Button) actionEvent.getTarget();
        hidePopOverWindow(button);
    }

    private void hidePopOverWindow(Button button) {
        Scene scene = button.getScene();
        PopOver popOver = (PopOver) scene.getWindow();
        popOver.hide();
    }

    public void onClearButtonClicked(ActionEvent actionEvent) {
        log.info("onClearButtonClicked");
        Button button = (Button) actionEvent.getTarget();

        hidePopOverWindow(button);
    }

    public void onClearTimeButtonAction() {
        hourSpinner.getValueFactory().setValue(null);
        minuteSpinner.getValueFactory().setValue(null);
        currentHour = null;
        currentMinute = null;
    }
}
