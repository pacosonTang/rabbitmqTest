package com.tr.rabbitmq.work.dispatch.fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * rabbitmq 消费者
 */
public class WorkFairRecv2 {
    private final static String QUEUE_NAME = WorkFairDeclarer.QUEUE_NAME;

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtil.getConn();
        Channel channel = connection.createChannel();
        System.out.println(" recv2 Waiting for messages. To exit press CTRL+C");

        // 在处理并确认上一条消息之前，不要将新消息发送给本消费者。 而是将其分派给不忙的其他消费者。
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DeliverCallback deliverCallback = (consumerTag1, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" recv2 Received '" + message + "'" + "consumerTag = " + consumerTag1);
            try {
                doWork(message);
            } finally {
                System.out.println(" recv2 Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); // 手动确认
            } 
        };
        // 1-队列，2-自动确认，3-消息接收成功回调，4-消费者被取消时回调（如队列被删除，则消费者被取消）
        // 公平分发必须是手动确认
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag22 -> { });
    }
    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000); // 睡眠1秒
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
