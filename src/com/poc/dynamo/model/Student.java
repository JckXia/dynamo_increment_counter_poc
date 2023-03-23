package com.poc.dynamo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Student {
    private String studentId;
    private Integer age;

    @DynamoDbPartitionKey
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public Integer getAge() { return this.age; }
    public void setAge(Integer age) { this.age = age; }
}
