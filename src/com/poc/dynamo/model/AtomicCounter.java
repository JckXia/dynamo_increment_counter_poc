package com.poc.dynamo.model;

import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAtomicCounter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class AtomicCounter {
    private int counter_id;
    private Long counter;

    @DynamoDbPartitionKey
    public int getCounter_id (){ return this.counter_id; }
    public void setCounter_id(int counterId) { this.counter_id = counterId; }

    @DynamoDbAtomicCounter(startValue = 50)
    public Long getCustomCounter() { return counter; }
    public void setCustomCounter(Long counter) { this.counter = counter; }
}
