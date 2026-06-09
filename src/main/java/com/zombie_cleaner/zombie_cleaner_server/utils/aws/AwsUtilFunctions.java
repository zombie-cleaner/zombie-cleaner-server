package com.zombie_cleaner.zombie_cleaner_server.utils.aws;

import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.EnvironmentServiceImpl;
import com.zombie_cleaner.zombie_cleaner_server.utils.AuthenticationUtil;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import tools.jackson.databind.ObjectMapper;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class AwsUtilFunctions {

    @Autowired
    EnvironmentService environmentService;

    public String getExternalId(String environmentId) throws AuthenticationException {
        Environment environment= environmentService.getEnvironmentById(environmentId);
        return environment.getExternalId();
    }

    public void setEvent(EventBridgeClient eventBridgeClient, LocalDateTime time, Object payload, String typeOfEvent ) throws RuntimeException{
        ObjectMapper objectMapper = new ObjectMapper();

        // convert payload to string
        String detail = objectMapper.writeValueAsString(payload);

        PutEventsRequestEntry eventsRequestEntry = PutEventsRequestEntry.builder()
                .source("com.zombie_cleaner.zombie_cleaner_server")
                .detailType(typeOfEvent)
                .time(time.atZone( java.time.ZoneId.systemDefault() ).toInstant())
                .detail(detail)
                .build();

        PutEventsRequest putEventsRequest = PutEventsRequest.builder()
                .entries(eventsRequestEntry)
                .build();

        eventBridgeClient.putEvents(putEventsRequest);
    }
}
