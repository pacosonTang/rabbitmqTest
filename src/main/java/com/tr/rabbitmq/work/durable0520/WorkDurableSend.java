package com.tr.rabbitmq.work.durable0520;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.tr.rabbitmq.util.MyDateUtil;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * 发送持久化消息
 * @author tang rong
 */
public class WorkDurableSend {
    private final static String QUEUE_NAME = WorkDurableDeclarer.D_QUEUE_NAME; // 队列名称

    public static void main(String[] args) throws Exception {
        try (Connection connection = RabbitMQUtil.getConn();
             Channel channel = connection.createChannel()) {

            for (int i = 0; i < 10; i++) {
                String message = "Hello World! msg is durable and queue is durable, " + MyDateUtil.getNow() + ", index【" + i + "】";
                // 发送持久化消息
                channel.basicPublish(
                        WorkDurableDeclarer.EXCHANGE_NAME, WorkDurableDeclarer.ROUTE_KEY_DURABLE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "' from queue" + QUEUE_NAME);
            }
        }
    }
}
