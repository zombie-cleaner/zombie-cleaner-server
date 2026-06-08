package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.requests.DeleteEventRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.requests.UpdateEventRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.services.AwsService;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.AwsServiceImpl;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ResourceController {
    @Autowired
    private AwsService awsService;

    @GetMapping("/api/{environmentId}/resources")
    public ResponseEntity<@NonNull ApiResponse<List<List<ExternalResourceSummary>>>> getResources(@PathVariable String environmentId) throws AuthenticationException {
        List<List<ExternalResourceSummary>> lists = awsService.getResources(environmentId);

        ApiResponse<List<List<ExternalResourceSummary>>> response = ApiResponse.success(lists);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    Scheduled deletion
    @PostMapping("/api/{environmentId}/resources/delete/{resourceArn}")
    public ResponseEntity<@NonNull ApiResponse<Boolean>> setDeleteEvent(@PathVariable String environmentId, @PathVariable String resourceArn, @Valid @RequestBody DeleteEventRequest deleteEventRequest) throws AuthenticationException{
        Boolean isSuccess = awsService.setDeleteEvent(environmentId, resourceArn, deleteEventRequest);
        ApiResponse<Boolean> response = ApiResponse.success(isSuccess, isSuccess ? "Resource is set to delete" : "Resource was not scheduled to deletion");
        return new ResponseEntity<>(response, isSuccess ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    Scheduled downtime
    @PostMapping("/api/{environmentId}/resource/update/{resourceArn}")
    public ResponseEntity<@NonNull ApiResponse<Boolean>> setUpdateEvent(@PathVariable String environmentId, @PathVariable String resourceArn, @Valid @RequestBody UpdateEventRequest updateEventRequest) throws AuthenticationException{
        Boolean isSuccess = awsService.setUpdateEvent(environmentId, resourceArn, updateEventRequest);
        ApiResponse<Boolean> response = ApiResponse.success(isSuccess, isSuccess ? "Resource is set to update" : "Resource was not scheduled for shutdown");
        return new ResponseEntity<>(response, isSuccess ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
