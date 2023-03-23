package com.poc.dynamo.model;

import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAtomicCounter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class AtomicCounter {
    private Integer counterId;
    private Long counter;
    @DynamoDbPartitionKey
    public Integer getAtomicCounterId() { return this.counterId; }
    public void setAtomicCounterId(Integer accountId) { this.counterId = accountId; }

    @DynamoDbAtomicCounter
    public Long getCounter() { return counter; }
    public void setCounter(Long counter) { this.counter = counter; }
}
