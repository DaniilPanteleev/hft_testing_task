package com.todo.load.scenarios;

import com.todo.load.cases.TodoHttpActions;
import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;

public class TodoScenario {

    public static ScenarioBuilder scn = scenario("DebugTodoScenarioScenario")
            .exec(TodoHttpActions.createTodo);

}
