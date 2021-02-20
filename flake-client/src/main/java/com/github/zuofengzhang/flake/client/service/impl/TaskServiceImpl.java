package com.github.zuofengzhang.flake.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.zuofengzhang.flake.client.dao.TaskDao;
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
    @Resource
    private TaskDao taskDao;

    @Override
    public List<TaskDto> findAllTasks() {
        return taskDao.selectList(new QueryWrapper<>()).stream().map(TaskDto::parse).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findAllTasksByDayId(int dayId) {
        return taskDao
                .selectList(
                        new QueryWrapper<>(TaskDo.builder().dayId(dayId).build())
                                .orderByDesc("priority_order", "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }

    
    @Override
    public List<TaskDto> findTasksByDayIdAndType(int dayId,TaskType taskType){
        return taskDao
        .selectList(
            new QueryWrapper<>(TaskDo.builder().dayId(dayId).typeId(taskType.getCId()).build())
            .orderByDesc("priority_order", "update_time")
            )
        .stream()
        .map(TaskDto::parse)
        .collect(Collectors.toList());
    }


    @Override
    public int insert(TaskDto taskDto) {
        log.info("insert into {}", taskDto);
        TaskDo taskDo = taskDto.parse();
        log.info("insert into {}", taskDo);
        int insert = taskDao.insert(taskDo);
        // reset id value to DTO
        taskDto.setTaskId(taskDo.getTaskId());
        return insert;
    }

    @Override
    public void deleteById(int taskId) {
        taskDao.deleteById(taskId);
    }

    @Override
    public int updateById(TaskDto selectedItem) {
        TaskDo taskDo = selectedItem.parse();
        return taskDao.updateById(taskDo);
    }

    @Override
    public TaskDto findById(int taskId) {
        return TaskDto.parse(taskDao.selectById(taskId));
    }

    @Override
    public List<TaskDto> findAllUndoneTasks() {
        return taskDao
                .selectList(
                        new QueryWrapper<>(TaskDo.builder().finished(false).build())
                                .orderByDesc("priority_order", "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }
}
