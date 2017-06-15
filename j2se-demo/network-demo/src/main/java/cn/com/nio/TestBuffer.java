/**
 * @Title: TestBuffer
 * @Package cn.com.nio
 * @Description: 缓冲区学习
 * @author hu.rl
 * @date 2017/6/11 12:11
 * @version V1.0
 */
package cn.com.nio;

import java.nio.IntBuffer;

/**
 * @author hu.rl
 * @ClassName: TestBuffer
 * @Description: NIO学习--缓冲区
 * @date 2017/6/11 12:11
 */
public class TestBuffer {

    // 1、基本操作
    public static void main(String[] args) {
        // 缓冲区初始化
        IntBuffer buffer = IntBuffer.allocate(10);
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        // 更新当前最大上限。put时必须更新，不然上限就是10在循环时不能拿到实时的长度
        buffer.flip();
        // 1、打印出缓冲区的情况 pos ：位置，limit：上限，capacity：容量
        // 2、put一个参数pos则加1
        System.out.println("打印buffer区当前状态:" + buffer);
        System.out.println("容量为:" + buffer.capacity());
        System.out.println("上限为:" + buffer.limit());

        // 获取下标的元素
        System.out.println("获取下标为0的元素:" + buffer.get(0));
        // 修改指定下标的元素
        System.out.println("修改下标为2的元素:" + buffer.put(2, 5));
        System.out.println("修改完之后获取2：" + buffer.get(2));

        // 循环
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println("循环输出值" + i + ":" + buffer.get());
        }

        // ===================使用数组缓冲
        int[] arr = new int[]{1, 2, 5};
        // 1、wrap方法会包裹一个数组，这种情况下缓冲区不会初始化缓存对象的长度，因为会被包裹数组覆盖
        // 2、wrap方法修改在缓冲区的数组的时候数组本身也会跟着改变
        IntBuffer arrBuffer1 = IntBuffer.wrap(arr);
        System.out.println("输出存放数组的buffer：" + arrBuffer1);
        IntBuffer arrBuffer2 = IntBuffer.wrap(arr, 0, 2);
        System.out.println("输出替换数组元素之后的buffer：" + arrBuffer2);

    }
}
