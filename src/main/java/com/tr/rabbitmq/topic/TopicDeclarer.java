package com.tr.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * 主题交换机声明
 */
public class TopicDeclarer {
    public final static String EXCHANGE_NAME = "topic-ex-log-20210527-a";
    public final static String QUEUE_NAME_Q1 = "topic-queue-Q1";
    public final static String QUEUE_NAME_Q2 = "topic-queue-Q2";
    public final static String ROUTE_KEY_WILDCARD_COLOR = "*.orange.*";
    public final static String ROUTE_KEY_WILDCARD_ANIMAL = "*.*.rabbit";
    public final static String ROUTE_KEY_WILDCARD_SPEED = "lazy.#";

    public static void main(String[] args) throws Exception {
        try (Connection conn = RabbitMQUtil.getConn();
             Channel channel = conn.createChannel()) {
            // 声明direct-交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            // 声明队列，参数，1-队列名称，2-持久化，3-独占，4-自动删除，5-属性
            channel.queueDeclare(QUEUE_NAME_Q1, false, false, false, null);
            channel.queueDeclare(QUEUE_NAME_Q2, false, false, false, null);
            // 绑定
            channel.queueBind(QUEUE_NAME_Q1, EXCHANGE_NAME, ROUTE_KEY_WILDCARD_COLOR);
            channel.queueBind(QUEUE_NAME_Q2, EXCHANGE_NAME, ROUTE_KEY_WILDCARD_ANIMAL);
            channel.queueBind(QUEUE_NAME_Q2, EXCHANGE_NAME, ROUTE_KEY_WILDCARD_SPEED);
            System.out.println("声明交换机成功， 交换机名称=" + EXCHANGE_NAME + ", 队列名称=" + QUEUE_NAME_Q1 +", 路由键=" + ROUTE_KEY_WILDCARD_COLOR);
            System.out.println("声明交换机成功， 交换机名称=" + EXCHANGE_NAME + ", 队列名称=" + QUEUE_NAME_Q2 +", 路由键=" + ROUTE_KEY_WILDCARD_ANIMAL);
            System.out.println("声明交换机成功， 交换机名称=" + EXCHANGE_NAME + ", 队列名称=" + QUEUE_NAME_Q2 +", 路由键=" + ROUTE_KEY_WILDCARD_SPEED);
        }
    }
}