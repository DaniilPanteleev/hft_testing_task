package com.todo.base;

import com.todo.containers.TodoContainer;
import com.todo.entities.ResponseWrapper;
import com.todo.services.TodoAssertService;
import com.todo.services.TodoTestStepsService;
import com.todo.services.rest.TodoApiService;
import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import todo_core_openapi.model.TODO;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.todo.constants.Constants.TODO_PORT;
import static com.todo.services.StepsService.expectedStep;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;

@Log4j2
@SpringBootTest
@Import(TestBase.TestConfig.class)
public abstract class TestBase {

    @Autowired
    protected TodoTestStepsService todoTestStepsService;

    @Autowired
    protected TodoAssertService todoAssertService;

    protected void checkJsonSchema(ResponseWrapper responseWrapper, String schemaFileName) {
        expectedStep("Check JSON schema", () -> {
            Allure.addAttachment("JSON schema", Thread.currentThread().getContextClassLoader().getResourceAsStream(schemaFileName));
            responseWrapper.response().assertThat().body(matchesJsonSchemaInClasspath(schemaFileName));
        });
    }

    @Container
    @Autowired
    protected TodoContainer todoContainer;

    @AfterEach
    void tearDown() {
        Allure.addAttachment("todo_container_logs", todoContainer.getLogs());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public TodoContainer todoContainer() {
            return new TodoContainer("todo-app")
                    .withExposedPorts(TODO_PORT)
                    .withEnv("VERBOSE", "1")
                    .waitingFor(Wait.forHttp("/todos"))
                    .withStartupTimeout(Duration.ofSeconds(5));
        }

    }

}
