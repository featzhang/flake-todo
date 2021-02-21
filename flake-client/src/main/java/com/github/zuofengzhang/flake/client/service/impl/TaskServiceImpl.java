package com.github.zuofengzhang.flake.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import com.github.zuofengzhang.flake.client.dao.TaskDao;
import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.TaskDo;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangzuofeng1
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final FlakeSettings settings = FlakeSettings.getInstance();
    @Resource
    private TaskDao dao;

    @Override
    public List<TaskDto> findAllTasks() {
        return dao.selectList(new QueryWrapper<>()).stream().map(TaskDto::parse).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findAllTasksByDayId(int dayId) {
        return dao
                .selectList(
                        new QueryWrapper<>(TaskDo.builder().dayId(dayId).build())
                                .orderByAsc("importance_urgency_axis")
                                .orderByDesc("priority_order", "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }


    @Override
    public List<TaskDto> findTasksByDayIdAndType(int dayId, TaskType taskType) {
        TaskDo.TaskDoBuilder builder = TaskDo.builder().dayId(dayId).typeId(taskType.getCId());
        if (!settings.getShowDeletedTask()) {
            builder.storeStatus(StoreStatus.YES.getCode());
        }
        TaskDo aDo = builder.build();
        return dao
                .selectList(
                        new QueryWrapper<>(
                                aDo
                        )
                                .orderByAsc("importance_urgency_axis")
                                .orderByDesc("priority_order",  "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }

    @Override
    public void moveOrderTop(TaskDto task) {
        TaskDo taskDo = dao.selectOne(new QueryWrapper<TaskDo>().orderByDesc("priority_order").last("limit 1"));
        Long priorityOrder = taskDo.getPriorityOrder();
        task.setPriorityOrder(priorityOrder + 10);
        updateById(task);
    }

    @Override
    public void moveOrderUp(TaskDto task) {
        long priorityOrder = task.getPriorityOrder();
        TaskDo lastBiggest = dao.selectOne(new QueryWrapper<TaskDo>().gt("priority_order", priorityOrder).orderByAsc("priority_order").last("limit 1"));
        Long priorityOrder1 = lastBiggest.getPriorityOrder();
        task.setPriorityOrder(priorityOrder1 + 1);

        updateById(task);
    }

    @Override
    public void moveOrderDown(TaskDto task) {
        long priorityOrder = task.getPriorityOrder();
        TaskDo lastBiggest = dao.selectOne(new QueryWrapper<TaskDo>().lt("priority_order", priorityOrder).orderByDesc("priority_order").last("limit 1"));
        Long priorityOrder1 = lastBiggest.getPriorityOrder();
        task.setPriorityOrder(priorityOrder1 - 1);
        updateById(task);
    }


    @Override
    public int insert(TaskDto task) {
        log.info("insert into {}", task);
        TaskDo taskDo = task.parse();
        log.info("insert into {}", taskDo);
        int insert = dao.insert(taskDo);
        // reset id value to DTO
        task.setTaskId(taskDo.getTaskId());
        return insert;
    }

    @Override
    public void deleteById(TaskDto task) {
//        taskDao.deleteById(taskId);
        // logic deletes
        task.setStoreStatus(StoreStatus.NO);
        updateById(task);
    }

    @Override
    public void restoreById(TaskDto task) {
        task.setStoreStatus(StoreStatus.YES);
        updateById(task);
    }

    @Override
    public int updateById(TaskDto task) {
        TaskDo taskDo = task.parse();
        return dao.updateById(taskDo);
    }

    @Override
    public TaskDto findById(int taskId) {
        return TaskDto.parse(dao.selectById(taskId));
    }

    @Override
    public List<TaskDto> findAllUndoneTasks() {
        TaskDo.TaskDoBuilder builder = TaskDo.builder().finished(false);
        if (!settings.getShowDeletedTask()) {
            builder.storeStatus(StoreStatus.YES.getCode());
        }
        TaskDo aDo = builder.build();
        return dao
                .selectList(
                        new QueryWrapper<>(aDo)
                                .orderByAsc("importance_urgency_axis")
                                .orderByDesc("priority_order", "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }
}
