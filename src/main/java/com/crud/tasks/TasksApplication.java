package com.crud.tasks;

import com.crud.tasks.domain.TaskDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class TasksApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {

		/*TaskDto taskDto = new TaskDto(
				(long) 1,
				"Test title",
				"Spring hard work"
		);
		Long id = taskDto.getId();
		String title = taskDto.getTitle();
		String content = taskDto.getContent();

		System.out.println(id + title + content);*/
		SpringApplication.run(TasksApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(TasksApplication.class);
	}
}
