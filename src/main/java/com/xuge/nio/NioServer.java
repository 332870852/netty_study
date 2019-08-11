package com.xuge.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {
    //维护客户端连接信息
    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //一定要配置成非阻塞
        serverSocketChannel.configureBlocking(false);
        //1.获取对应的客户端socket
        ServerSocket serverSocket = serverSocketChannel.socket();
        //2.绑定IP 和端口
        InetSocketAddress address = new InetSocketAddress(10098);
        serverSocket.bind(address);

        //3.创建selector对象
        Selector selector = Selector.open();
        //4.管道serverSocketChannel注册到selector选择器上， 关注Accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //5.事件处理
        while (true) {
            try {
                selector.select();//没有事件时，一直阻塞，返回它关注事件的数量
                Set<SelectionKey> selectionKeysSet = selector.selectedKeys(); //关注的事件集合SelectionKey

                selectionKeysSet.forEach(selectionKey -> {
                    final SocketChannel client;
                    try {
                        //6. 判断获取到的事件类型
                        if (selectionKey.isAcceptable()) {//客户端连接事件
                            //因为只有步骤4 把serverSocketChannel注册到selector而已，
                            // 所以一定知道selectionKey.channel() 返回的是ServerSocketChannel类型
                            //根据注册类型的不同，来确定具体类型
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();

                            client = server.accept();
                            //建立好连接后也注册到selector上
                            client.configureBlocking(false);
                            //关注读事件
                            client.register(selector, SelectionKey.OP_READ);

                            String key = "【" + UUID.randomUUID().toString() + "】";
                            clientMap.put(key,client);

                        }else if (selectionKey.isReadable()){//是否有数据可读
                            client=(SocketChannel)selectionKey.channel();

                            ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                            int count=client.read(readBuffer);
                            if(count>0){
                                readBuffer.flip();
                                //utf-8 解码
                                Charset charset=Charset.forName("utf-8");
                                String receivedMessage=String.valueOf(charset.decode(readBuffer).array());

                                System.out.println(client+": "+receivedMessage);

                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
