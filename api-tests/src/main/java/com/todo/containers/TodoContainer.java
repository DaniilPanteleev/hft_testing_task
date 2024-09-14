package com.todo.containers;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.todo.config.AppConfig;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;


public class TodoContainer extends GenericContainer<TodoContainer> {

    public TodoContainer(@NonNull String dockerImageName) {
        super(dockerImageName);
    }

}
