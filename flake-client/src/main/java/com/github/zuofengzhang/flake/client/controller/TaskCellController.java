package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.utils.ExpirationUtil;
import com.github.zuofengzhang.flake.client.utils.ImageHolder;
import com.github.zuofengzhang.flake.client.utils.TextUtils;
import com.google.common.base.Joiner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;

@Component
@FxmlView("task-cell.fxml")
@Slf4j
public class TaskCellController implements Initializable {
    @FXML
    public Label tagLabel;
    @FXML
    public Label expirationLabel;
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
            // title
            String title = task.getTitle();
            setTitleAndTag(title);
            task.titleProperty().addListener((observableValue, s, t1) -> {
                setTitleAndTag(t1);
            });

            StoreStatus storeStatus = task.getStoreStatus();
            if (storeStatus == StoreStatus.YES) {
                titleLabel.setStyle("-fx-underline: false;");
            } else {
                titleLabel.setStyle("-fx-underline: true;-fx-background-color: lightgray");
            }

            task.storeStatusProperty().addListener((observableValue, storeStatus1, t1) -> {
                if (t1 == StoreStatus.YES) {
                    titleLabel.setStyle("-fx-underline: false;");
                } else {
                    titleLabel.setStyle("-fx-underline: true;-fx-background-color: lightgray");
                }
            });
            // checkbox
            checkBox.setSelected(task.isFinished());
            task.finishedProperty().bind(checkBox.selectedProperty());
            // iua
            int iua = task.getIua();

            setIuaValue(iua);

            task.iuaProperty().addListener((observableValue, number, t1) -> {
                System.out.println();
                setIuaValue(t1.intValue());
            });

//            task.expirationProperty().addListener((observableValue, oldValue, newValue) -> {
//                log.info("Expiration : {}", newValue);
//                expirationLabel.setText(newValue);
//            });
            Consumer<TaskDto> consumer = (TaskDto t) -> {
                String label = ExpirationUtil.label(t.getExpirationDay(), t.getExpirationTime());
                expirationLabel.setText(label);
            };
            task.expirationDayProperty().addListener((observableValue, s, t1) -> {
                if (!Objects.equals(s, t1)) {
                    consumer.accept(task);
                }
            });
            task.expirationTimeProperty().addListener((observableValue, s, t1) -> {
                if (!Objects.equals(s, t1)) {
                    consumer.accept(task);
                }
            });
            consumer.accept(task);


        } else {
            checkBox.setVisible(false);
            titleLabel.setText("");
            iuaImageView.setImage(null);
            tagLabel.setText("");
            expirationLabel.setText("");
        }
    }

    private void setTitleAndTag(String t1) {
        Pair<List<String>, String> pair = TextUtils.splitTags(t1);
        titleLabel.setText(pair.getValue());
        if (!pair.getKey().isEmpty()) {
            tagLabel.setVisible(true);
            String join = Joiner.on(", ").join(pair.getKey());
            tagLabel.setText(join);
            tagLabel.setStyle("-fx-border-color: red;-fx-border-radius: 5%");
        } else {
            tagLabel.setVisible(false);
            tagLabel.setText("");
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
