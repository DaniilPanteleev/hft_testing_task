package com.todo.load;

import com.todo.load.scenarios.TodoScenario;
import io.gatling.javaapi.core.Simulation;

import java.time.Duration;

import static com.todo.load.Protocols.httpProtocol;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;

public class Stability extends Simulation {

    {
        setUp(
                TodoScenario.scn.injectOpen(
                        rampUsersPerSec(0)
                                .to(500)
                                .during(Duration.ofMinutes(5)),
                        constantUsersPerSec(500)
                                .during(Duration.ofMinutes(5))
                )
        ).protocols(
                httpProtocol
        );
    }
}
