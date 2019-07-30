package thirdExample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //保存所有以及与客户端建立好连接的客户端channel对象
    private  static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel=ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel!=ch){
                ch.writeAndFlush("["+channel.remoteAddress()+"] 发送的消息:"+msg+"\n");
            }else {
                ch.writeAndFlush("[自己] "+msg+"\n");
            }
        });
    }
    //服务端也客户端连接上了
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        //广播告诉其他客户端，有谁上线了(会写到channelGroup里的所有channel)
        channelGroup.writeAndFlush("[服务器] - "+channel.remoteAddress()+"加入\n");

        //连接上，保存进channelGroup
        channelGroup.add(channel);
    }

    //连接断掉，能处理的唯一处理方法
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        channelGroup.writeAndFlush("[服务器] - "+channel.remoteAddress()+"离开\n");
    }

    //连接活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        System.out.println(channel.remoteAddress()+"上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        System.out.println(channel.remoteAddress()+"下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
