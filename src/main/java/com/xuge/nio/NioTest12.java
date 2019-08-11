package com.xuge.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioTest12 {
    public static void main(String[]args) throws IOException {
        //定义监听的5个端口号
        int []ports=new int[5];
        ports[0]=5000;
        ports[1]=5001;
        ports[2]=5002;
        ports[3]=5003;
        ports[4]=5004;

        Selector selector=Selector.open();
       for (int i=0;i<ports.length;i++){
           ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
           //配置是否阻塞，false-不阻塞
           serverSocketChannel.configureBlocking(false);
           //serverSocketChannel.socket() 返回的是与ServerSocketChannel相关联的socket
           ServerSocket serverSocket=serverSocketChannel.socket();
            //绑定socket 地址
           InetSocketAddress address=new InetSocketAddress(ports[i]);
           serverSocket.bind(address);

           /**
            * Registers this channel with the given selector, returning a selection
            *  key
            */
           //OP_ACCEPT 对连接事件感兴趣 ，返回SelectionKey对象
           serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口： "+ports[i]);
       }

       while (true){
           //一旦有返回，说明channel中存在事件
           int numbers=selector.select();
           System.out.println("numbers: "+numbers);

           //获取对应的事件
           Set<SelectionKey> selectionKeys= selector.selectedKeys();
           System.out.println("selectionKeys: "+selectionKeys);
          Iterator<SelectionKey>iter= selectionKeys.iterator();
          while (iter.hasNext()){
              SelectionKey selectionKey=iter.next();
              if (selectionKey.isAcceptable()){
                  //获取到ServerSocketChannel
                  ServerSocketChannel serverSocketChannel= (ServerSocketChannel) selectionKey.channel();
                  SocketChannel socketChannel=serverSocketChannel.accept();
                  socketChannel.configureBlocking(false);
                    //关注读
                  socketChannel.register(selector, SelectionKey.OP_READ);
                  //一定要  iter.remove(); 表示当前事件使用完了
                  iter.remove();

                  System.out.println("获得客户端连接："+socketChannel);
              }else if (selectionKey.isReadable()){//读
                    SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
                    int bytesRead=0;
                    while (true){
                        ByteBuffer byteBuffer=ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read=socketChannel.read(byteBuffer);
                        if(read<=0)
                            break;
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        bytesRead+=read;
                    }
                    System.out.println("读取： "+bytesRead+" , 来自于: "+socketChannel);
                    iter.remove();
              }
          }
       }
    }
}
