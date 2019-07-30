import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) throws Exception {
        //1. 定义两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();//bossGroup 负责接收连接
        EventLoopGroup workGroup = new NioEventLoopGroup(); //连接的处理
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //反射的方式处理
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());

            //3.绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(10086).sync();

            //4.关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            //5.结束
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }


}
