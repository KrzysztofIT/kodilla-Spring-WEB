package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void checkTestMapperDto ()  {
        //Given

        TaskDto taskDto = new TaskDto(1L,"test TaskDto title","test TaskDto content");
        Task task = new Task(1L,"test Task title","test Task content");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        //When
        Task taskmapped = taskMapper.mapToTask(taskDto);
        TaskDto taskmappedDto = taskMapper.mapToTaskDto(task);

        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        Assert.assertEquals("test TaskDto title" , taskmapped.getTitle());
        Assert.assertEquals("test TaskDto content" , taskmapped.getContent());

        Assert.assertEquals("test Task title" , taskmappedDto.getTitle());
        Assert.assertEquals("test Task content" , taskmappedDto.getContent());

        Assert.assertEquals("test Task title" , taskDtoList.get(0).getTitle());
        Assert.assertEquals("test Task content" , taskDtoList.get(0).getContent());


    }
}
