package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;

import java.util.List;

public interface AwsService {
    public List<List<ExternalResourceSummary>> getResources(String environmentId);
    public List<ExternalResourceSummary> getRDSList(String externalId);
    public List<ExternalResourceSummary> getEC2List(String externalId);
    public List<ExternalResourceSummary> getS3List(String externalId);
    public List<ExternalResourceSummary> getLogGroupsList(String externalId);
}
