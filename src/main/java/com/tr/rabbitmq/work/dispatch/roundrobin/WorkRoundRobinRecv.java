package com.tr.rabbitmq.work.dispatch.roundrobin;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tr.rabbitmq.util.RabbitMQUtil;

public class WorkRoundRobinRecv {
    private final static String QUEUE_NAME = WorkRoundRobinDeclarer.QUEUE_NAME;

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtil.getConn();
        Channel channel = connection.createChannel();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 在处理并确认上一条消息之前，不要将新消息发送给本消费者。 而是将其分派给不忙的其他消费者。
//        int prefetchCount = 1;
//        channel.basicQos(prefetchCount);

        DeliverCallback deliverCallback = (consumerTag1, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" recv Received '" + message + "'" + "consumerTag = " + consumerTag1);
            try {
                doWork(message);
            } finally {
                System.out.println(" recv Done");
//                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); // 手动确认
            }
        };
        // 1-队列，2-自动确认，3-消息接收成功回调，4-消费者被取消时回调（如队列被删除，则消费者被取消）
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag22 -> { }); // 自动确认
    }
    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(100); // 睡眠100毫秒
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}