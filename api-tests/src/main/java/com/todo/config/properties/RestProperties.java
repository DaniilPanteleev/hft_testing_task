package com.todo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest")
public record RestProperties(String login, String password) {
}
