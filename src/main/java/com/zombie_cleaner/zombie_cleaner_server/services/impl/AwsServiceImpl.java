package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.dtos.resource.responses.ResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.services.AwsService;
import com.zombie_cleaner.zombie_cleaner_server.utils.AwsClientFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AwsServiceImpl implements AwsService {

    @Value("${aws.region}")
    private String region;

    AwsClientFactory factory = new AwsClientFactory();

    @Override
    public List<ExternalResourceSummary> getRDSList() {
        List<ExternalResourceSummary> resources = new ArrayList<>();

        try(RdsClient rdsClient = factory.createClient(RdsClient::builder)){
            DescribeDbInstancesResponse response = rdsClient.describeDBInstances();

            for(DBInstance db : response.dbInstances()){

                JacksonProperties.Json propertiesJson =

                resources.add(
                        new ExternalResourceSummary(
                              db.dbInstanceArn(),
                              db.dbName(),
                              "RDS",

                        )
                );
            }

            return resources;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ExternalResourceSummary> getEC2List(){

    }
    public List<ExternalResourceSummary> getS3List(){

    }
    public List<ExternalResourceSummary> getLogGroupsList(){

    }
}
