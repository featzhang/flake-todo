package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.TaskDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("task-cell.fxml")
public class TaskCellController implements Initializable {
    @FXML
    private Label titleLabel;

    @FXML
    private CheckBox checkBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setData(TaskDto task) {
        if (task != null) {
            titleLabel.setText(task.getTitle());
            checkBox.setSelected(task.isFinished());
            task.finishedPropertyProperty().bind(checkBox.selectedProperty());
        } else {
            checkBox.setVisible(false);
        }
    }
}
