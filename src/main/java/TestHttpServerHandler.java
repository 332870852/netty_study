import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

//处理方法
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {//SimpleChannelInboundHandler 对进来请求的处理
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {//读取客户端发送的请求，并向客户端返回响应

        //获取远程地址
        System.out.println(ctx.channel().remoteAddress());
        if(msg instanceof HttpRequest){ //判断是不是http的请求

            HttpRequest httpRequest=(HttpRequest)msg;
            System.out.println("请求方法名:"+httpRequest.method().name());
            URI uri=new URI(httpRequest.uri());
            System.out.println(uri.getPath());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon.ico");
                return;
            }

            ByteBuf context= Unpooled.copiedBuffer("Hello world", CharsetUtil.UTF_8);
            //构建HTTP返回响应
            FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,context);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,context.readableBytes());
            ctx.writeAndFlush(response);
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }
}
