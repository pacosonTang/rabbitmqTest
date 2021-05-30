package com.tr.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.MyDateUtil;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection conn = RabbitMQUtil.getConn();
        // 创建频道
        Channel channel = conn.createChannel();
        // 发送消息
        for (int i = 1; i <= 10; i++) {
            String message = "序号【" + i + "】 hello rabbitmq, 现在是 " + MyDateUtil.getNow();
            channel.basicPublish("", SimpleDeclarer.QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发送消息" + message);
        }
        // 关闭通道和连接
        channel.close();
        conn.close();
    }
}
