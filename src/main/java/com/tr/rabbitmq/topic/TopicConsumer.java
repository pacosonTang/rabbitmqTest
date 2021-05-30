package com.tr.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

public class TopicConsumer {
    private static final String EXCHANGE_NAME = TopicDeclarer.EXCHANGE_NAME;

    public static void main(String[] args) throws Exception {
        Connection conn = RabbitMQUtil.getConn();
        Channel channel = conn.createChannel();
        System.out.println("TopicConsumer 等待接收消息...... ");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("接收到的消息=" + message);
        };
        // 设置消费 QUEUE_NAME_BLACK 队列里的消息
        channel.basicConsume(TopicDeclarer.QUEUE_NAME_Q1, true, callback, consumerTag -> {});
    }
}
