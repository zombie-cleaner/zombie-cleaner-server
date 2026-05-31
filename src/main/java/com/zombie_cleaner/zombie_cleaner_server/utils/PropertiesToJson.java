package com.zombie_cleaner.zombie_cleaner_server.utils;

import lombok.AllArgsConstructor;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
@AllArgsConstructor
public class PropertiesToJson {
    private ObjectMapper objectMapper = new ObjectMapper();
    public JacksonProperties.Json getJson(Map<String,Object> properties){
        String JsonString = objectMapper.writeValueAsString(properties);
        return new JacksonProperties.Json(JsonString);
    }
}
