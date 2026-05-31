package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.AwsServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ResourceController {
    @Autowired
    private AwsServiceImpl awsService;

    @GetMapping("/api/resources")
    public ResponseEntity<@NonNull ApiResponse<List<List<ExternalResourceSummary>>>> getResources(){
        List<List<ExternalResourceSummary>> lists = new ArrayList<>();
        lists.add(awsService.getEC2List());
        lists.add(awsService.getS3List());
        lists.add(awsService.getRDSList());
        lists.add(awsService.getLogGroupsList());

        ApiResponse<List<List<ExternalResourceSummary>>> response = ApiResponse.success(lists);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
