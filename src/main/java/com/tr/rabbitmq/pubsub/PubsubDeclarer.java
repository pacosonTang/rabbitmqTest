package com.tr.rabbitmq.pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * 声明资源
 */
public class PubsubDeclarer { 
    public final static String EXCHANGE_NAME = "pubsub-ex-log-20210527-a";
    public final static String QUEUE_NAME_Q1 = "pubsub-queue-Q1";
    public final static String QUEUE_NAME_Q2 = "pubsub-queue-Q2";
    public final static String ROUTE_KEY_ORANGE = "orange";
    public final static String ROUTE_KEY_BLACK = "black";
    public final static String ROUTE_KEY_GREEN = "green";

    public static void main(String[] args) throws Exception {
        try (Connection conn = RabbitMQUtil.getConn();
             Channel channel = conn.createChannel()) {
            // 声明direct-交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 声明队列，参数，1-队列名称，2-持久化，3-独占，4-自动删除，5-属性
            channel.queueDeclare(QUEUE_NAME_Q1, false, false, false, null);
            channel.queueDeclare(QUEUE_NAME_Q2, false, false, false, null);
            // 绑定 
            channel.queueBind(QUEUE_NAME_Q1, EXCHANGE_NAME, ROUTE_KEY_ORANGE);
            channel.queueBind(QUEUE_NAME_Q2, EXCHANGE_NAME, ROUTE_KEY_BLACK);
            channel.queueBind(QUEUE_NAME_Q2, EXCHANGE_NAME, ROUTE_KEY_GREEN);
            System.out.println("声明交换机成功， 交换机名称=" + EXCHANGE_NAME + ", 队列名称=" + QUEUE_NAME_Q1 +", 路由键=" + ROUTE_KEY_BLACK);
        }
    }
}
