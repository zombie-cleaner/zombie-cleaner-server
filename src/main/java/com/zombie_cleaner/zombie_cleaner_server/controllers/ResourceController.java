package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.AwsServiceImpl;
import lombok.NonNull;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ResourceController {
    @Autowired
    private AwsServiceImpl awsService;

    @GetMapping("/api/{environmentId}/resources")
    public ResponseEntity<@NonNull ApiResponse<List<List<ExternalResourceSummary>>>> getResources(@PathVariable String environmentId) throws AuthenticationException {
        List<List<ExternalResourceSummary>> lists = awsService.getResources(environmentId);

        ApiResponse<List<List<ExternalResourceSummary>>> response = ApiResponse.success(lists);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/api/{environmentId}/resources/delete/{resourceArn}")
//    public ResponseEntity<@NonNull ApiResponse<Boolean>> setDeleteEvent(@PathVariable String environmentId, @PathVariable String resourceArn){
//
//    }
//
//    Scheduled downtime
//    @PostMapping("/api/{environmentId}/resource/update/{resourceArn}")
//    public ResponseEntity<@NonNull ApiResponse<Boolean>> setUpdateEvent(@PathVariable String environmentId, @PathVariable String resourceArn){
//
//    }
}
