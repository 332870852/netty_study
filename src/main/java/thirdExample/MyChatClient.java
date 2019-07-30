package thirdExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import secondExample.MyClientInitallizer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//客户端
public class MyChatClient {

    public static void main(String []args)throws Exception{
        EventLoopGroup eventExecutors= new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).
                    handler(new MyChatClientInitalizer());
            //连接
            Channel channel= bootstrap.connect("localhost",10088).sync().channel();
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                channel.writeAndFlush(br.readLine()+"\r\n");
            }
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
