package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.TaskEntity;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;

public class TaskEntityListCell extends ListCell<TaskEntity> {
    @Override
    protected void updateItem(TaskEntity taskEntityListCell, boolean b) {
        FXMLLoader loader = new FXMLLoader();
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream("fxml/taskEntityCell.fxml");
        try {
            GridPane gridBagLayout = loader.load(resource);
            TaskEntityCellController controller = loader.getController();
            controller.setData(taskEntityListCell);
            setGraphic(gridBagLayout);
        } catch (IOException e) {
            e.printStackTrace();
            setGraphic(null);
        }
        super.updateItem(taskEntityListCell, b);
    }
}
