package com.tr.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * 发布订阅模式-消费者
 */
public class PubsubConsumer {

    public static void main(String[] args) throws Exception {
        Connection conn = RabbitMQUtil.getConn();
         Channel channel = conn.createChannel();
        System.out.println("PubsubConsumer 等待接收消息...... ");
        System.out.println("终于提交了。。。");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("接收到的消息=" + message);
        };
        // 设置消费 QUEUE_NAME_BLACK 队列里的消息
        channel.basicConsume(PubsubDeclarer.QUEUE_NAME_Q1, true, callback, consumerTag -> {});
    }
}
