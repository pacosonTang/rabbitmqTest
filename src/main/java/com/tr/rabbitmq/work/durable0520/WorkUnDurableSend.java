package com.tr.rabbitmq.work.durable0520;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.MyDateUtil;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * 发送[非]持久化消息
 * @author tang rong
 */
public class WorkUnDurableSend {
    private final static String QUEUE_NAME = WorkDurableDeclarer.UND_QUEUE_NAME; // 队列名称

    public static void main(String[] args) throws Exception {
        try (Connection connection = RabbitMQUtil.getConn();
             Channel channel = connection.createChannel()) {

            for (int i = 0; i < 10; i++) {
                String message = "Hello World! iam durable" + MyDateUtil.getNow() + ", index【" + i + "】";
                // 发送持久化消息
                channel.basicPublish(
                        WorkDurableDeclarer.EXCHANGE_NAME, WorkDurableDeclarer.ROUTE_KEY_UNDURABLE, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "' from queue" + QUEUE_NAME);
            }
        }
    }
}
