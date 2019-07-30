package secondExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyServerInitallizer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        //解码器
        pipeline.addLast("LengthFieldBasedFrameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        //字符串解码，用于接收字符串
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //字符串编码器
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //自定义处理器
        pipeline.addLast(new MyServerHandler());
    }
}
