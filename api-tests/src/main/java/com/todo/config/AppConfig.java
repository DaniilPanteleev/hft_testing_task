package com.todo.config;

import com.todo.config.properties.RestProperties;
import com.todo.containers.TodoContainer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.SimpleThreadScope;

import java.util.HashMap;
import java.util.Map;

import static com.todo.constants.Constants.*;

@Getter
@Configuration
@EnableConfigurationProperties({RestProperties.class})
@Profile({LOCAL, DOCKER})
public class AppConfig {

    private RestProperties restProperties;

    public AppConfig(RestProperties restProperties) {
        this.restProperties = restProperties;
    }

    @Bean
    public static CustomScopeConfigurer threadScopeConfigurer() {
        CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();
        Map<String, Object> scopes = new HashMap<>();
        scopes.put(THREAD, SimpleThreadScope.class);
        scopeConfigurer.setScopes(scopes);
        return scopeConfigurer;
    }

}
