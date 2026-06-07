package com.zombie_cleaner.zombie_cleaner_server.utils.aws;

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

    private AwsCredentialsProvider getCredentialsProvider(String externalId){
        return awsCredentialUtil.getAssumeRoleCredentialsProvider(externalId);
    }

    // Common generic method to build any AWS client
//    <B extends AwsClientBuilder<B, C>, C extends SdkClient> (The Type Parameters)
//    This declares the generic types used inside the method.
//
//    C must be a subclass of SdkClient (e.g., RdsClient, S3Client).
//
//    B must be an AwsClientBuilder that knows how to configure itself (B) and ultimately build that specific client (C).
//    This self-referencing syntax (B extends AwsClientBuilder<B, C>) is known as the Simulated Self-Type pattern, ensuring method chaining like .region().credentialsProvider() returns the correct builder type.
//
//    C (The Return Type)
//    The method will return the exact client type you need (e.g., if you pass an RdsClient builder, it returns an RdsClient).
//
//    Supplier<AwsClientBuilder<B, C>> builderSupplier (The Argument): A functional interface that takes no arguments and returns a fresh builder instance. When you pass RdsClient::builder, Java treats it as a supplier that runs RdsClient.builder().
    public <B extends AwsClientBuilder<B, C>, C extends SdkClient> C createClient(Supplier<AwsClientBuilder<B, C>> builderSupplier, String externalId) {

        return builderSupplier.get()
                .region(Region.of(region))
                .credentialsProvider(getCredentialsProvider(externalId))
                .build();
    }
}
