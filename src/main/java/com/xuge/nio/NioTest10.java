package com.xuge.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

//文件锁
public class NioTest10 {

    public static void main(String []args)throws  Exception{

        RandomAccessFile randomAccessFile=new RandomAccessFile("NioText10.txt","rw");
        FileChannel fileChannel=randomAccessFile.getChannel();
        //获取文件锁. 从第3个位置锁，锁6个长度，ture为共享锁，false为排它锁
        FileLock fileLock=fileChannel.lock(3,6,true);

        System.out.println("valid: "+fileLock.isValid());
        System.out.println("lock type: "+fileLock.isShared());
        //释放锁
        fileLock.release();
        randomAccessFile.close();
    }
}
