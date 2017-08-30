/**
 * @Title: Client
 * @Package cn.com.netty.demo1
 * @Description: 客户端
 * @author hu.rl
 * @date 2017/7/30 14:25
 * @version V1.0
 */
package cn.com.netty.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author hu.rl
 * @ClassName: Client
 * @Description: 客户端
 * @date 2017/7/30 14:25
 */
public class DiscardClient {

    private int port;

    private DiscardClient(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new DiscardClientHandler());

            // 建立链接
            ChannelFuture cf1 = b.connect("127.0.0.1", port).sync();
            // 发送请求到服务端
            cf1.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("Hello Word!!", CharsetUtil.UTF_8), new InetSocketAddress("127.0.0.1", port))).sync();
            cf1.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8765;
        }
        new DiscardClient(port).run();
    }
}
