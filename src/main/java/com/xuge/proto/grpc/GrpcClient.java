package com.xuge.proto.grpc;

import com.xuge.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;

public class GrpcClient {


    public static void main(String[] args)  {

        //usePlaintext使用文本明文传输
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 10096).
                usePlaintext(true).build();
        //同步的
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.
                newBlockingStub(managedChannel);
        //StudentServiceStub 异步的通信
        StudentServiceGrpc.StudentServiceStub stub= StudentServiceGrpc.newStub(managedChannel);
       MyResponse response= blockingStub.getRealNameByUsername
               (MyRequest.newBuilder().setUsername("zhangsan").build());
       System.out.println(response.getRealname());
//
//       System.out.println("---------------");
//        Iterator<StudentResponse> iter=blockingStub.
//                getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
//        while (iter.hasNext()){
//            StudentResponse studentResponse=iter.next();
//            System.out.println(studentResponse.getName()+", "
//                    +studentResponse.getAge()+", "+
//                    studentResponse.getCity());
//        }
        System.out.println("---------------");

        StreamObserver<StudentResponseList> studentResponseListStreamObserver=new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(studentResponse -> {
                    System.out.println(studentResponse.getName());
                    System.out.println(studentResponse.getAge());
                    System.out.println(studentResponse.getCity());
                    System.out.println("*******");
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed!");
            }
        };

        StreamObserver<StudentRequest> studentRequestStreamObserver=stub.
                getStudentsWrapperByAges(studentResponseListStreamObserver);
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());

        studentRequestStreamObserver.onCompleted();
    }
}
