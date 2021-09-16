package com.github.zuofengzhang.flake.client.service;

import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    List<TaskDto> findAllTasks();

    List<TaskDto> findAllTasksByDayId(int dayId);

    int insert(TaskDto taskDto);

    void deleteById(TaskDto task);

    void restoreById(TaskDto task);

    int updateById(TaskDto selectedItem);

    TaskDto findById(int taskId);

    List<TaskDto> findAllUndoneTasks();

    public List<TaskDto> findTasksByDayIdAndType(int dayId, TaskType taskType);

    void moveOrderTop(TaskDto selectedItem);

    void moveOrderUp(TaskDto selectedItem);

    void moveOrderDown(TaskDto selectedItem);


    SimpleStringProperty totalTaskCntProperty();

    SimpleStringProperty todayTaskCntProperty();

    SimpleStringProperty taskPriorityDistributeProperty();

    SimpleStringProperty tomatoCntProperty();

    SimpleStringProperty maxWorkTimeProperty();

    SimpleStringProperty urgentTaskCntProperty();

    SimpleStringProperty completenessProperty();

    List<TaskDto> search(String queryString) throws IOException, ParseException;

    void updateIndex(TaskDto taskDto);

    List<TaskDto> findNearWeekTasks(int dayId);
}
