package com.zombie_cleaner.zombie_cleaner_server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

@Component
public class AwsCredentialUtil {
    @Value("${aws.assume-role.role-arn}")
    private String roleArn;

    @Value("${aws.assume-role.session-name}")
    private String sessionName;

    @Value("${aws.region}")
    private String region;

    public AwsCredentialsProvider getAssumeRoleCredentialsProvider() throws RuntimeException {
        try (StsClient stsClient = StsClient.builder()
                .region(Region.of(region))
                .build()
        ) {
            AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
                    .roleArn(roleArn)
                    .roleSessionName(sessionName)
                    .build();
            return StsAssumeRoleCredentialsProvider.builder()
                    .stsClient(stsClient)
                    .refreshRequest(assumeRoleRequest)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
