package com.tr.rabbitmq.simple;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.io.IOException;

/**
 * rabbitmq 消费者
 */
public class SimpleConsumer {

    public static void main(String[] args) throws Exception {
        Connection conn = RabbitMQUtil.getConn(); // 创建连接
        Channel channel = conn.createChannel(); // 创建频道
        System.out.println("customer wait to receive message...... ");
        // 告诉服务器，我们需要哪个频道的角色，如果频道中有消息，就会执行回调函数 handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * @param consumerTag 消费者标签，在 channel.basicConsume 可以指定
             * @param envelope 消息包内容，包括消息id，消息routingkey，交换机，
             * 消息和重转标记（收到消息失败后是否需要重新发送）
             * @param properties 基本属性
             * @param body 消息字节数组
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key=" + envelope.getRoutingKey());
                System.out.println("交换机=" + envelope.getExchange());
                System.out.println("消息id=" + envelope.getDeliveryTag());
                String message = new String(body, "UTF-8");
                System.out.println(String.format("消费者收到的消息【%s】", message));
                System.out.println("=============================================");
            }
        };
        // 自动恢复队列应答 -- rabbitmq中的消息确认机制
        String consumerTag = channel.basicConsume(SimpleDeclarer.QUEUE_NAME, true, consumer);
        System.out.println("consumerTag = " + consumerTag);
    }
}
