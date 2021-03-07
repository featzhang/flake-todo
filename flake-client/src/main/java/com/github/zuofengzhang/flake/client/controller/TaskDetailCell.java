package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.dto.TaskDetailDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangzuofeng1
 */
public class TaskDetailCell extends ListCell<TaskDetailDto> {
    @Override
    protected void updateItem(TaskDetailDto taskDtoListCell, boolean b) {
        FXMLLoader  loader   = new FXMLLoader();
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream("com/github/zuofengzhang/flake/client/controller/task-detail-cell.fxml");
        try {
            GridPane                 gridBagLayout = loader.load(resource);
            TaskDetailCellController controller    = loader.getController();
            controller.setData(taskDtoListCell);
            setGraphic(gridBagLayout);
        } catch (IOException e) {
            e.printStackTrace();
            setGraphic(null);
        }
        super.updateItem(taskDtoListCell, b);
    }
}
