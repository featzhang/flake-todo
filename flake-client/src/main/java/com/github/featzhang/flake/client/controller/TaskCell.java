package com.github.featzhang.flake.client.controller;

import com.github.featzhang.flake.client.entity.TaskDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangzuofeng1
 */
public class TaskCell extends ListCell<TaskDto> {
    @Override
    protected void updateItem(TaskDto taskDtoListCell, boolean b) {
        FXMLLoader loader = new FXMLLoader();
        InputStream inputStream = TaskCell.class.getClassLoader().getResourceAsStream("com/github/featzhang/flake/client/controller/task-cell.fxml");
        try {
            GridPane gridBagLayout = loader.load(inputStream);
            TaskCellController controller = loader.getController();
            controller.setData(taskDtoListCell);
            setGraphic(gridBagLayout);
        } catch (IOException e) {
            e.printStackTrace();
            setGraphic(null);
        }
        super.updateItem(taskDtoListCell, b);
    }
}
