/**
 * @Title: Server
 * @Package cn.com.socket.JDKNioServer
 * @Description: NIO 服务端
 * @author hu.rl
 * @date 2017/6/26 15:07
 * @version V1.0
 */
package cn.com.socket.JDKNioServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author hu.rl
 * @ClassName: Server
 * @Description: NIO 服务端
 * @date 2017/6/26 15:07
 */
public class Server implements Runnable {

    private Selector selector;

    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public Server(int port) {
        try {
            // 打开多路复用器
            this.selector = Selector.open();
            // 打开服务端通道
            ServerSocketChannel channel = ServerSocketChannel.open();
            // 设置服务器通道为非阻塞模式
            channel.configureBlocking(false);
            // 绑定地址,将通道注册到selector中，并且设置监听阻塞事件
            channel.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start port:" + port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                // 必须要让多路复用器开始监听
                this.selector.select();
                // 获取到selector选择的结果集
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();

                // 轮循通道
                while (keys.hasNext()) {
                    // 获取keys中的元素
                    SelectionKey key = keys.next();
                    // 获取之后完成之后删除
                    keys.remove();
                    // 如果有效
                    if (key.isValid()) {
                        // 如果为阻塞状态
                        if (key.isAcceptable()) {
                            this.accept(key);
                        }
                        // 如果状态为可读
                        if (key.isReadable()) {
                            this.read(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理可读通道方法
     *
     * @param key
     */
    private void read(SelectionKey key) throws Exception {
        // 清空缓存区数据
        this.byteBuffer.clear();
        // 设置之前注册的socket通道对象
        SocketChannel channel = (SocketChannel) key.channel();
        // 读取数据
        int count = channel.read(this.byteBuffer);
        // 没有数据
        if (count == -1) {
            key.channel().close();
            key.channel();
            return;
        }
        // 缓存区不为空则读取数据，读取之前需要调用复位方法（按postion 和limit进行复位）
        this.byteBuffer.flip();

        // 创建跟缓冲区相当长度的字节数组
        byte[] bytes = new byte[this.byteBuffer.remaining()];

        // 打印结果
        String body = new String(bytes).trim();
        System.out.println("server : " + body);
    }

    /**
     * 处理阻塞通道方法
     *
     * @param key
     * @throws Exception
     */
    private void accept(SelectionKey key) throws Exception {
        // 获取服务通道
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        // 执行阻塞方法
        SocketChannel sc = channel.accept();
        // 设置阻塞模式（非阻塞）
        sc.configureBlocking(false);
        // 注册到selector上，并且表示为可读
        sc.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        new Thread(new Server(8080)).start();
    }

}
