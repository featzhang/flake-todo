package com.github.zuofengzhang.flake.client.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.zuofengzhang.flake.client.constraints.FlakeLabel;
import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import com.github.zuofengzhang.flake.client.entity.*;
import com.github.zuofengzhang.flake.client.service.MessageService;
import com.github.zuofengzhang.flake.client.service.TaskService;
import com.github.zuofengzhang.flake.client.utils.DateUtils;
import com.github.zuofengzhang.flake.client.utils.OSValidator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.zuofengzhang.flake.client.constraints.FlakeLabel.*;

/**
 * @author zhangzuofeng1
 */
@Component
@FxmlView("dashboard.fxml")
public class DashboardController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    public TitledPane currentDayTitledPane;

    public Accordion according;
    public Button stopButton;
    public Label timerStatsLabel;
    public Label timerCounterLabel;
    public TextField newContentTextField;
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
    public TitledPane undoneTitledPane;
    public ListView<TaskDto> undoneList;
    public BorderPane rootPane;
    // stat labels
    public Label totalTaskCntLbl;
    public Label todayTaskCntLbl;
    public Label taskPriorityDistributeLbl;
    public Label tomatoCntLbl;
    public Label maxWorkTimeLbl;
    public Label urgentTaskCntLbl;
    public Label completenessLbl;
    public HBox statusHBox;
    public TextField searchTextField;
    public ListView<TaskDto> allTaskListView;
    public Tab tasksTab;
    public Tab todayTab;
    public ListView<TaskDto> nearWeekList;
    public TitledPane nearWeekTitledPane;
    public Label messageStatLabel;
    //
    @Resource
    private TaskService taskService;
    private DatePicker datePicker;
    private int currentTaskId = -1;
    //
    //    private AudioClip mNotify;
    //
    private Map<Integer, TitledPane> allTodayTitledPaneMap;
    private Map<Integer, ListView<TaskDto>> allListViewMap;
    private Timeline timeline;
    private Consumer<ActionEvent> o;

    @Resource
    private FxWeaver fxWeaver;
    private MenuItem moveToTodayPlanMenuItem;
    private MenuItem moveToTomatoPotatoPlanMenuItem;
    private MenuItem moveToTodaySummaryMenuItem;
    private RadioMenuItem iua1MenuItem;
    private RadioMenuItem iua2MenuItem;
    private RadioMenuItem iua3MenuItem;
    private RadioMenuItem iua4MenuItem;
    private Menu moveToMenu;
    private MenuItem undeletedMenuItem;
    private MenuItem deleteMenuItem;
    @Resource
    private ResourceBundle resourceBundle;
    @Resource
    private MessageService messageService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init UI & events
        // init
//        mNotify = new AudioClip(getClass().getResource("/sounds/notify.mp3").toExternalForm());
        // datepicker
        initDataPicker();
        initListView();
        //
        initListViewContextMenu();
        //
        initTimer();
        initMessageToolbar();
        // bindStat
        doBindTaskStat();
        // taskTab action
        loadTaskTabAction();
        loadListViewAction();
        // 切换
        Platform.runLater(() -> according.setExpandedPane(undoneTitledPane));
    }

    private void initMessageToolbar() {
        messageService.addConsumer((message) -> Platform.runLater(() -> messageStatLabel.setText(message)));
    }

    private void loadListViewAction() {
        // 修改为: 点击展开时，重新加载；如何清理掉事件绑定?
        Stream.of(yesterdayTitledPane, todayPlanTitledPane, tomatoPotatoTitledPane, todaySummaryTitledPane, nearWeekTitledPane, undoneTitledPane)
                .forEach(tp -> tp.expandedProperty().addListener((observableValue, aBoolean, newValue) -> {
                    String tpId = tp.getId();
                    log.info("TitledPane expandedProperty changed, id:{}, expanded: {}, titledPane:{} ", tpId, newValue, tp.getText());
                    if (newValue) {
                        loadTitledPaneData(tpId);
                    } else {
                        clearTitledPaneData(tpId);
                    }
                }));
    }

    private void initListView() {
        List<ListView<TaskDto>> listViewList = Arrays.asList(yesterdayList, todayPlanList, todayTomatoList, summaryList, nearWeekList, undoneList);
        allListViewMap = listViewList.stream().collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
        // listViewCellFactory
        nearWeekList.setCellFactory(t -> new TaskCell());
        yesterdayList.setCellFactory(t -> new TaskCell());
        todayPlanList.setCellFactory(t -> new TaskCell());
        todayTomatoList.setCellFactory(t -> new TaskCell());
        summaryList.setCellFactory(t -> new TaskCell());
        // all task
        allTaskListView.setCellFactory(t -> new TaskCell());

        undoneList.setCellFactory(t -> new TaskCell());
        //
        allTodayTitledPaneMap = Stream.of(yesterdayTitledPane, todayPlanTitledPane, tomatoPotatoTitledPane, todaySummaryTitledPane)
                .collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
    }

    private void initDataPicker() {
        datePicker = new DatePicker(LocalDate.now());
//        datePicker.setShowWeekNumbers(true);
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        datePickerPane.setCenter(popupContent);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            onDatePickerChanged(oldValue, newValue);
        });
        LocalDate localDate = datePicker.valueProperty().get();
        setDayTitle(localDate);
    }

    private void initTimer() {
        // init timer
        setTimerText(0);
        setTimerStatus(FlakeLabel.BREAKING);
        setTimerContent("");
        stopButton.setVisible(false);
    }

    private void initListViewContextMenu() {
        ContextMenu liveViewContextMenu = buildListViewContextMenu();

        undoneList.setContextMenu(liveViewContextMenu);
        nearWeekList.setContextMenu(liveViewContextMenu);
        yesterdayList.setContextMenu(liveViewContextMenu);
        todayPlanList.setContextMenu(liveViewContextMenu);
        todayTomatoList.setContextMenu(liveViewContextMenu);
        summaryList.setContextMenu(liveViewContextMenu);
        allTaskListView.setContextMenu(liveViewContextMenu);

        liveViewContextMenu.setOnShowing(this::onCommonListClick);
    }

    private void loadTaskTabAction() {
        tasksTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        // loadTasks
                        List<TaskDto> allTasks = taskService.findAllTasks();
                        ObservableList<TaskDto> items = allTaskListView.getItems();
                        items.clear();
                        items.addAll(allTasks);
                    }
                }
        );
    }

    private void doBindTaskStat() {
        totalTaskCntLbl.textProperty().bind(taskService.totalTaskCntProperty());
        todayTaskCntLbl.textProperty().bind(taskService.todayTaskCntProperty());
        taskPriorityDistributeLbl.textProperty().bind(taskService.taskPriorityDistributeProperty());
        tomatoCntLbl.textProperty().bind(taskService.tomatoCntProperty());
        maxWorkTimeLbl.textProperty().bind(taskService.maxWorkTimeProperty());
        urgentTaskCntLbl.textProperty().bind(taskService.urgentTaskCntProperty());
        completenessLbl.textProperty().bind(taskService.completenessProperty());
        statusHBox.setOnMouseClicked((e) -> statusHBox.requestFocus());
    }

    public void onNewContentKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            doAddNewTask();
        }
    }

    private void doAddNewTask() {
        String text = newContentTextField.getText();
        log.info("newContentTextField: {}", text);
        if (StringUtils.isNotBlank(text)) {
            // get selected dayId
            LocalDate localDate = datePicker.getValue();
            int dayId = DateUtils.dayId(localDate);
            // get taskType
            int taskTypeId = TaskType.TODAY_PLAN.getCId();
            //
            TaskDto taskDto = TaskDto.builder()
                    .dayId(dayId)
                    .taskType(TaskType.TODAY_PLAN)
                    .title(text)
                    .content("")
                    .createdTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis())
                    .importanceUrgencyAxis(4)
                    .finished(false)
                    .storeStatus(StoreStatus.YES)
                    .build();
            taskService.insert(taskDto);
            // bind db action
            onTaskDataChange(taskDto);

            // expand selected TitledPane

            BooleanProperty expandedProperty = allTodayTitledPaneMap.get(taskTypeId).expandedProperty();
            expandedProperty.set(true);

            reloadCurrentTitlePane();
            //
            newContentTextField.clear();
        }
    }

    private void onTaskDataChange(TaskDto taskDto) {
        log.info("addAction onDataChange, taskId: {}", taskDto.getTaskId());
        taskDto.finishedProperty().addListener((observableValue, s, t1) -> {
            log.info("update finished status:  @{},{}->{}", taskDto.getTaskId(), s, t1);
            taskService.updateById(taskDto);
        });
        taskDto.iuaProperty().addListener(((observableValue, s, t1) -> {
            log.info("update iua value:  @{},{}->{}", taskDto.getTaskId(), s, t1);
            taskService.updateById(taskDto);
        }));
        taskDto.attachmentProperty().addListener((observableValue, s, t1) -> {
            log.info("update attachment: @{},{}->{}", taskDto.getTaskId(), s, t1);
            taskService.updateById(taskDto);
        });
        taskDto.titleProperty().addListener((observableValue, s, t1) -> {
            log.info("update title: @{},{}->{}", taskDto.getTaskId(), s, t1);
            taskService.updateById(taskDto);
        });
        taskDto.contentProperty().addListener((observableValue, s, t1) -> {
            log.info("update content: @{},{}->{}", taskDto.getTaskId(), s, t1);
            taskService.updateById(taskDto);
        });
    }

    public void onAddButtonAction(ActionEvent actionEvent) {
        doAddNewTask();
    }

    public void onMoveMenu(ActionEvent actionEvent) {
        MenuItem eventSource = (MenuItem) actionEvent.getTarget();
        int targetId = Integer.parseInt(eventSource.getId());

        ListView<TaskDto> selectedListView = doGetSelectedListView();
        TaskDto selectedItem = selectedListView.getSelectionModel().getSelectedItem();

        selectedItem.setTaskType(TaskType.findById(targetId));
        if (taskService.updateById(selectedItem) > 0) {
            selectedListView.getItems().remove(selectedItem);
//            listViewMap.get(targetId).getItems().add(selectedItem);
        }
    }

    public void onDeleteMenu(ActionEvent actionEvent) {
        TaskDto selectedItem = doGetSelectedTask();
        if (selectedItem != null) {
            taskService.deleteById(selectedItem);
            reloadCurrentTitlePane();
        }
    }

    private void onCommonListClick(WindowEvent mouseEvent) {
        initListContextMenu();
        int titledPaneId = Integer.parseInt(according.getExpandedPane().getId());
        ListView<TaskDto> listView = allListViewMap.get(titledPaneId);
        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        undeletedMenuItem.setVisible(selectedItem.getStoreStatus() == StoreStatus.NO);
        deleteMenuItem.setVisible(selectedItem.getStoreStatus() == StoreStatus.YES);
        if (listView == undoneList) {
            moveToMenu.setVisible(false);
        } else {
            TaskType taskType = TaskType.findById(titledPaneId);
            switch (taskType) {
                case TODAY_PLAN:
                    moveToTodayPlanMenuItem.setVisible(false);
                    break;
                case TODAY_SUMMARY:
                    moveToTodaySummaryMenuItem.setVisible(false);
                    break;
                case TOMATO_POTATO:
                    moveToTomatoPotatoPlanMenuItem.setVisible(false);
                    break;
//                case YESTERDAY_REVIEW:
//                    moveToYesterdayReviewMenuItem.setVisible(false);
//                    break;
                default:
            }
        }

        int iua = selectedItem.getIua();
        switch (iua) {
            case 1:
                iua1MenuItem.setSelected(true);
                break;
            case 2:
                iua2MenuItem.setSelected(true);
                break;
            case 3:
                iua3MenuItem.setSelected(true);
                break;
            case 4:
            default:
                iua4MenuItem.setSelected(true);
                break;
        }
    }

    private void initListContextMenu() {
        moveToTodaySummaryMenuItem.setVisible(true);
        moveToTodayPlanMenuItem.setVisible(true);
//        moveToYesterdayReviewMenuItem.setVisible(true);
        moveToTomatoPotatoPlanMenuItem.setVisible(true);
        moveToMenu.setVisible(true);
    }

    private ContextMenu buildListViewContextMenu() {
        ContextMenu liveViewContextMenu = new ContextMenu();
        // focus
        MenuItem focusMenuItem = new MenuItem(FOCUS);
        focusMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+F"));
        focusMenuItem.setOnAction(this::onStartTimer);
        // move_to
        // <MenuItem id="1" mnemonicParsing="false" onAction="#onMoveMenu"
        //                                                                  text="%label_yesterday_review"/>
        moveToMenu = new Menu(MENU_MOVE_TO);
//        moveToYesterdayReviewMenuItem  = createMenuItem("1", "label_yesterday_review", this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+1"));
        moveToTodayPlanMenuItem = createMenuItem("2", TODAY_PLAN, this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+1"));
        moveToTomatoPotatoPlanMenuItem = createMenuItem("3", TOMATO_POTATO, this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+2"));
        moveToTodaySummaryMenuItem = createMenuItem("4", TODAY_SUMMARY, this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+3"));
        moveToMenu.getItems().addAll(/*moveToYesterdayReviewMenuItem,*/ moveToTodayPlanMenuItem, moveToTomatoPotatoPlanMenuItem, moveToTodaySummaryMenuItem);
        // menu_importance_urgency_axis
        Menu iuaMenu = new Menu(MENU_IMPORTANCE_URGENCY_AXIS);
        ToggleGroup toggleGroup = new ToggleGroup();
        iua1MenuItem = createRadioMenuItem("1", IMPORTANCE_URGENCY, toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+1"));
        iua2MenuItem = createRadioMenuItem("2", NOT_IMPORTANCE_BUT_URGENCY, toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+2"));
        iua3MenuItem = createRadioMenuItem("3", IMPORTANCE_BUT_NOT_URGENCY, toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+3"));
        iua4MenuItem = createRadioMenuItem("4", NOT_IMPORTANCE_NOT_URGENCY, toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+4"));
        iuaMenu.getItems().addAll(iua1MenuItem, iua2MenuItem, iua3MenuItem, iua4MenuItem);
        // menu_order
        Menu orderMenu = new Menu(MENU_ORDER);
        MenuItem moveOrderTopMenuItem = createMenuItem("0", MENU_MOVE_TOP, this::onOrderMoveTopMenu, KeyCombination.keyCombination("Meta+T"));
        MenuItem moveOrderUpMenuItem = createMenuItem("0", MENU_MOVE_UP, this::onOrderMoveUpMenu, KeyCombination.keyCombination("Meta+K"));
        MenuItem moveOrderDownMenuItem = createMenuItem("0", MENU_MOVE_DOWN, this::onOrderMoveDownMenu, KeyCombination.keyCombination("Meta+J"));
        orderMenu.getItems().addAll(moveOrderTopMenuItem, moveOrderUpMenuItem, moveOrderDownMenuItem);

        // delete or undeleted
        Menu deleteOrUndeletedMenu = new Menu(MENU_DELETE_RECOVERY);
        deleteMenuItem = createMenuItem("0", MENU_DELETE, this::onDeleteMenu, KeyCombination.keyCombination("Meta+X"));
        undeletedMenuItem = createMenuItem("0", MENU_RECOVERY, this::onUndeleteMenu, KeyCombination.keyCombination("Meta+Z"));
        deleteOrUndeletedMenu.getItems().addAll(deleteMenuItem, undeletedMenuItem);

        // tools
        Menu toolMenu = new Menu(MENU_TOOLS);
        MenuItem menuSearch = createMenuItem("1", MENU_SEARCH, this::onSearchTaskMenu, KeyCombination.keyCombination("Meta+Alt+F"));
        toolMenu.getItems().add(menuSearch);


        liveViewContextMenu.getItems().addAll(focusMenuItem, moveToMenu, iuaMenu, orderMenu, deleteOrUndeletedMenu, new SeparatorMenuItem(), toolMenu);
        return liveViewContextMenu;
    }

    private void onSearchTaskMenu(ActionEvent actionEvent) {
        ListView<TaskDto> listView = allListViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));
        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        String title = selectedItem.getTitle();
        String url = "http://www.bing.com/search?q=" + title;
        if (Desktop.isDesktopSupported()) {
            try {
                URI uri = new URI(url);
                Desktop.getDesktop().browse(uri);
            } catch (Exception e) {
                log.error("", e);
            }
        } else {
            if (OSValidator.IS_MAC) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("open", url);
                try {
                    processBuilder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (OSValidator.IS_WINDOWS) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("start", "", url);
                try {
                    Process start = processBuilder.start();
                    int i = start.waitFor();
                    log.info("execute result: {}", i);
                } catch (IOException | InterruptedException e) {
                    log.error("", e);
                }
            }
        }
    }

    private RadioMenuItem createRadioMenuItem(String id, String label, ToggleGroup toggleGroup, EventHandler<ActionEvent> onMoveMenu, KeyCombination keyCombination) {
        RadioMenuItem item = new RadioMenuItem(label);
        item.setId(id);
        item.setToggleGroup(toggleGroup);
        item.setOnAction(onMoveMenu);
        item.setAccelerator(keyCombination);
        return item;
    }

    private MenuItem createMenuItem(String id, String label, EventHandler<ActionEvent> onMoveMenu, KeyCombination keyCombination) {
        MenuItem moveToTodayPlanMenuItem = new MenuItem(label);
        moveToTodayPlanMenuItem.setId(id);
        moveToTodayPlanMenuItem.setOnAction(onMoveMenu);
        moveToTodayPlanMenuItem.setAccelerator(keyCombination);
        return moveToTodayPlanMenuItem;
    }

    private void clearTitledPaneData(String id) {
        int titledPaneId = Integer.parseInt(id);
        TaskType taskType = TaskType.findById(titledPaneId);
        // undone
        if (taskType == null) {
            undoneList.getItems().forEach(task -> {
                task.finishedProperty().unbind();
                task.iuaProperty().unbind();
            });
            undoneList.getItems().clear();
        } else {
            // loadDayTask
            ListView<TaskDto> listView = allListViewMap.get(titledPaneId);
            ObservableList<TaskDto> items = listView.getItems();
            items.forEach(task -> {
                task.finishedProperty().unbind();
                task.iuaProperty().unbind();
            });
            items.clear();
        }
    }

    private void loadTitledPaneData(String id) {
        int titledPaneId = Integer.parseInt(id);
        TaskType taskType = TaskType.findById(titledPaneId);
        LocalDate localDate = datePicker.getValue();
        int dayId = setDayTitle(localDate);
        // undone
        if (taskType == null) {
            log.info("Load all undone tasks.");
            List<TaskDto> undoneTasks = taskService.findAllUndoneTasks();
            // remove undoneList task bind
            undoneList.getItems().forEach(task -> {
                task.finishedProperty().unbind();
                task.iuaProperty().unbind();
            });
            undoneList.getItems().clear();
            // add new task
            if (!CollectionUtils.isEmpty(undoneTasks)) {
                undoneTasks.forEach(this::onTaskDataChange);
                undoneList.getItems().addAll(undoneTasks);
            }
        } else {
            // loadDayTask
            log.info("Load today tasks: {}", taskType);
            List<TaskDto> tasks = taskService.findTasksByDayIdAndType(dayId, taskType);
            ListView<TaskDto> listView = allListViewMap.get(titledPaneId);
            ObservableList<TaskDto> items = listView.getItems();
            items.forEach(task -> {
                task.finishedProperty().unbind();
                task.iuaProperty().unbind();
            });
            items.clear();
            tasks.forEach(this::onTaskDataChange);
            items.addAll(tasks);
        }
    }

    private int setDayTitle(LocalDate localDate) {
        int dayId = DateUtils.dayId(localDate);
        currentDayTitledPane.setText(FlakeLabel.CURRENT_DAY + " " + dayId);
        return dayId;
    }

    private void onDatePickerChanged(LocalDate oldValue, LocalDate newValue) {
        if (oldValue == newValue) {
            return;
        }

        reloadCurrentTitlePane();
//        loadData();
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
        allListViewMap.get(TaskType.TOMATO_POTATO.getCId()).getItems().add(newTask);
    }

    /**
     * 休息结束
     */
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

    public void onStartTimer(ActionEvent actionEvent) {
        ContextMenu parentPopup = ((MenuItem) actionEvent.getTarget()).getParentPopup();
        ListView<TaskDto> listView = allListViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));
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

    private void setTimerStatus(String timeForABreak) {
        timerStatsLabel.setText(timeForABreak);
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
        Node node = (Node) actionEvent.getSource();
        Scene scene1 = node.getScene();
        Stage primaryStage = (Stage) scene1.getWindow();
        BorderPane borderPane = fxWeaver.loadView(SettingsController.class, resourceBundle);
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle(SETTING);
        stage.setResizable(false);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        reloadCurrentTitlePane();
    }

    private void reloadCurrentTitlePane() {
        String id = according.getExpandedPane().getId();
        clearTitledPaneData(id);
        loadTitledPaneData(id);
    }

    public void onSetIuaMenu(ActionEvent actionEvent) {
        EventTarget target = actionEvent.getTarget();
        RadioMenuItem menuItem = (RadioMenuItem) target;
        ContextMenu parentPopup = menuItem.getParentPopup();
        // undoneListView id is 0
        String id = parentPopup.getId();
        //
        int targetIuaId = Integer.parseInt(menuItem.getId());
        TaskDto selectedItem = doGetSelectedTask();
        if (selectedItem != null) {
            log.info("Set task iua, task: {},uia: {}", selectedItem.getTaskId(), targetIuaId);
            int iua = selectedItem.getIua();
            if (targetIuaId != iua) {
                selectedItem.setIua(targetIuaId);
                log.info("set iua : {} -> {} ,{}", iua, targetIuaId, selectedItem);
                reloadCurrentTitlePane();
            }
        } else {
            log.info("Can not find selected task.");
        }
    }

    private TaskDto doGetSelectedTask() {
        ListView<TaskDto> listView = doGetSelectedListView();
        return listView.getSelectionModel().getSelectedItem();
    }

    private ListView<TaskDto> doGetSelectedListView() {
        if (todayTab.isSelected()) {
            // today tab is selected
            TitledPane expandedPane = according.getExpandedPane();
            return allListViewMap.get(Integer.parseInt(expandedPane.getId()));
        } else if (tasksTab.isSelected()) {
            return allTaskListView;
        }
        throw new IllegalStateException("Can not  found selected task.");
    }

    public void onTaskClicked(MouseEvent mouseEvent) {
        TaskDto selectedTask = doGetSelectedTask();
        if (selectedTask != null) {
            if (mouseEvent.getClickCount() == 2) {
                Node node = (Node) mouseEvent.getSource();
                Scene nodeScene = node.getScene();
                Stage primaryStage = (Stage) nodeScene.getWindow();

                FxControllerAndView<TaskDetailController, GridPane> controllerAndView = fxWeaver.load(TaskDetailController.class, resourceBundle);
                GridPane borderPane = controllerAndView.getView().get();
                TaskDetailController controller = controllerAndView.getController();
                controller.setData(selectedTask);
                Scene scene = new Scene(borderPane);
                Stage stage = new Stage();
                stage.setResizable(true);
                stage.initOwner(primaryStage);
                selectedTask.titleProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        stage.setTitle("[" + FlakeLabel.TASK_EDIT + "] - " + newValue);
                    }
                });
                stage.setTitle("[" + FlakeLabel.TASK_EDIT + "] - " + selectedTask.getTitle());
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void onUndeleteMenu(ActionEvent actionEvent) {
        TaskDto selectedItem = doGetSelectedTask();
        if (selectedItem != null) {
            taskService.restoreById(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onOrderMoveTopMenu(ActionEvent actionEvent) {
        TaskDto selectedItem = doGetSelectedTask();
        if (selectedItem != null) {
            taskService.moveOrderTop(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onOrderMoveUpMenu(ActionEvent actionEvent) {
        TaskDto selectedItem = doGetSelectedTask();
        if (selectedItem != null) {
            taskService.moveOrderUp(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onOrderMoveDownMenu(ActionEvent actionEvent) {
        TaskDto selectedItem = doGetSelectedTask();
        if (selectedItem != null) {
            taskService.moveOrderDown(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onAddMoreButtonAction(ActionEvent actionEvent) {
    }

    public void onSearchTextKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            doSearchText();
        }
    }

    public void onSearchButtonAction(ActionEvent actionEvent) {
        doSearchText();
    }

    private void doSearchText() {
        String searchText = searchTextField.getText();
        allTaskListView.getItems().clear();
        if (!StringUtils.isEmpty(searchText)) {
            try {
                List<TaskDto> search = taskService.search(searchText);
                allTaskListView.getItems().addAll(search);
            } catch (IOException | ParseException e) {
                log.error("", e);
            }
        }
    }

    public void onAllTodosIconEntered(MouseEvent mouseEvent) {
        Label button = (Label) mouseEvent.getTarget();
        PopOver popOver = new PopOver();
        GridPane content = fxWeaver.loadView(StatPopOverViewController.class, resourceBundle);
        popOver.setContentNode(content);
        popOver.setAutoHide(true);
        popOver.setAutoFix(true);
        popOver.setHideOnEscape(true);
        popOver.setDetachable(true);
        popOver.setDetached(false);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        popOver.show(button);
    }
}
