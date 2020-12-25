package com.github.zuofengzhang.flake.client.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.zuofengzhang.flake.client.constraints.FlakeLabel;
import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.entity.TimerActionType;
import com.github.zuofengzhang.flake.client.entity.TimerStatus;
import com.github.zuofengzhang.flake.client.service.TaskService;
import com.github.zuofengzhang.flake.client.utils.DateUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.Notifications;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangzuofeng1
 */
@Component
@FxmlView("dashboard.fxml")
@Slf4j
public class DashboardController implements Initializable {


    public TitledPane titledPane;

    public Button stopButton;
    public Label timerStatsLabel;
    public Label timerCounterLabel;
    public ComboBox<String> typeComboBox;
    public TextField newContentTextField;
    public Button addButton;
    public ListView<TaskDto> yesterdayList;
    public ListView<TaskDto> todayPlanList;
    public ListView<TaskDto> todayTomatoList;
    public ListView<TaskDto> summaryList;
    public TitledPane yesterdayTitledPane;
    public TitledPane todayPlanTitledPane;
    public TitledPane tomatoPotatoTitledPane;
    public TitledPane todaySummaryTitledPane;
    public TextField mottoTextField;
    public BorderPane datePickerPane;
    public Label workContentLabel;
    @Resource
    private TaskService taskService;
    private DatePicker datePicker;
    private int currentTaskId = -1;
    //    private AudioClip mNotify;
    private Map<Integer, TitledPane> titledPaneMap;
    private Map<Integer, ListView<TaskDto>> listViewMap;
    private Timeline timeline;

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
            assert taskType != null;
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
        // init
//        mNotify = new AudioClip(getClass().getResource("/sounds/notify.mp3").toExternalForm());

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
                titledPaneMap.get(Objects.requireNonNull(TaskType.findByCName(t1)).getCId())
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
        // datePick action
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == newValue) {
                return;
            }

            loadData();
        });
        // init view
        yesterdayTitledPane.expandedProperty().setValue(false);
        yesterdayTitledPane.expandedProperty().setValue(true);
        // init timer
        setTimerText(0);
        setTimerStatus(FlakeLabel.BREAKING);
        setTimerContent("");
        stopButton.setVisible(false);
    }

    private void setTimerContent(String s) {
        this.workContentLabel.setText(s);
    }

    public void setTimerText(long remainingSeconds) {
        int hours = (int) (remainingSeconds / 60 / 60);
        int minutes = (int) ((remainingSeconds / 60) % 60);
        int seconds = (int) (remainingSeconds % 60);

        //Show only minute and second if hour is not available
        if (hours <= 0) {
            setTimerText(String.format("%02d:%02d", minutes, seconds));
        } else {
            setTimerText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }
    }

    private void setTimerText(String s) {
        timerCounterLabel.setText(s);
    }

    private void loadData() {
        // loadAllTask
        int dayId = DateUtils.dayId(datePicker.getValue());
        titledPane.setText(FlakeLabel.CURRENT_DAY + " " + dayId);
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

    public void onAddMottoTextField(ActionEvent actionEvent) {
        mottoTextField.setText("");
    }

    public void initTimerAction(TimerActionType type) {

        TimerStatus timerStatus = new TimerStatus(type, System.currentTimeMillis());

        setTimerStatus(timerStatus.getType().getDisplayName());
        if (timerStatus.getType() == TimerActionType.FOCUS) {
            timerStatus.setRemainingSeconds(FlakeSettings.getInstance().getFocusTimeInSeconds());
        } else if (timerStatus.getType() == TimerActionType.BREAK) {
        }
        setTimerText(timerStatus.getRemainingSeconds());
        timeline = new Timeline();
        timeline.setCycleCount((int) timerStatus.getRemainingSeconds());
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            timerStatus.countDown();
            setTimerText(timerStatus.getRemainingSeconds());
        }));

        timeline.setOnFinished(event -> {
//            mNotify.play();
            Notifications.create().title(FlakeLabel.TIME_TO_WEAK).text("").hideAfter(Duration.minutes(5)).showWarning();
            doAddNewWorkLog(timerStatus);
            currentTaskId = -1;
            if (timerStatus.getType() == TimerActionType.FOCUS) {
                takeBreakNotification();
            }
            initTimerAction(timerStatus.getType() == TimerActionType.FOCUS ?
                    TimerActionType.BREAK : TimerActionType.FOCUS);
            stopButton.setVisible(false);
        });
    }

    private void doAddNewWorkLog(TimerStatus timerStatus) {
        int taskId = currentTaskId;
        log.info("add work log : {}", taskId);
        TaskDto taskDto = taskService.findById(taskId);
        TaskDto newTask = TaskDto.builder()
                .title(taskDto.getTitle())
                .content(taskDto.getContent())
                .taskType(TaskType.TOMATO_POTATO)
                .endTime(System.currentTimeMillis())
                .startTime(timerStatus.getStartTime())
                .dayId(taskDto.getDayId())
                .fullTomato(true)
                .build();
        taskService.insert(newTask);
        // how get the newest id
        listViewMap.get(TaskType.TOMATO_POTATO.getCId()).getItems().add(newTask);
    }

    public void takeBreakNotification() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Get the Stage.
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // Add a custom icon.
//        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Great! Now Take a Break");
        alert.setContentText("You have worked 30 min long. Now you should take a at least 5 minutes break to relax yourself.");

        alert.show();
        //alert.showAndWait();
        System.out.println("take a break notification");
    }

    private void setTimerStatus(String timeForABreak) {
        timerStatsLabel.setText(timeForABreak);
    }

    public void onStartTimer(ActionEvent actionEvent) {
        ContextMenu parentPopup = ((MenuItem) actionEvent.getTarget()).getParentPopup();
        ListView<TaskDto> listView = listViewMap.get(Integer.parseInt(parentPopup.getId()));
        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        //
        int newTaskId = selectedItem.getTaskId();
        //
        stopButton.setVisible(true);
        //
        if (currentTaskId == newTaskId) {
            log.info("the same task is running");
            return;
        }
        if (currentTaskId != -1) {
            log.info("stop unfinished task: {}, start new task: {}", currentTaskId, newTaskId);
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                log.info("stop timeLine..");
                timeline.stop();
                initTimerAction(TimerActionType.FOCUS);
            }
        } else {
            initTimerAction(TimerActionType.FOCUS);
        }

        this.currentTaskId = newTaskId;
        timeline.play();
        stopButton.setVisible(true);
        getTimerStatus();
        setTimerContent(selectedItem.getTitle());
    }

    //debugging purpose
    private Animation.Status getTimerStatus() {
        Animation.Status mStatus = timeline.getStatus();
        System.out.println(mStatus);
        return mStatus;
    }

    public void onStopTimer(ActionEvent actionEvent) {
        log.info("stop timer");
        currentTaskId = -1;
        timeline.stop();
        stopButton.setVisible(false);
        setTimerText(0);
        getTimerStatus();
        setTimerStatus(FlakeLabel.BREAKING);
        setTimerContent("");
    }

    public void onSettings(ActionEvent actionEvent) {
        o.accept(actionEvent);
    }

    private Consumer<ActionEvent> o;

    public void setOnSetting(Consumer<ActionEvent> o) {
        this.o = o;
    }
}
