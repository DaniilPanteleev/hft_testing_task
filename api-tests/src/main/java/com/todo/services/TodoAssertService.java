package com.todo.services;

import com.todo.entities.ResponseWrapper;
import org.assertj.core.api.Assertions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import todo_core_openapi.model.TODO;

import java.util.List;

import static com.todo.constants.Constants.THREAD;
import static com.todo.services.StepsService.expectedStep;
import static org.assertj.core.api.Assertions.assertThat;

@Component
@Scope(THREAD)
public class TodoAssertService {

    public TodoAssertService checkStatusCode(ResponseWrapper responseWrapper, Integer expected) {
        expectedStep("Check status code", () -> {
            assertThat(responseWrapper.getStatusCode())
                    .as("Status code doesn't match")
                    .isEqualTo(expected);
        });
        return this;
    }

    public TodoAssertService checkErrorDeserializationBody(ResponseWrapper responseWrapper, String expected) {
        expectedStep("Check error body", () -> {
            assertThat(responseWrapper.response()
                    .extract()
                    .body()
                    .asPrettyString()
            ).as("Error body doesn't match")
                    .containsPattern(expected);
        });
        return this;
    }

    public TodoAssertService checkTodosList(List<TODO> actual, List<TODO> expected) {
        expectedStep("Check todo list", () -> {
            assertThat(actual)
                    .as("Todo list doesn't match")
                    .isEqualTo(expected);
        });
        return this;
    }

    public TodoAssertService checkOffset(List<TODO> actual, List<TODO> expected, Integer offset) {
        expectedStep("Check todo lists size with offset", () -> {
            int result = offset > 0 ? Math.max(expected.size() - offset, 0) : expected.size();
            assertThat(actual)
                    .as("Todo list size doesn't match")
                    .hasSize(result);
        });
        return this;
    }

    public TodoAssertService checkLimit(List<TODO> actual, Integer limit) {
        expectedStep("Check todo lists size with limit", () -> {
            int result = limit > 0 ? Math.min(limit, actual.size()) : 0;
            assertThat(actual)
                    .as("Todo list size doesn't match")
                    .hasSize(result);
        });
        return this;
    }

}
