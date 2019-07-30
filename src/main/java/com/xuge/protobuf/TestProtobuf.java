package com.xuge.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestProtobuf {

    public static void  main(String[]args) throws InvalidProtocolBufferException {
        Datainfo.Student student=Datainfo.Student.newBuilder().setName("张三")
                .setAge(20).setAddress("北京").build();
        byte[]student2ByteArray=student.toByteArray();
        Datainfo.Student student2=Datainfo.Student.parseFrom(student2ByteArray);
        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}
