/**
 * @Title: ClientHandler
 * @Package cn.com.netty.demo1
 * @Description: 客户端业务实现
 * @author hu.rl
 * @date 2017/7/30 14:27
 * @version V1.0
 */
package cn.com.netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author hu.rl
 * @ClassName: ClientHandler
 * @Description: 客户端业务实现
 * @date 2017/7/30 14:27
 */
public class DiscardClientHandler extends ChannelHandlerAdapter {
    /**
     * 用于接受客户端上送消息
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;

            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);

            String body = new String(req, "utf-8");
            System.out.println("Client :" + body);
            String response = "收到服务器端的返回信息：" + body;
        } finally {
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
    }
}
