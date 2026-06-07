package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.requests.DeleteEventRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.requests.UpdateEventRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;
import org.apache.tomcat.websocket.AuthenticationException;

import java.util.List;

public interface AwsService {
    public List<List<ExternalResourceSummary>> getResources(String environmentId) throws AuthenticationException;
    public List<ExternalResourceSummary> getRDSList(String externalId);
    public List<ExternalResourceSummary> getEC2List(String externalId);
    public List<ExternalResourceSummary> getS3List(String externalId);
    public List<ExternalResourceSummary> getLogGroupsList(String externalId);

    public boolean setDeleteEvent(String environmentId, String resourceArn, DeleteEventRequest deleteEventRequest) throws AuthenticationException;
    public boolean setUpdateEvent(String environmentId, String resourceArn, UpdateEventRequest updateEventRequest) throws AuthenticationException;
    }
