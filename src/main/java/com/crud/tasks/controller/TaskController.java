package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Transactional
@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;

    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET , value = "getTasks")
    public List<TaskDto> getTasks(){
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method =  RequestMethod.GET , value = "getTask")
    public TaskDto getTask(Long taskId){
        return  taskMapper.mapToTaskDto(service.getTask(taskId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto){
        service.saveTask(taskMapper.mapToTask(taskDto));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTaskO")
    public TaskDto getTaskO(@RequestParam Long taskId) throws TaskNotFoundException{
        return taskMapper.mapToTaskDto(service.getTaskO(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask", consumes = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto){
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(@RequestParam Long taskId){
        service.deleteTask(taskId);
    }


/*
    @RequestMapping(method = RequestMethod.GET , value = "getTasks")
    public List<TaskDto> getTasks(){
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(Long taskId){
        return new TaskDto(1L,"test title","test content");
        //return new TaskDto(taskId,"test title","test content");
    }

    @RequestMapping(method = RequestMethod.DELETE , value = "deleteTask")
    public void deleteTask(Long taskId){

    }

    @RequestMapping(method = RequestMethod.PUT  , value = "updateTask")
    public TaskDto updateTask(TaskDto task){
        return new TaskDto(1L,"Edited test title","Edited test content");
        //return new TaskDto(task.getId(),task.getTitle(),task.getContent());
    }

    @RequestMapping(method = RequestMethod.POST , value = "createTask")
    public void createTask(TaskDto task){
       // return new TaskDto(1L,"Create test title","Create test content")
    }
    */
}
