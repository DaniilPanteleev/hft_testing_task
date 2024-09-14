package com.todo.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.SneakyThrows;

@Builder
public class TODONegativeEntity {

    @JsonProperty("id")
    private Object id;

    @JsonProperty("text")
    private Object text;

    @JsonProperty("completed")
    private Object completed;

    @SneakyThrows
    private String toJson(JsonInclude.Include include) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);

        return mapper.writeValueAsString(this);
    }

    public String toJson() {
        return toJson(JsonInclude.Include.ALWAYS);
    }

    public String toJsonWithoutNulls() {
        return toJson(JsonInclude.Include.NON_NULL);
    }


}
