package com.tr.rabbitmq.simple.gov;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * 资源声明
 */
public class GovSimpleDeclarer {
    public final static String QUEUE_NAME = "hello0522";

    public static void main(String[] argv) throws Exception {
        try (Connection connection = RabbitMQUtil.getConn();
             Channel channel = connection.createChannel()) {
            // 1-队列名，2-是否持久化，3-是否独占，4-是否自动删除，5-队列属性
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.print("声明队列成功，队列名称【" + QUEUE_NAME + "】");
        }
    }
}