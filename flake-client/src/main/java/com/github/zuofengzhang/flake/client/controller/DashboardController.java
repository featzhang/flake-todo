package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.entity.TaskEntity;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DashboardController implements Initializable {

    @FXML
    public ComboBox<String> typeComboBox;
    @FXML
    public TextField newContentTextField;
    @FXML
    public Button addButton;
    @FXML
    public ListView<TaskEntity> yesterdayList;
    @FXML
    public ListView<TaskEntity> todayPlanList;
    @FXML
    public ListView<TaskEntity> todayTomatoList;
    @FXML
    public ListView<TaskEntity> summaryList;
    @FXML
    public TreeView<TaskEntity> dateTreeView;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TitledPane yesterdayTitledPane;
    @FXML
    public TitledPane todayPlanTitledPane;
    @FXML
    public TitledPane tomatoPotatoTitledPane;
    @FXML
    public TitledPane todaySummaryTitledPane;
    @FXML
    public TextField mottoTextField;

    public void onNewContentKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String text = newContentTextField.getText();
            if (StringUtils.isNotBlank(text)) {
                TaskType taskType = TaskType.findByCName(typeComboBox.getSelectionModel().getSelectedItem());
                int taskTypeId = taskType.getId();
                titledPaneMap.get(taskTypeId).expandedProperty().set(true);
                ListView<TaskEntity> listView = listViewMap.get(taskTypeId);
                TaskEntity taskEntity = TaskEntity.builder().taskType(taskType).title(text).content(text).createdTime(System.currentTimeMillis()).build();
                listView.getItems().add(taskEntity);
            }
        }
    }

    public void onAddButtonAction(ActionEvent actionEvent) {

    }

    public void onMoveMenu(ActionEvent actionEvent) {

    }

    public void onDeleteMenu(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // type
        List<String> taskTypeNames = Arrays.stream(TaskType.values()).map(TaskType::getCname).collect(Collectors.toList());
        typeComboBox.getItems().addAll(taskTypeNames);
        typeComboBox.getSelectionModel().select(0);
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (!s.equals(t1)) {
                titledPaneMap.get(TaskType.findByCName(t1).getId())
                        .expandedProperty().setValue(true);
            }
        });
        //
        addButton.requestFocus();
        //
        List<ListView<TaskEntity>> listViewList = Arrays.asList(yesterdayList, todayPlanList, todayTomatoList, summaryList);
        listViewMap = listViewList.stream().collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
        // listViewCellFactory
        yesterdayList.setCellFactory(taskEntityListView -> new TaskEntityListCell());
        todayPlanList.setCellFactory(taskEntityListView -> new TaskEntityListCell());
        todayTomatoList.setCellFactory(taskEntityListView -> new TaskEntityListCell());
        summaryList.setCellFactory(taskEntityListView -> new TaskEntityListCell());
        //
        titledPaneMap = Stream.of(yesterdayTitledPane, todayPlanTitledPane, tomatoPotatoTitledPane, todaySummaryTitledPane)
                .collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
        yesterdayTitledPane.expandedProperty().setValue(true);

    }

    private Map<Integer, TitledPane> titledPaneMap;

    private Map<Integer, ListView<TaskEntity>> listViewMap;
}
