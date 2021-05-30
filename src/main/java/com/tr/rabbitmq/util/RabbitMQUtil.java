package com.tr.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Map;

/**
 * rabbitmq 连接工具类
 * @author tang rong
 */
public class RabbitMQUtil {
    /**
     * 获取rabbitmq连接
     * @return
     * @throws Exception
     * @throws
     */
    public static Connection getConn() throws Exception {
        // 创建连接工厂
        ConnectionFactory connFactory = new ConnectionFactory();
        // 主机地址，默认为 localhost
        connFactory.setHost("192.168.163.201");
        connFactory.setPort(5672);
        // 设置虚拟主机
        connFactory.setVirtualHost("/");
        // 设置账号密码
        connFactory.setUsername("guest");
        connFactory.setPassword("guest");
        // 创建连接
        return connFactory.newConnection();
    }

    /**
     * 声明队列
     * @param queue 队列
     * @param durable 是否持久化
     * @param exclusive 是否独占
     * @param autoDelete 是否自动删除
     * @param arguments 属性
     * @throws Exception
     */
    public final static void queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
                                          Map<String, Object> arguments) throws Exception {
        try (Connection conn = RabbitMQUtil.getConn();
             Channel channel = conn.createChannel()) {
            channel.queueDeclare(queue, durable, false, false, null);
            System.out.println("声明队列成功 " + queue);
        }
    }

    /**
     * 删除队列
     * @param queue 队列
     * @throws Exception
     */
    public static void delete(String queue) throws Exception {
        try (Connection conn = RabbitMQUtil.getConn();
             Channel channel = conn.createChannel()) {
            channel.queueDelete(queue);
            System.out.println("删除队列成功，队列=" + queue);
        }
    }
}
