package com.todo.services.rest;

import com.todo.containers.TodoContainer;
import com.todo.entities.ResponseWrapper;
import com.todo.entities.TODONegativeEntity;
import com.todo.services.StepsService;
import com.todo.utils.Base64Utils;
import io.restassured.response.ResponseOptions;
import org.awaitility.Awaitility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import todo_core_openapi.api.DefaultApi;
import todo_core_openapi.invoker.ApiClient;
import todo_core_openapi.model.TODO;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.todo.constants.Constants.*;

@Component
@Scope(THREAD)
public class TodoApiService extends BaseRestService<TodoApiService> {

    private TodoContainer todoContainer;

    private ApiClient client = ApiClient.api(
            ApiClient.Config.apiConfig()
                    .reqSpecSupplier(getCommonRequestSpecBuilderSupplier())
    );

    @Autowired
    public TodoApiService(TodoContainer todoContainer) {
        this.todoContainer = todoContainer;
    }

    @Override
    protected String getBaseUri() {
        return "http://%s:%s".formatted(todoContainer.getHost(), todoContainer.getMappedPort(TODO_PORT));
    }

    public ResponseWrapper getTodosList() {
       return getTodosList(null, null);
    }

    public ResponseWrapper getTodosList(Integer offset, Integer limit) {
        AtomicReference<ResponseWrapper> responseWrapper = new AtomicReference<>();
        StepsService.step("Send %s".formatted(STORY_GET), () -> {
            DefaultApi.TodosGetOper getOper = client._default()
                    .todosGet();

            if (Optional.ofNullable(offset).isPresent()) {
                getOper.offsetQuery(offset);
            }
            if (Optional.ofNullable(limit).isPresent()) {
                getOper.limitQuery(limit);
            }

            responseWrapper.set(wrap(
                    getOper
                            .execute(ResponseOptions::andReturn)
                            .then()
            ));
        });
        return responseWrapper.get();
    }

    public ResponseWrapper postCreateTodo(TODO body) {
        AtomicReference<ResponseWrapper> responseWrapper = new AtomicReference<>();
        StepsService.step("Send %s".formatted(STORY_POST), () -> responseWrapper.set(wrap(
                client._default()
                        .todosPost()
                        .body(body)
                        .execute(ResponseOptions::andReturn)
                        .then()
        )));
        return responseWrapper.get();
    }

    public ResponseWrapper postCreateTodo(TODONegativeEntity body) {
        AtomicReference<ResponseWrapper> responseWrapper = new AtomicReference<>();
        StepsService.step("Send %s".formatted(STORY_POST), () -> responseWrapper.set(wrap(
                client._default()
                        .reqSpec(r -> r.setBody(body.toJson()))
                        .todosPost()
                        .execute(ResponseOptions::andReturn)
                        .then()
        )));
        return responseWrapper.get();
    }

    public ResponseWrapper putUpdateTodo(TODO body) {
        AtomicReference<ResponseWrapper> responseWrapper = new AtomicReference<>();
        StepsService.step("Send %s".formatted(STORY_PUT), () -> responseWrapper.set(wrap(
                client._default()
                        .todosIdPut()
                        .idPath(body.getId())
                        .body(body)
                        .execute(ResponseOptions::andReturn)
                        .then()
        )));
        return responseWrapper.get();
    }

    public ResponseWrapper deleteTodo(Long id) {
        return deleteTodo(id, null, null);
    }

    public ResponseWrapper deleteTodo(Long id, String login, String password) {
        AtomicReference<ResponseWrapper> responseWrapper = new AtomicReference<>();
        StepsService.step("Send %s".formatted(STORY_DELETE), () -> {
            DefaultApi api = client._default();
            if (Optional.ofNullable(login).isPresent() && Optional.ofNullable(password).isPresent()) {
                String token = Base64Utils.encode("%s:%s".formatted(login, password));
                api.reqSpec(r -> r.addHeader("Authorization", "Basic " + token));
            }

            responseWrapper.set(wrap(
                    api
                            .todosIdDelete()
                            .idPath(id)
                            .execute(ResponseOptions::andReturn)
                            .then()
            ));
        });
        return responseWrapper.get();
    }

}
