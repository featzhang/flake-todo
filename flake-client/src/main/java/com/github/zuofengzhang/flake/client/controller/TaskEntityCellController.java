package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.TaskEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("task-cell.fxml")
public class TaskEntityCellController implements Initializable {
    @FXML
    private Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(TaskEntity taskEntityListCell) {
        if (taskEntityListCell != null) {
            titleLabel.setText(taskEntityListCell.getTitle());
        }
    }
}
