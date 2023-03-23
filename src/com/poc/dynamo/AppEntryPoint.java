package com.poc.dynamo;

import com.poc.dynamo.util.DynamoDbClientWrapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

public class AppEntryPoint {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        DynamoDbEnhancedClient d = DynamoDbClientWrapper.getInstance();
        System.out.println("Successfully established connection");
    }
}
