package com.todo;

import com.todo.annotations.tests.Regression;
import com.todo.base.TestBase;
import com.todo.entities.ResponseWrapper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import todo_core_openapi.model.TODO;

import java.util.List;

import static com.todo.constants.Constants.*;
import static com.todo.services.TodoTestStepsService.COMMON_TEXT;
import static org.apache.http.HttpStatus.*;

@Testcontainers
@Regression
@Log4j2
@Epic(EPIC)
public class TodoApiTests extends TestBase {

    @Test
    @Story(STORY_POST)
    @DisplayName("Todo success creation")
    @Description(POSITIVE)
    void testSuccessCreation() {
        ResponseWrapper result = todoTestStepsService.successCreateNewTodo(
                RandomUtils.nextLong()
        );

        todoAssertService.checkStatusCode(result, SC_CREATED);
    }

    @Test
    @Story(STORY_POST)
    @DisplayName("Todo success creation \"text\" with symbols")
    @Description(POSITIVE)
    void testCreationWithTextSymbols() {
        ResponseWrapper result = todoTestStepsService.successCreateNewTodo(
                "!@#$%^&*()[]{}<>:;'\"№~`"
        );

        todoAssertService.checkStatusCode(result, SC_CREATED);
    }

    @Test
    @Story(STORY_POST)
    @DisplayName("Todo success creation \"complete\" false")
    @Description(POSITIVE)
    void testCreationWithCompleteFalse() {
        ResponseWrapper result = todoTestStepsService.successCreateNewTodo(
                false
        );

        todoAssertService.checkStatusCode(result, SC_CREATED);
    }

    @Test
    @Story(STORY_POST)
    @DisplayName("Todo error creation with \"id\" different type")
    @Description(NEGATIVE)
    void testCreationIdIsString() {
        ResponseWrapper result = todoTestStepsService.errorCreateNewTodo(
                COMMON_TEXT
        );

        todoAssertService
                .checkStatusCode(result, SC_BAD_REQUEST)
                .checkErrorDeserializationBody(result, "Request body deserialize error: invalid type: string" +
                        " \"Test\", expected u64 at line 1 column \\d*");
    }

    @Test
    @Story(STORY_POST)
    @DisplayName("Todo error creation with \"completed\" different type")
    @Description(NEGATIVE)
    void testCreationCompletedIsLong() {
        long id = 6505172296773693616L;
        ResponseWrapper result = todoTestStepsService.errorCreateNewTodo(
                id
        );

        todoAssertService
                .checkStatusCode(result, SC_BAD_REQUEST)
                .checkErrorDeserializationBody(result, "Request body deserialize error: invalid type: integer " +
                        "`%s`, expected a boolean at line 1 column \\d*".formatted(id));
    }

    @Test
    @Story(STORY_POST)
    @DisplayName("Todo error creation with \"text\" different type")
    @Description(NEGATIVE)
    void testCreationTextBoolean() {
        ResponseWrapper result = todoTestStepsService.errorCreateNewTodo(
                true
        );

        todoAssertService
                .checkStatusCode(result, SC_BAD_REQUEST)
                .checkErrorDeserializationBody(result, "Request body deserialize error: invalid type: boolean " +
                        "`true`, expected a string at line 1 column \\d*");
    }

    @Test
    @Story(STORY_POST)
    @DisplayName("Todo error creation with already created \"id\"")
    @Description(NEGATIVE)
    void testCreationCompletedLong() {
        long id = RandomUtils.nextLong();
        todoTestStepsService.successCreateNewTodo(id);

        ResponseWrapper result = todoTestStepsService.successCreateNewTodo(id);

        todoAssertService
                .checkStatusCode(result, SC_BAD_REQUEST);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Success get todos")
    @Description(POSITIVE)
    void testGettingList() {
        List<TODO> expected = todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoList();
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_OK)
                .checkTodosList(actual, expected);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with offset 1, total items 3")
    @Description(POSITIVE)
    void testGettingListWithOffset() {
        int offset = 1;
        List<TODO> expected = todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListOffset(offset);
        List<TODO> actual = result.asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_OK)
                .checkOffset(actual, expected, offset);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with offset 4, total items 3")
    @Description(NEGATIVE)
    void testGettingListWithOffsetMoreThanItems() {
        int offset = 4;
        List<TODO> expected = todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListOffset(offset);
        List<TODO> actual = result.asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_OK)
                .checkOffset(actual, expected, offset);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with offset -1, total items 3")
    @Description(NEGATIVE)
    void testGettingListWithOffsetIsNegative() {
        int offset = -1;
        List<TODO> expected = todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListOffset(offset);
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_BAD_REQUEST)
                .checkErrorDeserializationBody(result, "Invalid query string")
                .checkOffset(actual, expected, offset);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with offset 0, total items 3")
    @Description(NEGATIVE)
    void testGettingListWithOffsetIsZero() {
        int offset = 0;
        List<TODO> expected = todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListOffset(offset);
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_OK)
                .checkOffset(actual, expected, offset);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with limit 1, total items 3")
    @Description(POSITIVE)
    void testGettingListWithLimit() {
        int limit = 1;
        todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListLimit(limit);
        List<TODO> actual = result.asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_OK)
                .checkLimit(actual, limit);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with limit 4, total items 3")
    @Description(POSITIVE)
    void testGettingListWithLimitMoreThanTodos() {
        int limit = 4;
        todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListLimit(limit);
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_OK)
                .checkLimit(actual, limit);
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with limit -1, total items 3")
    @Description(NEGATIVE)
    void testGettingListWithLimitIsNegative() {
        int limit = -1;
        List<TODO> expected = todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListLimit(limit);
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_BAD_REQUEST)
                .checkErrorDeserializationBody(result, "Invalid query string")
                .checkLimit(actual, expected.size());
    }

    @Test
    @Story(STORY_GET)
    @DisplayName("Get todos with limit 0, total items 3")
    @Description(NEGATIVE)
    void testGettingListWithLimitIsZero() {
        int limit = 0;
        todoTestStepsService.createTodosList();

        ResponseWrapper result = todoTestStepsService.getTodoListLimit(limit);
        List<TODO> actual = result.asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_OK)
                .checkLimit(actual, limit);
    }

    @Test
    @Story(STORY_PUT)
    @DisplayName("Update not existed todo item")
    @Description(NEGATIVE)
    void testUpdateNotExistedTodo() {
        ResponseWrapper result = todoTestStepsService.updateTodo(
                new TODO()
                        .id(RandomUtils.nextLong())
                        .completed(true)
                        .text(COMMON_TEXT)
        );

        todoAssertService
                .checkStatusCode(result, SC_NOT_FOUND);
    }

    @Test
    @Story(STORY_PUT)
    @DisplayName("Successfully update todo item")
    @Description(POSITIVE)
    void testSuccessfullyUpdateTodo() {
        TODO todo = new TODO()
                .id(RandomUtils.nextLong())
                .text(COMMON_TEXT)
                .completed(false);
        todoTestStepsService.createTodo(todo);

        todo.text("New Text");
        todo.completed(true);
        ResponseWrapper result = todoTestStepsService.updateTodo(todo);

        todoAssertService
                .checkStatusCode(result, SC_OK);
    }

    @Test
    @Story(STORY_DELETE)
    @DisplayName("Delete todo item as authorized admin user")
    @Description(POSITIVE)
    void testDeleteTodo() {
        List<TODO> expected = todoTestStepsService.createTodosList();

        TODO todo = expected.get(0);
        expected.remove(todo);

        ResponseWrapper result = todoTestStepsService.deleteTodoAsAdmin(todo.getId());
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_NO_CONTENT)
                .checkTodosList(actual, expected);
    }

    @Test
    @Story(STORY_DELETE)
    @DisplayName("Delete not existed todo item")
    @Description(NEGATIVE)
    void testDeleteNotExistedTodo() {
        ResponseWrapper result = todoTestStepsService.deleteTodoAsAdmin(2334435L);

        todoAssertService
                .checkStatusCode(result, SC_NOT_FOUND);
    }

    @Test
    @Story(STORY_DELETE)
    @DisplayName("Delete todo item as authorized not existed user")
    @Description(NEGATIVE)
    void testDeleteTodoWithNotExistedUser() {
        List<TODO> expected = todoTestStepsService.createTodosList();

        TODO todo = expected.get(0);

        ResponseWrapper result = todoTestStepsService.deleteTodoAsAnotherUser(todo.getId());
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_UNAUTHORIZED)
                .checkTodosList(actual, expected);
    }

    @Test
    @Story(STORY_DELETE)
    @DisplayName("Delete todo item as unauthorized user")
    @Description(NEGATIVE)
    void testDeleteTodoWithUnAuthUser() {
        List<TODO> expected = todoTestStepsService.createTodosList();

        TODO todo = expected.get(0);

        ResponseWrapper result = todoTestStepsService.deleteTodoWithoutAuth(todo.getId());
        List<TODO> actual = todoTestStepsService.getTodoList().asList(".", TODO.class);

        todoAssertService
                .checkStatusCode(result, SC_UNAUTHORIZED)
                .checkTodosList(actual, expected);
    }


}
