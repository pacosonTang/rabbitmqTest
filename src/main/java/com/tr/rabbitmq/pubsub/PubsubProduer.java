package com.tr.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.MyDateUtil;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * 发布订阅模式-发送
 */
public class PubsubProduer {
    private final static String[] ROUTE_KEY_ARR = new String[]{PubsubDeclarer.ROUTE_KEY_BLACK, PubsubDeclarer.ROUTE_KEY_GREEN, PubsubDeclarer.ROUTE_KEY_ORANGE};

    public static void main(String[] args) throws Exception {
        try (Connection conn = RabbitMQUtil.getConn();
             Channel channel = conn.createChannel()) {
            for (int i = 1; i <= 10; i++) {
                String routeKey = ROUTE_KEY_ARR[i%3];
                String message = "【" + i + "】hello world, 发布订阅模式测试，时间戳=" + MyDateUtil.getNow() + ", color=" + routeKey;
                channel.basicPublish(
                        PubsubDeclarer.EXCHANGE_NAME, routeKey, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("发送消息到交换机成功，消息=" + message);
            }
        }
    }
}
