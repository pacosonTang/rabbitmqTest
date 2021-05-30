package com.tr.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.MyDateUtil;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * 消息发送者-工作队列
 */
public class WorkSend {
    public final static String QUEUE_NAME = "queue-0426d-undurable";
    //
    public static void main(String[] argv) throws Exception {
        try (Connection connection = RabbitMQUtil.getConn();
             Channel channel = connection.createChannel()) {
            boolean durable = false;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            for (int i = 0; i < 10; i++) {
                String message = "Hello World!" + MyDateUtil.getNow() + "index【" + i + "】";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "' from queue" + QUEUE_NAME);
            }
        }
    }
}