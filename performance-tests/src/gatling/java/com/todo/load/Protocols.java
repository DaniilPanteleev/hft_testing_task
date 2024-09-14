package com.todo.load;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;


public class Protocols {

    public static HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:4242")
                    .acceptHeader("application/json")
                    .basicAuth("admin", "admin")
                    .disableFollowRedirect();

}
