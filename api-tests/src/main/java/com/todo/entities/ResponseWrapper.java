package com.todo.entities;

import io.restassured.response.ValidatableResponse;
import lombok.Getter;

import java.util.List;

public record ResponseWrapper(ValidatableResponse response) {

    public <T> T as(Class<T> clazz) {
        return as(".", clazz);
    }

    public <T> T as(String path, Class<T> clazz) {
        return response.extract().jsonPath().getObject(path, clazz);
    }

    public <T> List<T> asList(String path, Class<T> clazz) {
        return response.extract().jsonPath().getList(path, clazz);
    }

    public int getStatusCode() {
        return response.extract().response().getStatusCode();
    }

}
