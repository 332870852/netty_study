package com.xuge.proto.grpc;

import com.xuge.proto.*;
import io.grpc.stub.StreamObserver;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接受到客户端信息："+request.getUsername());
        //返回客户端信息
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张四").build());
        //告诉客户端发送消息完了
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        responseObserver.onNext
                (StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build());
        responseObserver.onNext
                (StudentResponse.newBuilder().setName("李想").setAge(21).setCity("广西").build());
        responseObserver.onNext
                (StudentResponse.newBuilder().setName("里斯").setAge(55).setCity("北京").build());
        responseObserver.onCompleted();
    }
}
