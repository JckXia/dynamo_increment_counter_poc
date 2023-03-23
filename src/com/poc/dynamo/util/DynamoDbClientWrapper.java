package com.poc.dynamo.util;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

// AWS best practice recommends us to re-use SDK client
public class DynamoDbClientWrapper {
    private static DynamoDbEnhancedClient dynamoClientInstance = null;

    public static DynamoDbEnhancedClient getInstance() {
        if(dynamoClientInstance != null) { return dynamoClientInstance;}

        ProfileCredentialsProvider credProvider = ProfileCredentialsProvider.create();
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(credProvider)
                .build();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
        dynamoClientInstance = enhancedClient;
        return dynamoClientInstance;
    }

    private DynamoDbClientWrapper() {

    }
}
