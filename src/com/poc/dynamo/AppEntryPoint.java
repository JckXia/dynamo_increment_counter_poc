package com.poc.dynamo;

import com.poc.dynamo.model.AtomicCounter;
import com.poc.dynamo.model.Student;
import com.poc.dynamo.util.DynamoDbClientWrapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppEntryPoint {
    public static Integer COUNTER_NUM = 3;
    public static void main(String[] args) {
       // SetupCounter();
       // generateUniqueIdentifier(24);
       createStudentObjects();
    }

    // In the beginning spin up 3 counters
    public static void SetupCounter() {
        DynamoDbEnhancedClient d = DynamoDbClientWrapper.getInstance();
        TableSchema<AtomicCounter> counterSchema = TableSchema.fromBean(AtomicCounter.class);
        DynamoDbTable<AtomicCounter> atomicCounterTable = d.table("AtomicCounter", counterSchema);

        for(int i=0;i<= COUNTER_NUM;i++)
        {
            AtomicCounter counter = new AtomicCounter();
            counter.setCounter_id(i);
            atomicCounterTable.updateItem(counter);
        }

    }

    private static class CreateStudentObjects implements  Runnable {
        private final Student _student;
        public CreateStudentObjects(Student stu) {
            this._student = stu;
        }
        @Override
        public void run() {
            DynamoDbEnhancedClient d = DynamoDbClientWrapper.getInstance();
            TableSchema<Student> studentSchema = TableSchema.fromBean(Student.class);
            DynamoDbTable<Student> atomicCounterTable = d.table("Students", studentSchema);

            int counterId = this._student.getAge() % COUNTER_NUM;

            String studentId = "SI" + counterId + "D" + generateUniqueIdentifier(this._student.getAge());

            this._student.setStudent_id(studentId);
            System.out.println("Student age: "+ this._student.getAge() + "  Student ID: " + studentId);
            atomicCounterTable.updateItem(this._student);
        }
    }

    // Use student age to load balance between different counters
    public static void createStudentObjects() {
        Student studentA = new Student();
        studentA.setAge(24);

        Student studentB = new Student();
        studentB.setAge(21);

        List<Student> studentList = List.of(studentA,studentB);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Spin up some threads to test id atomicity increments
        for(Student s : studentList) {
            executor.execute(new CreateStudentObjects(s));
        }

        executor.shutdown();
    }



    private static Long generateUniqueIdentifier(int studentAge) {
        DynamoDbEnhancedClient d = DynamoDbClientWrapper.getInstance();
        TableSchema<AtomicCounter> counterSchema = TableSchema.fromBean(AtomicCounter.class);
        DynamoDbTable<AtomicCounter> atomicCounterTable = d.table("AtomicCounter", counterSchema);

        int counterId = studentAge % COUNTER_NUM;

        System.out.println("Using Counter " + counterId);

        // Get the counterId
        AtomicCounter oldCounter = new AtomicCounter();
        oldCounter.setCounter_id(counterId);

        /**
         *     It's crucial to take counter value from this updated variable, and not
         *    from the AtomicCounter from another `getItem` call, as there aren't no locks preventing another update from happening
         */

        AtomicCounter updated = atomicCounterTable.updateItem(oldCounter);

        return updated.getCustomCounter();
    }
}
