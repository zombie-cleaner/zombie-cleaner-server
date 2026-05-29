package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;

import java.util.List;

public interface AwsService {
    public List<ExternalResourceSummary> getRDSList();
    public List<ExternalResourceSummary> getEC2List();
    public List<ExternalResourceSummary> getS3List();
    public List<ExternalResourceSummary> getLogGroupsList();
}
