package com.github.zuofengzhang.flake.client.controller;

import java.io.IOException;
import java.io.InputStream;

import com.github.zuofengzhang.flake.client.entity.dto.SingleDailyTaskDto;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

/**
 * @author zhangzuofeng1
 */
public class SingleDialyTaskCell extends ListCell<SingleDailyTaskDto> {
    @Override
    protected void updateItem(SingleDailyTaskDto taskDtoListCell, boolean b) {
        FXMLLoader loader = new FXMLLoader();
        InputStream resource = this.getClass().getClassLoader()
                .getResourceAsStream("com/github/zuofengzhang/flake/client/controller/single_daily_task_cell.fxml");
        try {
            GridPane gridBagLayout = loader.load(resource);
            SingleDailyTaskCellController controller = loader.getController();
            controller.setData(taskDtoListCell);
            setGraphic(gridBagLayout);
        } catch (IOException e) {
            e.printStackTrace();
            setGraphic(null);
        }
        super.updateItem(taskDtoListCell, b);
    }
}
