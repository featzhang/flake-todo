package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.utils.ImageHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    @FXML
    private ImageView iuaImageView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private final ImageHolder imageHolder = ImageHolder.getInstance();

    public void setData(TaskDto task) {
        if (task != null) {
            titleLabel.setText(task.getTitle());
            StoreStatus storeStatus = task.getStoreStatus();
            if (storeStatus == StoreStatus.YES) {
                titleLabel.setStyle("-fx-underline: false;");
            } else {
                titleLabel.setStyle("-fx-underline: true;-fx-background-color: lightgray");
            }
            checkBox.setSelected(task.isFinished());
            task.finishedProperty().bind(checkBox.selectedProperty());

            int iua = task.getIua();

            setIuaValue(iua);

            task.iuaProperty().addListener((observableValue, number, t1) -> {
                System.out.println();
                setIuaValue(t1.intValue());
            });

        } else {
            checkBox.setVisible(false);
            titleLabel.setText("");
            iuaImageView.setImage(null);
        }
    }

    private void setIuaValue(int iua) {
        switch (iua) {
            case 1:
                iuaImageView.setImage(imageHolder.loadImage("a1.png"));
                break;
            case 2:
                iuaImageView.setImage(imageHolder.loadImage("a2.png"));
                break;
            case 3:
                iuaImageView.setImage(imageHolder.loadImage("a3.png"));
                break;
            default:
                iuaImageView.setImage(imageHolder.loadImage("a4.png"));
                break;
        }
    }
}
