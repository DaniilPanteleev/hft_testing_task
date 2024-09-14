package com.todo.constants;

public class Constants {

    // Env
    public static final String LOCAL = "local";
    public static final String DOCKER = "docker";

    // Suite
    public static final String REGRESSION = "regression";
    public static final String SMOKE = "smoke";

    // Spring
    public static final String THREAD = "thread";

    // Testcontainers
    public static final Integer TODO_PORT = 4242;

    // Allure
    public static final String EPIC = "Todo service api";
    public static final String STORY_GET = "GET /todos";
    public static final String STORY_POST = "POST /todos";
    public static final String STORY_PUT = "PUT /todos/:id";
    public static final String STORY_DELETE = "DELETE /todos/:id";
    public static final String POSITIVE = "Positive test";
    public static final String NEGATIVE = "Negative test";

}
