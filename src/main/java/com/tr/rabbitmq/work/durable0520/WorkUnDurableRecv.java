package com.tr.rabbitmq.work.durable0520;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * 非持久化消息-消费者
 * @author tang rong
 */
public class WorkUnDurableRecv {
    public final static String QUEUE_NAME = WorkDurableDeclarer.UND_QUEUE_NAME;

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtil.getConn();
        Channel channel = connection.createChannel();
        System.out.println(" WorkUnDurableRecv [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" WorkUnDurableRecv [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });


    }
}
