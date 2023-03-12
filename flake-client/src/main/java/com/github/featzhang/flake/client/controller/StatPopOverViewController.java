package com.github.featzhang.flake.client.controller;

import com.github.featzhang.flake.client.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.github.featzhang.flake.client.consts.FlakeLabel.*;

@Component
@FxmlView("stat-popover-view.fxml")
public class StatPopOverViewController implements Initializable {

    public  Label       urgentTaskCntLabel;
    public  Label       completenessLabel;
    @Resource
    private TaskService taskService;

    public Label    totalTaskCntLabel;
    public Label    totalTomatoCntLabel;
    public Label    todayTaskCntLabel;
    public PieChart taskDistributePie;

    public void onShowDetailAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String totalTaskCnt = taskService.totalTaskCntProperty().getValue();
        totalTaskCntLabel.setText(totalTaskCnt);

        String totalTomatoCnt = taskService.tomatoCntProperty().getValue();
        totalTomatoCntLabel.setText(totalTomatoCnt);

        String todayTaskCnt = taskService.todayTaskCntProperty().getValue();
        todayTaskCntLabel.setText(todayTaskCnt);

       

        String completeness = taskService.completenessProperty().getValue();
        completenessLabel.setText(completeness);

        String        pdp  = taskService.taskPriorityDistributeProperty().getValue();
        List<Integer> list = Arrays.stream(pdp.split("/")).map(Integer::parseInt).collect(Collectors.toList());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(IMPORTANCE_URGENCY.value(), list.get(0)),
                new PieChart.Data(IMPORTANCE_BUT_NOT_URGENCY.value(), list.get(1)),
                new PieChart.Data(NOT_IMPORTANCE_BUT_URGENCY.value(), list.get(2)),
                new PieChart.Data(NOT_IMPORTANCE_NOT_URGENCY.value(), list.get(3))
        );
        taskDistributePie.setData(pieChartData);

        urgentTaskCntLabel.setText(String.valueOf(list.get(0)));
    }
}
