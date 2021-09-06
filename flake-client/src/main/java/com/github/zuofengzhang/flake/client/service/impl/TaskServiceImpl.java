package com.github.zuofengzhang.flake.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import com.github.zuofengzhang.flake.client.dao.TaskDao;
import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.TaskDo;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.service.TaskService;
import com.google.common.base.Joiner;
import javafx.beans.property.SimpleStringProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.zuofengzhang.flake.client.utils.DateUtils.lastDayRangeOfDayId;

/**
 * @author zhangzuofeng1
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final SimpleStringProperty totalTaskCntProperty           = new SimpleStringProperty();
    private final SimpleStringProperty todayTaskCntProperty           = new SimpleStringProperty();
    private final SimpleStringProperty taskPriorityDistributeProperty = new SimpleStringProperty();
    private final SimpleStringProperty tomatoCntProperty              = new SimpleStringProperty("0");
    private final SimpleStringProperty maxWorkTimeProperty            = new SimpleStringProperty();
    private final SimpleStringProperty urgentTaskCntProperty          = new SimpleStringProperty();
    private final SimpleStringProperty completenessProperty           = new SimpleStringProperty();

    @Resource
    private LuceneIndexer indexer;

    private final FlakeSettings settings = FlakeSettings.getInstance();
    @Resource
    private       TaskDao       dao;

    @PostConstruct
    private void init() {
        refreshTaskCnt();
    }


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
        if (taskType == TaskType.YESTERDAY_REVIEW) {
            Pair<Long, Long>     pair    = lastDayRangeOfDayId(dayId);
            TaskDo.TaskDoBuilder builder = TaskDo.builder();
            if (!settings.getShowDeletedTask()) {
                builder.storeStatus(StoreStatus.YES.getCode());
            }
            TaskDo aDo = builder.build();
            return dao.selectList(
                            new QueryWrapper<>(aDo)
                                    .between("update_time", pair.getLeft() - 1, pair.getRight() + 1)
                                    .orderByAsc("importance_urgency_axis")
                                    .orderByDesc("priority_order", "update_time")
                    )
                    .stream()
                    .map(TaskDto::parse)
                    .collect(Collectors.toList());
        }
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
                                .orderByDesc("priority_order", "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }

    @Override
    public void moveOrderTop(TaskDto task) {
        TaskDo taskDo        = dao.selectOne(new QueryWrapper<TaskDo>().orderByDesc("priority_order").last("limit 1"));
        Long   priorityOrder = taskDo.getPriorityOrder();
        task.setPriorityOrder(priorityOrder + 10);
        updateById(task);
    }

    @Override
    public void moveOrderUp(TaskDto task) {
        long   priorityOrder  = task.getPriorityOrder();
        TaskDo lastBiggest    = dao.selectOne(new QueryWrapper<TaskDo>().gt("priority_order", priorityOrder).orderByAsc("priority_order").last("limit 1"));
        Long   priorityOrder1 = lastBiggest.getPriorityOrder();
        task.setPriorityOrder(priorityOrder1 + 1);

        updateById(task);
    }

    @Override
    public void moveOrderDown(TaskDto task) {
        long   priorityOrder  = task.getPriorityOrder();
        TaskDo lastBiggest    = dao.selectOne(new QueryWrapper<TaskDo>().lt("priority_order", priorityOrder).orderByDesc("priority_order").last("limit 1"));
        Long   priorityOrder1 = lastBiggest.getPriorityOrder();
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
        refreshTaskCnt();
        try {
            indexer.addTask(task);
        } catch (IOException e) {
            log.error("", e);
        }
        return insert;
    }

    private void refreshTaskCnt() {
        final List<TaskDto> allTasks = findAllTasks();
        //
        totalTaskCntProperty.set(String.valueOf(allTasks.size()));
        //
        final int dayId = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        todayTaskCntProperty.set(String.valueOf(findAllTasksByDayId(dayId).size()));
        //
        final LinkedHashMap<Integer, Long> taskPriorityMap = new LinkedHashMap<>();
        taskPriorityMap.put(1, 0L);
        taskPriorityMap.put(2, 0L);
        taskPriorityMap.put(3, 0L);
        taskPriorityMap.put(4, 0L);
        allTasks.stream().collect(Collectors.groupingBy(TaskDto::getIua, LinkedHashMap::new, Collectors.counting())).forEach(taskPriorityMap::put);
        final String taskPriorityStat = Joiner.on("/").join(taskPriorityMap.values());
        taskPriorityDistributeProperty.set(taskPriorityStat);
        //
        final Long urgentTaskCnt = taskPriorityMap.get(1);
        urgentTaskCntProperty.set(String.valueOf(urgentTaskCnt));
    }

    @Override
    public void deleteById(TaskDto task) {
//        taskDao.deleteById(taskId);
        // logic deletes
        task.setStoreStatus(StoreStatus.NO);
        updateById(task);
        refreshTaskCnt();
    }

    @Override
    public void restoreById(TaskDto task) {
        task.setStoreStatus(StoreStatus.YES);
        updateById(task);
    }

    @Override
    public int updateById(TaskDto task) {
        TaskDo    taskDo = task.parse();
        final int update = dao.updateById(taskDo);
        try {
            indexer.updateTask(task);
        } catch (IOException e) {
            log.error("", e);
        }
        refreshTaskCnt();
        return update;
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

    @Override
    public SimpleStringProperty totalTaskCntProperty() {
        return totalTaskCntProperty;
    }

    @Override
    public SimpleStringProperty todayTaskCntProperty() {
        return todayTaskCntProperty;
    }

    @Override
    public SimpleStringProperty taskPriorityDistributeProperty() {
        return taskPriorityDistributeProperty;
    }

    @Override
    public SimpleStringProperty tomatoCntProperty() {
        return tomatoCntProperty;
    }

    @Override
    public SimpleStringProperty maxWorkTimeProperty() {
        return maxWorkTimeProperty;
    }

    @Override
    public SimpleStringProperty urgentTaskCntProperty() {
        return urgentTaskCntProperty;
    }

    @Override
    public SimpleStringProperty completenessProperty() {
        return completenessProperty;
    }

    @Override
    public List<TaskDto> search(String queryString) throws IOException, ParseException {
        List<Integer> list = indexer.search(queryString, settings.getShowDeletedTask());
        if (list != null) {
            return list.stream().map(taskId -> findById(taskId)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


}
