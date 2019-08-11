package com.xuge.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioTest11 {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(10097);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2); //最多读2个字节
        buffers[1] = ByteBuffer.allocate(3);//最多读3个字节
        buffers[2] = ByteBuffer.allocate(4);//最多读4个字节

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long r = socketChannel.read(buffers);
                bytesRead += r;
                System.out.println("bytesRead: " + bytesRead);
                Arrays.asList(buffers).
                        stream().
                        map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit()).
                        forEach(System.out::println);
            }
            //反转
            Arrays.asList(buffers).
                    forEach(buffer -> {
                        buffer.flip();
                    });

            long byteWritten = 0;
            //写回客户端
            while (byteWritten < messageLength) {
                long r = socketChannel.write(buffers);
                byteWritten += r;
            }

            Arrays.asList(buffers).
                    forEach(buffer -> {
                        buffer.clear();
                    });
            System.out.println("bytesRead: " + bytesRead + ", bytesWritten: " + byteWritten + ", messageLength: " + messageLength);

        }
    }
}
