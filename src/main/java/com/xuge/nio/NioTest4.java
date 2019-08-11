package com.xuge.nio;

import io.netty.buffer.UnpooledHeapByteBuf;
import sun.nio.ch.DirectBuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest4 {
    public static void main(String[]args)throws Exception{
        FileInputStream inputStream=new FileInputStream("input.txt");
        FileOutputStream outputStream=new FileOutputStream("output.txt");

        FileChannel inputChannel=inputStream.getChannel();
        FileChannel outputChannel=outputStream.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);

        while (true){
            //如果没有调用clear，则limit=position，read读到的内容为0
            byteBuffer.clear();
            int read=inputChannel.read(byteBuffer);
            if(-1==read){
                break;
            }
            byteBuffer.flip();

            outputChannel.write(byteBuffer);
        }

        inputChannel.close();
        outputChannel.close();

    }
}
