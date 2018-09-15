package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean// bo jest w controlerze jako @Autowired
    private TaskMapper taskMapper;
    @MockBean// bo jest w controlerze jako @Autowired
    private DbService dbService;
    @Test
    public void shouldFetchEmptyTaskTasks() throws Exception {
        // Given
        List<Task> taskList = new ArrayList<>();
        List<TaskDto> taskDtoList = new ArrayList<>();
        //When
        //Mockito.when(taskController.getTask(1L)).thenReturn(taskMapper.mapToTaskDto(task)); tutaj TaskMapper oraz dbService ponirwaz z tych Beanow korzysta TaskController
        Mockito.when(dbService.getAllTasks()).thenReturn(taskList);
        Mockito.when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        // When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().is(200)) // or isOk()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$" , hasSize(0)))
        ;
    }

    @Test
    public void shouldFetchTaskTasks() throws Exception {
        // Given
        Task task = new Task(1L,"test title","test content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        TaskDto taskDto = new TaskDto(1L,"test title","test content");
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(taskDto);
        //When
        //Mockito.when(taskController.getTask(1L)).thenReturn(taskMapper.mapToTaskDto(task)); tutaj TaskMapper oraz dbService ponirwaz z tych Beanow korzysta TaskController
        Mockito.when(dbService.getTask(1L)).thenReturn(task);
        Mockito.when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        Mockito.when(dbService.getAllTasks()).thenReturn(taskList);
        Mockito.when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        // When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().is(200)) // or isOk()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" , is(1)))
                .andExpect(jsonPath("$.title" , is("test title")))
                .andExpect(jsonPath("$.content" , is("test content")))
        ;
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().is(200)) // or isOk()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$" , hasSize(1)))
                .andExpect(jsonPath("$[0].id" , is(1)))
                .andExpect(jsonPath("$[0].title" , is("test title")))
                .andExpect(jsonPath("$[0].content" , is("test content")))
        ;
    }
/*
    @Test
    public void shouldUpdateTask() throws Exception {
        // Given
        Task task = new Task(1L,"test title","test content");
        TaskDto taskDto = new TaskDto(1L,"test title","test content");
        //When
        //Mockito.when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        // When & Then
        mockMvc.perform(delete("/v1/task/deleteTaskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().is(200)) // or isOk()
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id" , is(1)))
                //.andExpect(jsonPath("$.title" , is("test title")))
                //.andExpect(jsonPath("$.content" , is("test content")))
        ;
    }*/
}
