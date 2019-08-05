package com.xuge.proto.grpc;

import com.xuge.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class GrpcClient {


    public static void main(String[] args)  {

        //usePlaintext使用文本明文传输
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 10096).
                usePlaintext(true).build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.
                newBlockingStub(managedChannel);

       MyResponse response= blockingStub.getRealNameByUsername
               (MyRequest.newBuilder().setUsername("zhangsan").build());
       System.out.println(response.getRealname());

       System.out.println("---------------");
        Iterator<StudentResponse> iter=blockingStub.
                getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
        while (iter.hasNext()){
            StudentResponse studentResponse=iter.next();
            System.out.println(studentResponse.getName()+", "
                    +studentResponse.getAge()+", "+
                    studentResponse.getCity());
        }
        System.out.println("---------------");
    }
}
