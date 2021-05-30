package com.tr.rabbitmq.simple.gov;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tr.rabbitmq.util.RabbitMQUtil;

public class GovSimpleRecv {
    private final static String QUEUE_NAME = GovSimpleDeclarer.QUEUE_NAME;

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQUtil.getConn();
        Channel channel = connection.createChannel();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag1, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'" + "consumerTag = " + consumerTag1);
        };
        // 1-队列，2-自动确认，3-消息接收成功回调，4-消费者被取消时回调（如队列被删除，则消费者被取消）
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag22 -> { });
    }
}