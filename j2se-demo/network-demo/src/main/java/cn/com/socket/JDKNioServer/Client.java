/**
 * @Title: Client
 * @Package cn.com.socket.JDKNioServer
 * @Description: 客户端服务
 * @author hu.rl
 * @date 2017/6/26 21:30
 * @version V1.0
 */
package cn.com.socket.JDKNioServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author hu.rl
 * @ClassName: Client
 * @Description: 客户端服务
 * @date 2017/6/26 21:30
 */
public class Client {

    public static void main(String[] args) {

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);

        // 创建客户端通道
        SocketChannel channel = null;
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            // 打开通道
            channel = SocketChannel.open();
            // 链接通道
            channel.connect(address);
            while (true) {
                // 创建字符数组
                byte[] bytes = new byte[1024];
                // 控制台输入
                System.in.read(bytes);
                // 将数据放入缓冲区
                buffer.put(bytes);
                // 将缓冲区复位
                buffer.flip();
                // 写出数据
                channel.write(buffer);
                // 清空缓存
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
