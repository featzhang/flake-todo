package com.github.zuofengzhang.flake.client.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.service.TaskService;
import com.github.zuofengzhang.flake.client.utils.DateUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@FxmlView("dashboard.fxml")
public class DashboardController implements Initializable {

    @Resource
    private TaskService taskService;
    @FXML
    public ComboBox<String> typeComboBox;
    @FXML
    public TextField newContentTextField;
    @FXML
    public Button addButton;
    @FXML
    public ListView<TaskDto> yesterdayList;
    @FXML
    public ListView<TaskDto> todayPlanList;
    @FXML
    public ListView<TaskDto> todayTomatoList;
    @FXML
    public ListView<TaskDto> summaryList;
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
    @FXML
    public BorderPane datePickerPane;
    private DatePicker datePicker;

    public void onNewContentKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            doAddNewTask();
        }
    }

    private void doAddNewTask() {
        String text = newContentTextField.getText();
        if (StringUtils.isNotBlank(text)) {
            // get selected dayId
            LocalDate localDate = datePicker.getValue();
            int dayId = DateUtils.dayId(localDate);
            // get taskType
            TaskType taskType = TaskType.findByCName(typeComboBox.getSelectionModel().getSelectedItem());
            int taskTypeId = taskType.getCId();
            //
            TaskDto taskDto = TaskDto.builder()
                    .dayId(dayId)
                    .taskType(taskType)
                    .title(text)
                    .content(text)
                    .createdTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis())
                    .build();
            // add to ListView
            ListView<TaskDto> listView = listViewMap.get(taskTypeId);
            listView.getItems().add(taskDto);
            taskService.insert(taskDto);

            // expand selected TitledPane
            titledPaneMap.get(taskTypeId).expandedProperty().set(true);
            //
            newContentTextField.clear();
        }
    }

    public void onAddButtonAction(ActionEvent actionEvent) {
        doAddNewTask();
    }

    @FXML
    public void onMoveMenu(ActionEvent actionEvent) {
        MenuItem eventSource = (MenuItem) actionEvent.getTarget();
        int targetId = Integer.parseInt(eventSource.getId());
        ContextMenu popup = eventSource.getParentMenu().getParentPopup();
        int sourceId = Integer.parseInt(popup.getId());
        ListView<TaskDto> listView = listViewMap.get(sourceId);
        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        selectedItem.setTaskType(TaskType.findById(targetId));
        if (taskService.updateById(selectedItem) > 0) {
            listView.getItems().remove(selectedItem);
            listViewMap.get(targetId).getItems().add(selectedItem);
        }
    }

    public void onDeleteMenu(ActionEvent actionEvent) {
        EventTarget target = actionEvent.getTarget();
        MenuItem menuItem = (MenuItem) target;
        ContextMenu parentPopup = menuItem.getParentPopup();
        ListView<TaskDto> listView = listViewMap.get(Integer.parseInt(parentPopup.getId()));
        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            taskService.deleteById(selectedItem.getTaskId());
            listView.getItems().remove(selectedItem);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // datepicker
        datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        datePickerPane.setCenter(popupContent);

        // type
        List<String> taskTypeNames = Arrays.stream(TaskType.values()).map(TaskType::getCname).collect(Collectors.toList());
        typeComboBox.getItems().addAll(taskTypeNames);
        typeComboBox.getSelectionModel().select(0);
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (!s.equals(t1)) {
                titledPaneMap.get(TaskType.findByCName(t1).getCId())
                        .expandedProperty().setValue(true);
            }
        });
        //
        addButton.requestFocus();
        //
        List<ListView<TaskDto>> listViewList = Arrays.asList(yesterdayList, todayPlanList, todayTomatoList, summaryList);
        listViewMap = listViewList.stream().collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
        // listViewCellFactory
        yesterdayList.setCellFactory(taskEntityListView -> new TaskCell());
        todayPlanList.setCellFactory(taskEntityListView -> new TaskCell());
        todayTomatoList.setCellFactory(taskEntityListView -> new TaskCell());
        summaryList.setCellFactory(taskEntityListView -> new TaskCell());
        //
        titledPaneMap = Stream.of(yesterdayTitledPane, todayPlanTitledPane, tomatoPotatoTitledPane, todaySummaryTitledPane)
                .collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
        // load data
        loadData();
        // datepick action
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == newValue) {
                return;
            }
            int newDayId = DateUtils.dayId(newValue);
            loadData();
        });
        // init view
        yesterdayTitledPane.expandedProperty().setValue(false);
        yesterdayTitledPane.expandedProperty().setValue(true);
    }

    private void loadData() {
        // loadAllTask
        int dayId = DateUtils.dayId(datePicker.getValue());
        List<TaskDto> list = taskService.findAllTasksByDayId(dayId);

        if (CollectionUtils.isNotEmpty(list)) {
            Map<TaskType, List<TaskDto>> map = list.stream().collect(Collectors.groupingBy(TaskDto::getTaskType));

            for (Map.Entry<TaskType, List<TaskDto>> entry : map.entrySet()) {
                ObservableList<TaskDto> items = listViewMap.get(entry.getKey().getCId()).getItems();
                items.clear();
                items.addAll(entry.getValue());
            }
        } else {
            listViewMap.values().forEach(l -> l.getItems().clear());
        }
    }

    private Map<Integer, TitledPane> titledPaneMap;

    private Map<Integer, ListView<TaskDto>> listViewMap;
}
