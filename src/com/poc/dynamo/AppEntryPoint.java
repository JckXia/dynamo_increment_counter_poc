package com.poc.dynamo;

import com.poc.dynamo.model.AtomicCounter;
import com.poc.dynamo.util.DynamoDbClientWrapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


public class AppEntryPoint {
    public static void main(String[] args) {
        SetupCounter();
    }

    public static void SetupCounter() {
        DynamoDbEnhancedClient d = DynamoDbClientWrapper.getInstance();
        TableSchema<AtomicCounter> counterSchema = TableSchema.fromBean(AtomicCounter.class);
        DynamoDbTable<AtomicCounter> atomicCounterTable = d.table("AtomicCounter", counterSchema);
        System.out.println("Successfully established connection");

        for(int i=0;i<4;i++)
        {
            AtomicCounter counter = new AtomicCounter();
            counter.setCounter_id(i);
            atomicCounterTable.updateItem(counter);
        }

    }
}
