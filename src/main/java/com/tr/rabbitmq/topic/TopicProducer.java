package com.tr.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.MyDateUtil;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

public class TopicProducer {
    private final static String[] ROUTE_KEY_ARR = new String[]
            {
                     "lazy.orange.rabbit"
                    , "lazy.blue.rabbit"
                    , "fast.red.lion"
                    , "fast.orange"
                    , "fast.orange.1"
            };

    public static void main(String[] args) throws Exception {
        try (Connection conn = RabbitMQUtil.getConn();
             Channel channel = conn.createChannel()) {
            for (int i = 1; i <= 10; i++) {
                String routeKey = ROUTE_KEY_ARR[i%ROUTE_KEY_ARR.length];
                String message = "【" + i + "】hello world, 发布订阅模式测试，时间戳=" + MyDateUtil.getNow() + ", 路由键=" + routeKey;
                channel.basicPublish(
                        TopicDeclarer.EXCHANGE_NAME, routeKey, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("发送消息到交换机成功，消息 =" + message);
            }
        }
    }
}