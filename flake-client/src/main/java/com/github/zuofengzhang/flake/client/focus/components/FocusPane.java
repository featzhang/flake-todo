package com.github.zuofengzhang.flake.client.focus.components;

import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.focus.FocusContext;
import com.github.zuofengzhang.flake.client.focus.FocusItem;
import com.github.zuofengzhang.flake.client.focus.FocusState;
import com.github.zuofengzhang.flake.client.focus.StateEnum;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class FocusPane extends VBox {
    private Label stateLabel;
    private Label timeLabel;
    private Label titleLabel;
    private Button startButton;
    private Timeline timeline = new Timeline();

    public FocusPane() {
        initComponent();
        loadAction();
    }

    private void loadAction() {

    }

    private FocusContext focusContext = new FocusContext();

    private void initComponent() {
        this.setSpacing(10.0);
        this.setPrefWidth(200.0);
        this.setAlignment(Pos.TOP_CENTER);
        //
        stateLabel = new Label("state");
        timeLabel = new Label("time");
        titleLabel = new Label("title");
        startButton = new Button("start");
        //
        stateLabel.setFont(Font.font(16));
        titleLabel.setFont(Font.font(16));
        timeLabel.setFont(Font.font(42));
        //
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(startButton);
        //
        getChildren().addAll(stateLabel, timeLabel, titleLabel, buttonBox);
    }

    public void focusTask(TaskDto taskDto) {
        FocusItem focusItem
                = FocusItem
                .builder()
                .title(taskDto.getTitle())
                .sit(true)
                .finished(false)
                .build();
        focusContext.setItem(focusItem);
        focusContext.initState();
        //
        titleLabel.setText(focusItem.getTitle());
        stateLabel.setText("working");
        startTimer();
    }

    private void startTimer() {
        StateEnum stateEnum = focusContext.getState().getStateEnum();
        String cName = stateEnum.getCName();
        stateLabel.setText(cName);
    }

}
