package com.github.zuofengzhang.flake.client.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.zuofengzhang.flake.client.constraints.FlakeLabel;
import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import com.github.zuofengzhang.flake.client.entity.*;
import com.github.zuofengzhang.flake.client.service.TaskService;
import com.github.zuofengzhang.flake.client.utils.DateUtils;
import com.github.zuofengzhang.flake.client.utils.OSValidator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.zuofengzhang.flake.client.constraints.FlakeLabel.label;

/**
 * @author zhangzuofeng1
 */
@Component
@FxmlView("dashboard.fxml")
public class DashboardController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    public TitledPane currentDayTitledPane;

    public  Accordion                       according;
    public  Button                          stopButton;
    public  Label                           timerStatsLabel;
    public  Label                           timerCounterLabel;
    public  ComboBox<String>                typeComboBox;
    public  TextField                       newContentTextField;
    public  ListView<TaskDto>               yesterdayList;
    public  ListView<TaskDto>               todayPlanList;
    public  ListView<TaskDto>               todayTomatoList;
    public  ListView<TaskDto>               summaryList;
    public  TitledPane                      yesterdayTitledPane;
    public  TitledPane                      todayPlanTitledPane;
    public  TitledPane                      tomatoPotatoTitledPane;
    public  TitledPane                      todaySummaryTitledPane;
    public  TextField                       mottoTextField;
    public  BorderPane                      datePickerPane;
    public  Label                           workContentLabel;
    public  TitledPane                      undoneTitledPane;
    public  ListView<TaskDto>               undoneList;
    public  BorderPane                      rootPane;
    // stat labels
    public  Label                           totalTaskCntLbl;
    public  Label                           todayTaskCntLbl;
    public  Label                           taskPriorityDistributeLbl;
    public  Label                           tomatoCntLbl;
    public  Label                           maxWorkTimeLbl;
    public  Label                           urgentTaskCntLbl;
    public  Label                           completenessLbl;
    public  HBox                            statusHBox;
    //
    @Resource
    private TaskService                     taskService;
    private DatePicker                      datePicker;
    private int                             currentTaskId = -1;
    //
    //    private AudioClip mNotify;
    //
    private Map<Integer, TitledPane>        titledPaneMap;
    private Map<Integer, ListView<TaskDto>> listViewMap;
    private Timeline                        timeline;
    private Consumer<ActionEvent>           o;

    @Resource
    private FxWeaver      fxWeaver;
    private MenuItem      moveToYesterdayReviewMenuItem;
    private MenuItem      moveToTodayPlanMenuItem;
    private MenuItem      moveToTomatoPotatoPlanMenuItem;
    private MenuItem      moveToTodaySummaryMenuItem;
    private RadioMenuItem iua1MenuItem;
    private RadioMenuItem iua2MenuItem;
    private RadioMenuItem iua3MenuItem;
    private RadioMenuItem iua4MenuItem;
    private ContextMenu   liveViewContextMenu;
    private Menu          moveToMenu;
    private MenuItem      undeletedMenuItem;
    private MenuItem      deleteMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init UI & events
        // init
//        mNotify = new AudioClip(getClass().getResource("/sounds/notify.mp3").toExternalForm());

        // datepicker
        datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node           popupContent   = datePickerSkin.getPopupContent();
        datePickerPane.setCenter(popupContent);

        // type
        List<String> taskTypeNames
                = Arrays
                .stream(TaskType.values())
                .map(TaskType::getCname)
                .collect(Collectors.toList());
        typeComboBox.getItems().addAll(taskTypeNames);
        typeComboBox.getSelectionModel().select(0);
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (!s.equals(t1)) {
                titledPaneMap.get(Objects.requireNonNull(TaskType.findByCName(t1)).getCId())
                        .expandedProperty().setValue(true);
            }
        });
        //


        //
        List<ListView<TaskDto>> listViewList = Arrays.asList(yesterdayList, todayPlanList, todayTomatoList, summaryList, undoneList);
        listViewMap = listViewList.stream().collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
        // listViewCellFactory
        yesterdayList.setCellFactory(t -> new TaskCell());
        todayPlanList.setCellFactory(t -> new TaskCell());
        todayTomatoList.setCellFactory(t -> new TaskCell());
        summaryList.setCellFactory(t -> new TaskCell());
        undoneList.setCellFactory(t -> new TaskCell());
        //
        titledPaneMap = Stream.of(yesterdayTitledPane, todayPlanTitledPane, tomatoPotatoTitledPane, todaySummaryTitledPane)
                .collect(Collectors.toMap(s -> Integer.parseInt(s.getId()), s -> s));
        // 修改为: 点击展开时，重新加载；如何清理掉事件绑定?
        Stream.of(yesterdayTitledPane, todayPlanTitledPane, tomatoPotatoTitledPane, todaySummaryTitledPane, undoneTitledPane)
                .forEach(tp -> tp.expandedProperty().addListener((observableValue, aBoolean, newValue) -> {
                    int tpId = Integer.parseInt(tp.getId());
                    if (newValue) {
                        loadTitledPaneData(tpId);
                    } else {
                        clearTitledPaneData(tpId);
                    }
                }));

        // load data
//        loadData();
        // datePick action
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            onDatePickerChanged(oldValue, newValue);
        });

        // init timer
        setTimerText(0);
        setTimerStatus(FlakeLabel.BREAKING);
        setTimerContent("");
        stopButton.setVisible(false);

        // init view
        // expanded undoneTitledPane
        according.setExpandedPane(undoneTitledPane);

        // loadData
        loadTitledPaneData(Integer.parseInt(undoneTitledPane.getId()));
        //
        buildListViewContextMenu();
        //

        yesterdayList.setContextMenu(liveViewContextMenu);
        todayPlanList.setContextMenu(liveViewContextMenu);
        todayTomatoList.setContextMenu(liveViewContextMenu);
        summaryList.setContextMenu(liveViewContextMenu);
        undoneList.setContextMenu(liveViewContextMenu);
        liveViewContextMenu.setOnShowing(this::onCommonListClick);

        // bindStat
        doBindTaskStat();
    }

    private void doBindTaskStat() {
        totalTaskCntLbl.textProperty().bind(taskService.totalTaskCntProperty());
        todayTaskCntLbl.textProperty().bind(taskService.todayTaskCntProperty());
        taskPriorityDistributeLbl.textProperty().bind(taskService.taskPriorityDistributeProperty());
        tomatoCntLbl.textProperty().bind(taskService.tomatoCntProperty());
        maxWorkTimeLbl.textProperty().bind(taskService.maxWorkTimeProperty());
        urgentTaskCntLbl.textProperty().bind(taskService.urgentTaskCntProperty());
        completenessLbl.textProperty().bind(taskService.completenessProperty());
        statusHBox.setOnMouseClicked((e) ->
        {
            statusHBox.requestFocus();
        });

        // use different backgrounds for focused and unfocused states
        statusHBox.backgroundProperty().bind(Bindings
                .when(statusHBox.focusedProperty())
                .then(statusHBoxFocusBackground)
                .otherwise(statusHBoxUnfocusBackground)
        );


    }

    private final Background statusHBoxUnfocusBackground = Background.EMPTY;
    private final Background statusHBoxFocusBackground   = new Background(
            new BackgroundFill(
                    new LinearGradient(0, 0, 0, 1, true,
                            CycleMethod.NO_CYCLE,
                            new Stop(0, Color.web("#4568DC")),
                            new Stop(1, Color.web("#B06AB3"))
                    ), CornerRadii.EMPTY, Insets.EMPTY
            ));

    /**
     * 初始化typePie
     */
    private void doInitTypePie() {

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
            int       dayId     = DateUtils.dayId(localDate);
            // get taskType
            TaskType taskType = TaskType.findByCName(typeComboBox.getSelectionModel().getSelectedItem());
            assert taskType != null;
            int taskTypeId = taskType.getCId();
            //
            TaskDto taskDto = TaskDto.builder()
                    .dayId(dayId)
                    .taskType(taskType)
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
            BooleanProperty expandedProperty = titledPaneMap.get(taskTypeId).expandedProperty();
            if (expandedProperty.get()) {
                reloadCurrentTitlePane();
            }
            expandedProperty.set(true);
            //
//            newContentTextField.clear();
        }
    }


    private void onTaskDataChange(TaskDto taskDto) {
        log.info("onDataChange: {}", taskDto.getTaskId());
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
        MenuItem          eventSource  = (MenuItem) actionEvent.getTarget();
        int               targetId     = Integer.parseInt(eventSource.getId());
        ContextMenu       popup        = eventSource.getParentMenu().getParentPopup();
        int               sourceId     = Integer.parseInt(according.getExpandedPane().getId());
        ListView<TaskDto> listView     = listViewMap.get(sourceId);
        TaskDto           selectedItem = listView.getSelectionModel().getSelectedItem();
        selectedItem.setTaskType(TaskType.findById(targetId));
        if (taskService.updateById(selectedItem) > 0) {
            listView.getItems().remove(selectedItem);
//            listViewMap.get(targetId).getItems().add(selectedItem);
        }
    }

    public void onDeleteMenu(ActionEvent actionEvent) {
        EventTarget       target      = actionEvent.getTarget();
        MenuItem          menuItem    = (MenuItem) target;
        ContextMenu       parentPopup = menuItem.getParentMenu().getParentPopup();
        ListView<TaskDto> listView    = listViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));

        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            taskService.deleteById(selectedItem);
            reloadCurrentTitlePane();
        }
    }


    private void onCommonListClick(WindowEvent mouseEvent) {
        initListContextMenu();
        int               titledPaneId = Integer.parseInt(according.getExpandedPane().getId());
        ListView<TaskDto> listView     = listViewMap.get(titledPaneId);
        TaskDto           selectedItem = listView.getSelectionModel().getSelectedItem();
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
                case YESTERDAY_REVIEW:
                    moveToYesterdayReviewMenuItem.setVisible(false);
                    break;
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
        moveToYesterdayReviewMenuItem.setVisible(true);
        moveToTomatoPotatoPlanMenuItem.setVisible(true);
        moveToMenu.setVisible(true);
    }

    private void buildListViewContextMenu() {
        liveViewContextMenu = new ContextMenu();
        // focus
        MenuItem focusMenuItem = new MenuItem(label("label_focus"));
        focusMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+F"));
        focusMenuItem.setOnAction(this::onStartTimer);
        // move_to
        // <MenuItem id="1" mnemonicParsing="false" onAction="#onMoveMenu"
        //                                                                  text="%label_yesterday_review"/>
        moveToMenu                     = new Menu(label("menu_move_to"));
        moveToYesterdayReviewMenuItem  = createMenuItem("1", "label_yesterday_review", this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+1"));
        moveToTodayPlanMenuItem        = createMenuItem("2", "label_today_plan", this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+2"));
        moveToTomatoPotatoPlanMenuItem = createMenuItem("3", "label_tomato_potato", this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+3"));
        moveToTodaySummaryMenuItem     = createMenuItem("4", "label_today_summary", this::onMoveMenu, KeyCombination.keyCombination("Meta+Ctrl+4"));
        moveToMenu.getItems().addAll(moveToYesterdayReviewMenuItem, moveToTodayPlanMenuItem, moveToTomatoPotatoPlanMenuItem, moveToTodaySummaryMenuItem);
        // menu_importance_urgency_axis
        Menu        iuaMenu     = new Menu(label("menu_importance_urgency_axis"));
        ToggleGroup toggleGroup = new ToggleGroup();
        iua1MenuItem = createRadioMenuItem("1", "label_importance_urgency", toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+1"));
        iua2MenuItem = createRadioMenuItem("2", "label_not_importance_but_urgency", toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+2"));
        iua3MenuItem = createRadioMenuItem("3", "label_importance_but_not_urgency", toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+3"));
        iua4MenuItem = createRadioMenuItem("4", "label_not_importance_not_urgency", toggleGroup, this::onSetIuaMenu, KeyCombination.keyCombination("Meta+Alt+4"));
        iuaMenu.getItems().addAll(iua1MenuItem, iua2MenuItem, iua3MenuItem, iua4MenuItem);
        // menu_order
        Menu     orderMenu             = new Menu(label("menu_order"));
        MenuItem moveOrderTopMenuItem  = createMenuItem("0", "menu_move_top", this::onOrderMoveTopMenu, KeyCombination.keyCombination("Meta+T"));
        MenuItem moveOrderUpMenuItem   = createMenuItem("0", "menu_move_up", this::onOrderMoveUpMenu, KeyCombination.keyCombination("Meta+K"));
        MenuItem moveOrderDownMenuItem = createMenuItem("0", "menu_move_down", this::onOrderMoveDownMenu, KeyCombination.keyCombination("Meta+J"));
        orderMenu.getItems().addAll(moveOrderTopMenuItem, moveOrderUpMenuItem, moveOrderDownMenuItem);

        // delete or undeleted
        Menu deleteOrUndeletedMenu = new Menu(label("menu_delete_undelete"));
        deleteMenuItem    = createMenuItem("0", "menu_delete", this::onDeleteMenu, KeyCombination.keyCombination("Meta+X"));
        undeletedMenuItem = createMenuItem("0", "menu_undelete", this::onUndeleteMenu, KeyCombination.keyCombination("Meta+Z"));
        deleteOrUndeletedMenu.getItems().addAll(deleteMenuItem, undeletedMenuItem);

        // tools
        Menu toolMenu = new Menu(label("menu_tools"));
        MenuItem menuSearch = createMenuItem("1", "menu_search", this::onSearchTaskMenu, KeyCombination.keyCombination("Meta+Alt+F"));
        toolMenu.getItems().add(menuSearch);


        liveViewContextMenu.getItems().addAll(focusMenuItem, moveToMenu, iuaMenu, orderMenu, deleteOrUndeletedMenu, new SeparatorMenuItem(), toolMenu);

    }

    private void onSearchTaskMenu(ActionEvent actionEvent) {
        ListView<TaskDto> listView = listViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));
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
        RadioMenuItem item = new RadioMenuItem(label(label));
        item.setId(id);
        item.setToggleGroup(toggleGroup);
        item.setOnAction(onMoveMenu);
        item.setAccelerator(keyCombination);
        return item;
    }

    private MenuItem createMenuItem(String id, String label_today_plan, EventHandler<ActionEvent> onMoveMenu, KeyCombination keyCombination) {
        MenuItem moveToTodayPlanMenuItem = new MenuItem(label(label_today_plan));
        moveToTodayPlanMenuItem.setId(id);
        moveToTodayPlanMenuItem.setOnAction(onMoveMenu);
        moveToTodayPlanMenuItem.setAccelerator(keyCombination);
        return moveToTodayPlanMenuItem;
    }

    private void clearTitledPaneData(int titledPaneId) {
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
            ListView<TaskDto>       listView = listViewMap.get(titledPaneId);
            ObservableList<TaskDto> items    = listView.getItems();
            items.forEach(task -> {
                task.finishedProperty().unbind();
                task.iuaProperty().unbind();
            });
            items.clear();
        }
    }

    private void loadTitledPaneData(int titledPaneId) {
        TaskType taskType = TaskType.findById(titledPaneId);
        int      dayId    = DateUtils.dayId(datePicker.getValue());
        currentDayTitledPane.setText(FlakeLabel.CURRENT_DAY + " " + dayId);
        // undone
        if (taskType == null) {
            List<TaskDto> undoneTasks = taskService.findAllUndoneTasks();
//            undoneList.getItems().forEach(task -> {
//                task.finishedProperty().unbind();
//                task.iuaProperty().unbind();
//            });
//            undoneList.getItems().clear();
            if (!CollectionUtils.isEmpty(undoneTasks)) {
                undoneTasks.forEach(this::onTaskDataChange);
                undoneList.getItems().addAll(undoneTasks);
            }
        } else {
            // loadDayTask

            List<TaskDto>           tasks    = taskService.findTasksByDayIdAndType(dayId, taskType);
            ListView<TaskDto>       listView = listViewMap.get(titledPaneId);
            ObservableList<TaskDto> items    = listView.getItems();
//            items.forEach(task -> {
//                task.finishedProperty().unbind();
//                task.iuaProperty().unbind();
//            });
//            items.clear();
            tasks.forEach(this::onTaskDataChange);
            items.addAll(tasks);
        }
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
        int hours   = (int) (remainingSeconds / 60 / 60);
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

//    private void loadData() {
//        // loadDayTask
//        int dayId = DateUtils.dayId(datePicker.getValue());
//        titledPane.setText(FlakeLabel.CURRENT_DAY + " " + dayId);
//        List<TaskDto> list = taskService.findAllTasksByDayId(dayId);
//
//        List<TaskDto> needObserver = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(list)) {
//            Map<TaskType, List<TaskDto>> map = list.stream().collect(Collectors.groupingBy(TaskDto::getTaskType));
//
//            for (Map.Entry<TaskType, List<TaskDto>> entry : map.entrySet()) {
//                ObservableList<TaskDto> items = listViewMap.get(entry.getKey().getCId()).getItems();
//                items.clear();
//                List<TaskDto> dtos = entry.getValue();
//                items.addAll(dtos);
//                needObserver.addAll(dtos);
//            }
//        } else {
//            listViewMap.values().forEach(l -> l.getItems().clear());
//        }
//        // load all undone tasks
//        List<TaskDto> undoneTasks = taskService.findAllUndoneTasks();
//        needObserver.addAll(undoneTasks);
//        undoneList.getItems().addAll(undoneTasks);
//        //
//        needObserver.forEach(this::onDataChange);
////        taskService.findAllTasksByDayId()
//    }

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
        ContextMenu       parentPopup  = ((MenuItem) actionEvent.getTarget()).getParentPopup();
        ListView<TaskDto> listView     = listViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));
        TaskDto           selectedItem = listView.getSelectionModel().getSelectedItem();
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
        Node       node         = (Node) actionEvent.getSource();
        Scene      scene1       = node.getScene();
        Stage      primaryStage = (Stage) scene1.getWindow();
        BorderPane borderPane   = fxWeaver.loadView(SettingsController.class, resourceBundle);
        Scene      scene        = new Scene(borderPane);
        Stage      stage        = new Stage();
        stage.setResizable(false);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        reloadCurrentTitlePane();
    }

    private void reloadCurrentTitlePane() {
        int titledPaneId = Integer.parseInt(according.getExpandedPane().getId());
        clearTitledPaneData(titledPaneId);
        loadTitledPaneData(titledPaneId);
    }


    public void onSetIuaMenu(ActionEvent actionEvent) {
        EventTarget   target      = actionEvent.getTarget();
        RadioMenuItem menuItem    = (RadioMenuItem) target;
        ContextMenu   parentPopup = menuItem.getParentPopup();
        // undoneListView id is 0
        String id = parentPopup.getId();
        //
        int     targetIuaId  = Integer.parseInt(menuItem.getId());
        TaskDto selectedItem = undoneList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int iua = selectedItem.getIua();
            if (targetIuaId != iua) {
                selectedItem.setIua(targetIuaId);
                log.info("set iua : {} -> {} ,{}", iua, targetIuaId, selectedItem);
                reloadCurrentTitlePane();
            }
        }
    }

    @Resource
    private ResourceBundle resourceBundle;

    public void onTaskClicked(MouseEvent mouseEvent) {
        EventTarget       target       = mouseEvent.getTarget();
        TitledPane        expandedPane = according.getExpandedPane();
        ListView<TaskDto> listView     = listViewMap.get(Integer.parseInt(expandedPane.getId()));
        TaskDto           selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            if (mouseEvent.getClickCount() == 2) {
                Node                                                node              = (Node) mouseEvent.getSource();
                Scene                                               nodeScene         = node.getScene();
                Stage                                               primaryStage      = (Stage) nodeScene.getWindow();
                FxControllerAndView<TaskDetailController, GridPane> controllerAndView = fxWeaver.load(TaskDetailController.class, resourceBundle);
                GridPane                                            borderPane        = controllerAndView.getView().get();
                TaskDetailController                                controller        = controllerAndView.getController();
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
        EventTarget       target      = actionEvent.getTarget();
        MenuItem          menuItem    = (MenuItem) target;
        ContextMenu       parentPopup = menuItem.getParentMenu().getParentPopup();
        ListView<TaskDto> listView    = listViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));

        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            taskService.restoreById(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onOrderMoveTopMenu(ActionEvent actionEvent) {
        EventTarget       target       = actionEvent.getTarget();
        MenuItem          menuItem     = (MenuItem) target;
        ContextMenu       parentPopup  = menuItem.getParentMenu().getParentPopup();
        ListView<TaskDto> listView     = listViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));
        TaskDto           selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            taskService.moveOrderTop(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onOrderMoveUpMenu(ActionEvent actionEvent) {
        EventTarget       target       = actionEvent.getTarget();
        MenuItem          menuItem     = (MenuItem) target;
        ContextMenu       parentPopup  = menuItem.getParentMenu().getParentPopup();
        ListView<TaskDto> listView     = listViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));
        TaskDto           selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            taskService.moveOrderUp(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onOrderMoveDownMenu(ActionEvent actionEvent) {
        EventTarget       target      = actionEvent.getTarget();
        MenuItem          menuItem    = (MenuItem) target;
        ContextMenu       parentPopup = menuItem.getParentMenu().getParentPopup();
        ListView<TaskDto> listView    = listViewMap.get(Integer.parseInt(according.getExpandedPane().getId()));

        TaskDto selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            taskService.moveOrderDown(selectedItem);
            reloadCurrentTitlePane();
//            listView.getItems().remove(selectedItem);
        }
    }

    public void onAddMoreButtonAction(ActionEvent actionEvent) {
    }
}
