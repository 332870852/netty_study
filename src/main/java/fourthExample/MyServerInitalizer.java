package fourthExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import thirdExample.MyChatServerHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitalizer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        //心跳包机制，指定事件内没有读写则触发事件，读空闲5秒，写空闲7秒。读写空闲3秒
        pipeline.addLast(new IdleStateHandler(5,7,3,TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
