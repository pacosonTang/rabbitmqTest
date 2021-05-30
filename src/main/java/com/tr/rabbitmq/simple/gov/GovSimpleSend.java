package com.tr.rabbitmq.simple.gov;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.MyDateUtil;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * 消息发送者
 */
public class GovSimpleSend {
    private final static String QUEUE_NAME = GovSimpleDeclarer.QUEUE_NAME;

    public static void main(String[] argv) throws Exception {
        try (Connection connection = RabbitMQUtil.getConn();
             Channel channel = connection.createChannel()) {
            for (int i = 0; i < 10; i++) {
                String message = "【" + i +  "】Hello World!" + MyDateUtil.getNow() ;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}