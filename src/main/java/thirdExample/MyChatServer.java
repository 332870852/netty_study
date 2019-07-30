package thirdExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

//服务端
public class MyChatServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup= new NioEventLoopGroup();
        EventLoopGroup workGroup= new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).
                    childHandler(new MyChatServerInitalizer());

            ChannelFuture channelFuture= serverBootstrap.bind(10088).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
