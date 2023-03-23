package com.poc.dynamo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Student {
    private String student_id;
    private Integer age;

    @DynamoDbPartitionKey
    public String getStudent_id() { return this.student_id; }
    public void setStudent_id(String studentId) { this.student_id = studentId; }

    public Integer getAge() { return this.age; }
    public void setAge(Integer age) { this.age = age; }
}
