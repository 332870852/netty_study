package sixthExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt=new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;
        if(randomInt==0){
             myMessage=MyDataInfo.MyMessage.newBuilder().
                    setDataType(MyDataInfo.MyMessage.DataType.StudentType).
                    setStudent(MyDataInfo.Student.newBuilder().
                            setName("小康").
                            setAge(21).
                            setAddress("贺州").
                            build()).build();
        }else if (randomInt==1){
            myMessage=MyDataInfo.MyMessage.newBuilder().
                    setDataType(MyDataInfo.MyMessage.DataType.DogType).
                    setDog(MyDataInfo.Dog.newBuilder().
                            setName("猫").
                            setAge(10).
                            build()).build();
        }else {
            myMessage=MyDataInfo.MyMessage.newBuilder().
                    setDataType(MyDataInfo.MyMessage.DataType.CatType).
                    setCat(MyDataInfo.Cat.newBuilder().
                            setName("小康").
                            setCity("南宁").
                            build()).build();
        }
        ctx.channel().writeAndFlush(myMessage);
    }
}
