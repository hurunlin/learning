/**
 * @Title: DiscardServerHandler
 * @Package cn.com.netty.demo1
 * @Description: 业务实现类handler
 * @author hu.rl
 * @date 2017/7/29 9:24
 * @version V1.0
 */
package cn.com.netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author hu.rl
 * @ClassName: DiscardServerHandler
 * @Description: 业务实现类handler
 * @date 2017/7/29 9:24
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel active... ");
    }

    /**
     * 用于接受客户端上送消息
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            System.out.println("业务处理！！！");
            System.out.print((char) in.readByte());
            System.out.flush();
        } finally {
            // 关闭流
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当出现异常时会调用该方法
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
