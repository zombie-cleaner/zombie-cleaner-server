package com.zombie_cleaner.zombie_cleaner_server.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.core.SdkClient;
import software.amazon.awssdk.regions.Region;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class AwsClientFactory {

    @Value("${aws.region}")
    private String region;

    private final AwsCredentialUtil awsCredentialUtil;

    private AwsCredentialsProvider getCredentialsProvider(){
        return awsCredentialUtil.getAssumeRoleCredentialsProvider();
    }

    // Common generic method to build any AWS client
    public <B extends AwsClientBuilder<B, C>, C extends SdkClient> C createClient(Supplier<AwsClientBuilder<B, C>> builderSupplier) {

        return builderSupplier.get()
                .region(Region.of(region))
                .credentialsProvider(getCredentialsProvider())
                .build();
    }
}
