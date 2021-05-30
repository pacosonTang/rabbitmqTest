package com.tr.rabbitmq.work.dispatch.fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * 消息发送者-持久化队列
 */
public class WorkFairSend {
    public final static String QUEUE_NAME = WorkFairDeclarer.QUEUE_NAME;

    public static void main(String[] argv) throws Exception {
        try (Connection connection = RabbitMQUtil.getConn();
             Channel channel = connection.createChannel()) {
            for (int i = 1; i <= 10; i++) {
                String message = getdot(i) + ", index【" + i + "】";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "' from queue" + QUEUE_NAME);
            }
        }
    }

    public static String getdot(int num) {
        String raw = "";
        for (int i = 0; i < num; i++) {
            raw +=".";
        }
        return raw;
    }
}