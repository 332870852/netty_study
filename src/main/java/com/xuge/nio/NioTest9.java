package com.xuge.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest9 {

    public static void main(String []args)throws  Exception{
        RandomAccessFile randomAccessFile=new RandomAccessFile("NioTest9.txt","rw");
        FileChannel fileChannel=randomAccessFile.getChannel();
        //直接在内存中修改数据
        //通过map拿到内存映射对象
        MappedByteBuffer mappedByteBuffer=fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);

        //把第0个元素改为a
        mappedByteBuffer.put(0,(byte)'a');
        mappedByteBuffer.put(3,(byte)'b');
        randomAccessFile.close();
    }
}
