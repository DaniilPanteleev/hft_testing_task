package com.todo.load.cases;

import io.gatling.javaapi.core.Body;
import io.gatling.javaapi.http.HttpRequestActionBuilder;

import java.util.concurrent.atomic.AtomicLong;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class TodoHttpActions {

    private static AtomicLong idCounter = new AtomicLong();

    private static long generateUniqueId() {
        return idCounter.incrementAndGet();
    }

    public static HttpRequestActionBuilder createTodo = http("POST [create todo] /todos")
            .post("/todos")
            .body(StringBody(session -> {
                long uniqueId = generateUniqueId();
                boolean completed = false;
                String text = "Test";

                return "{\"id\": " + uniqueId + ", \"completed\": " + completed + ", \"text\": \"" + text + "\"}";
            }))
            .check(status().is(201));

}
