package sixthExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import secondExample.MyClientInitallizer;

public class TestClient {

    public static void main(String []args)throws Exception{
        EventLoopGroup eventExecutors= new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            //使用handler 和childhandler的区别是，handler处理 第一个EventLoopGroup，handler是处理第二个EventLoopGroup，相当于boosExecutors交给workExecutors之后再处理
            //注意是NioSocketChannel
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).
                    handler(new TestClientInitalizer());
            //连接
            ChannelFuture channelFuture= bootstrap.connect("localhost",10090).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
