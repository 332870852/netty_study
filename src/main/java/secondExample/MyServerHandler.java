package secondExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

// 传输的内容是String类型，// 传输的内容是String类型
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //接收消息
        System.out.println(ctx.channel().remoteAddress()+","+ msg);
        ctx.channel().writeAndFlush("from server:"+ UUID.randomUUID());

    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
        ctx.close();  //发送异常，把socket关闭
    }
}
