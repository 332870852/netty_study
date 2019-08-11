package com.xuge.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest3 {
    public static void main(String[]args)throws Exception{

        FileOutputStream fileOutputStream=new FileOutputStream("NioTest3.txt");
        FileChannel fileChannel=fileOutputStream.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocate(512);
        byte[] message="hello world welcome, nihao".getBytes();
        for (int i=0;i<message.length;i++){
            byteBuffer.put(message[i]);
        }

        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
