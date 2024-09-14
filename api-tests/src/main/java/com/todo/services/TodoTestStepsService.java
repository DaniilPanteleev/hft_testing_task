package com.todo.services;


import com.todo.config.AppConfig;
import com.todo.entities.ResponseWrapper;
import com.todo.entities.TODONegativeEntity;
import com.todo.services.rest.TodoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import todo_core_openapi.model.TODO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.todo.constants.Constants.THREAD;

@Component
@Scope(THREAD)
public class TodoTestStepsService {

    public static final String COMMON_TEXT = "Test";

    @Autowired
    private TodoApiService todoApiService;

    @Autowired
    protected AppConfig appConfig;

    public TodoTestStepsService(TodoApiService todoApiService, AppConfig appConfig) {
        this.todoApiService = todoApiService;
        this.appConfig = appConfig;
    }

    public ResponseWrapper successCreateNewTodo(Long id) {
        return createTodo(id, false, COMMON_TEXT);
    }

    public ResponseWrapper successCreateNewTodo(String text) {
        return createTodo(RandomUtils.nextLong(), false, text);
    }

    public ResponseWrapper successCreateNewTodo(Boolean isCompleted) {
        return createTodo(RandomUtils.nextLong(), isCompleted, COMMON_TEXT);
    }

    public ResponseWrapper errorCreateNewTodo(String id) {
        return createTodo(id, false, COMMON_TEXT);
    }

    public ResponseWrapper errorCreateNewTodo(Long isCompleted) {
        return createTodo(RandomUtils.nextLong(), isCompleted, COMMON_TEXT);
    }

    public ResponseWrapper errorCreateNewTodo(Boolean text) {
        return createTodo(RandomUtils.nextLong(), false, text);
    }

    public ResponseWrapper createTodo(Long id, Boolean isCompleted, String text) {
        return todoApiService.postCreateTodo(new TODO()
                .id(id)
                .completed(isCompleted)
                .text(text)
        );
    }

    public ResponseWrapper createTodo(TODO body) {
        return todoApiService.postCreateTodo(body);
    }

    public ResponseWrapper createTodo(Object id, Object isCompleted, Object text) {
        return todoApiService.postCreateTodo(TODONegativeEntity.builder()
                .id(id)
                .completed(isCompleted)
                .text(text)
                .build()
        );
    }

    public ResponseWrapper getTodoList() {
        return todoApiService.getTodosList();
    }

    public ResponseWrapper getTodoListOffset(Integer offset) {
        return todoApiService.getTodosList(offset, null);
    }

    public ResponseWrapper getTodoListLimit(Integer limit) {
        return todoApiService.getTodosList(null, limit);
    }

    public ResponseWrapper getTodoList(Integer offset, Integer limit) {
        return todoApiService.getTodosList(offset, limit);
    }

    public ResponseWrapper updateTodo(TODO body) {
        return todoApiService.putUpdateTodo(body);
    }

    public ResponseWrapper deleteTodoAsAdmin(Long id) {
        return todoApiService.deleteTodo(id, appConfig.getRestProperties().login(), appConfig.getRestProperties().password());
    }

    public ResponseWrapper deleteTodoAsAnotherUser(Long id) {
        return todoApiService.deleteTodo(id, "test", "test");
    }

    public ResponseWrapper deleteTodoWithoutAuth(Long id) {
        return todoApiService.deleteTodo(id);
    }

    public List<TODO> createTodosList() {
        return Stream.of(
                new TODO()
                        .id(RandomUtils.nextLong())
                        .text(COMMON_TEXT)
                        .completed(false),
                new TODO()
                        .id(RandomUtils.nextLong())
                        .text("@!#$%^^$")
                        .completed(true),
                new TODO()
                        .id(RandomUtils.nextLong())
                        .text("")
                        .completed(false)
        ).map(todo -> {
            todoApiService.postCreateTodo(todo);
            return todo;
        }).collect(Collectors.toList());
    }


}
