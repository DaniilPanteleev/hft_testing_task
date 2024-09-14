package com.todo.services.rest;

import com.todo.config.AppConfig;
import com.todo.entities.ResponseWrapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import org.apache.logging.log4j.io.IoBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

import static io.restassured.http.ContentType.JSON;

@Getter
public abstract class BaseRestService<T extends BaseRestService> {

    @Autowired
    protected AppConfig appConfig;

    protected final Supplier<RequestSpecBuilder> commonRequestSpecBuilderSupplier = () -> {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri(getBaseUri())
                .setContentType(JSON)
                .setRelaxedHTTPSValidation()
                .addFilter(new AllureRestAssured())
                .addFilter(RequestLoggingFilter.logRequestTo(IoBuilder.forLogger().buildPrintStream()))
                .addFilter(ResponseLoggingFilter.logResponseTo(IoBuilder.forLogger().buildPrintStream()))
                .addFilter(new SwaggerCoverageV3RestAssured());
        return rsb;
    };

    protected abstract String getBaseUri();

    protected ResponseWrapper wrap(ValidatableResponse response) {
        return new ResponseWrapper(response);
    }

}
