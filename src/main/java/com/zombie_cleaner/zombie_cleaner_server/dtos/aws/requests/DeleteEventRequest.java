package com.zombie_cleaner.zombie_cleaner_server.dtos.aws.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeleteEventRequest {
    private String resourceIdentifier;
    private LocalDateTime shutdownStartTime;
    private LocalDateTime shutdownEndTime;
}
