/**
 * @Title: DiscardServer
 * @Package cn.com.netty.demo1
 * @Description: 服务端
 * @author hu.rl
 * @date 2017/7/30 13:02
 * @version V1.0
 */
package cn.com.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hu.rl
 * @ClassName: DiscardServer
 * @Description: 服务端
 * @date 2017/7/30 13:02
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        /**
         * 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
         * 并且可以通过构造函数来配置他们的关系。
         */
        // 接收请求的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 启动NIO服务的辅助启动类。你可以在这个服务中直接使用Channel，但是这会是一个复杂的处理过程，在很多情况下你并不需要这样做。
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    /**
                     * 这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel。
                     * 也许你想通过增加一些处理类比如DiscardServerHandle来配置一个新的Channel或者其对应的ChannelPipeline来实现你的网络程序。
                     * 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，然后提取这些匿名类到最顶层的类上
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 初始化通道
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 具体业务实现类
                            channel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    /**
                     * 你可以设置这里指定的通道实现的配置参数。我们正在写一个TCP/IP的服务端，因此我们被允许设置socket的参数选项比如tcpNoDelay和keepAlive。
                     * 请参考ChannelOption和详细的ChannelConfig实现的接口文档以此可以对ChannelOptions的有一个大概的认识。
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置发送缓区大小
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // 设置接受缓冲区大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    /**
                     * option()是提供给NioServerSocketChannel用来接收进来的连接。
                     * childOption()是提供给由父管道ServerChannel接收到的连接，在这个例子中也是NioServerSocketChannel。
                     */
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            /**
             * 可以多次调用bind()方法(基于不同绑定地址)。
             * 使用异步绑定方式，非阻塞
             */
            ChannelFuture cf = b.bind(port).sync(); // (7)
            // 等待服务器监听端口关闭
            cf.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8765;
        }
        new DiscardServer(port).run();
    }
}
