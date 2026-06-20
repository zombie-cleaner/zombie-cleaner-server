package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.requests.DeleteEventRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.requests.UpdateEventRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses.ExternalResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.repositories.EnvironmentRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.AwsService;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import com.zombie_cleaner.zombie_cleaner_server.utils.AuthenticationUtil;
import com.zombie_cleaner.zombie_cleaner_server.utils.aws.AwsClientFactory;
import com.zombie_cleaner.zombie_cleaner_server.utils.aws.AwsUtilFunctions;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

    private final AwsClientFactory factory;
    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private AwsUtilFunctions awsUtil;

    @Override
    public List<List<ExternalResourceSummary>> getResources(String environmentId) throws AuthenticationException {
        Long currentUserId = authenticationUtil.getCurrentUserId();
        String externalId = awsUtil.getExternalId(environmentId);

        List<List<ExternalResourceSummary>> listOfResources = new ArrayList<>();
        listOfResources.add( getEC2List(externalId));
        listOfResources.add( getRDSList(externalId));
        listOfResources.add( getS3List(externalId));
        listOfResources.add(getLogGroupsList(externalId));

        return listOfResources;
    }

    @Override
    public List<ExternalResourceSummary> getRDSList(String externalId) {
        List<ExternalResourceSummary> resources = new ArrayList<>();

        try (RdsClient rdsClient = factory.createClient(RdsClient::builder, externalId)) {
            DescribeDbInstancesResponse response = rdsClient.describeDBInstances();

            for (DBInstance db : response.dbInstances()) {
                resources.add(
                        ExternalResourceSummary.builder()
                                .resourceArn(db.dbInstanceArn())
                                .resourceName(db.dbInstanceIdentifier())
                                .resourceType("RDS")
                                .properties(Map.of(
                                        "allocatedStorage", db.allocatedStorage(),
                                        "dbInstanceStatus", db.dbInstanceStatus(),
                                        "engine", db.engine(),
                                        "instanceClass", db.dbInstanceClass()
                                ))
                                .build()
                );
            }
            return resources;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching RDS instances", e);
        }
    }

    @Override
    public List<ExternalResourceSummary> getEC2List(String externalId) {
        List<ExternalResourceSummary> resources = new ArrayList<>();

        try (Ec2Client ec2Client = factory.createClient(Ec2Client::builder, externalId)) {
            DescribeInstancesResponse response = ec2Client.describeInstances();

            response.reservations().forEach(reservation -> {
                for (Instance instance : reservation.instances()) {
                    resources.add(
                            ExternalResourceSummary.builder()
                                    .resourceArn(instance.instanceId()) // EC2 doesn't always have ARN easily accessible in simple list
                                    .resourceName(instance.tags().stream()
                                            .filter(t -> t.key().equals("Name"))
                                            .findFirst().map(t -> t.value()).orElse(instance.instanceId()))
                                    .resourceType("EC2")
                                    .properties(Map.of(
                                            "instanceType", instance.instanceTypeAsString(),
                                            "state", instance.state().nameAsString(),
                                            "publicIp", instance.publicIpAddress() != null ? instance.publicIpAddress() : "N/A"
                                    ))
                                    .build()
                    );
                }
            });
            return resources;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching EC2 instances", e);
        }
    }

    @Override
    public List<ExternalResourceSummary> getS3List(String externalId) {
        List<ExternalResourceSummary> resources = new ArrayList<>();

        try (S3Client s3Client = factory.createClient(S3Client::builder, externalId)) {
            ListBucketsResponse response = s3Client.listBuckets();

            for (Bucket bucket : response.buckets()) {
                resources.add(
                        ExternalResourceSummary.builder()
                                .resourceArn("arn:aws:s3:::" + bucket.name())
                                .resourceName(bucket.name())
                                .resourceType("S3")
                                .properties(Map.of(
                                        "creationDate", bucket.creationDate().toString()
                                ))
                                .build()
                );
            }
            return resources;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching S3 buckets", e);
        }
    }

    @Override
    public List<ExternalResourceSummary> getLogGroupsList(String externalId) {
        // Implementation for CloudWatch Logs can go here
        return new ArrayList<>();
    }

    @Override
    public boolean setDeleteEvent(String environmentId, String resourceArn, DeleteEventRequest deleteEventRequest) throws AuthenticationException{

        String externalId = awsUtil.getExternalId(environmentId);
        try(EventBridgeClient eventBridgeClient = factory.createClient(EventBridgeClient::builder, externalId)){

//            "detail": {
//                "resourceType": "cloudwatch-log",
//                        "resourceIdentifier": "/aws/lambda/crud_handler-my-app-dev",
//                        "auth-creds": {
//                    "roleArn": "arn:aws:iam::816881850355:role/IdleZeroAccessRole",
//                        "externalId": "816881850355"
//                }
//            }
            Object response = awsUtil.setEvent(eventBridgeClient, "DELETE_RESOURCE");

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean setUpdateEvent(String environmentId, String resourceArn, UpdateEventRequest updateEventRequest) throws AuthenticationException {
        String externalId = awsUtil.getExternalId(environmentId);
        try(EventBridgeClient eventBridgeClient = factory.createClient(EventBridgeClient::builder, externalId)){

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
