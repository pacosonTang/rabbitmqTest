package com.tr.rabbitmq.simple.gov;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tr.rabbitmq.util.RabbitMQUtil;

public class GovSimpleRecv2 {
    private final static String QUEUE_NAME = GovSimpleDeclarer.QUEUE_NAME;

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQUtil.getConn();
        Channel channel = connection.createChannel();
        System.out.println(" Recv2 [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" Recv2 [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}