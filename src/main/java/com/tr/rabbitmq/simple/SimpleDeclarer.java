package com.tr.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * 简单声明
 * @author tang rong
 */
public class SimpleDeclarer {
    /**
     * 队列名称
     */
    public final static String QUEUE_NAME = "simple_queue_0425";

    public static void main(String[] args) throws Exception {
        /* 获取连接*/
        Connection conn = RabbitMQUtil.getConn();
        // 创建信道
        Channel channel = conn.createChannel();
        /**
         * 创建队列
         * 队列名称， 是否持久化队列，是否独占本次连接，是否在不使用的时候自动删除队列， 队列其他参数；
         */
        channel.queueDeclare(SimpleDeclarer.QUEUE_NAME, false, false, false, null);
        // 若队列不绑定任何交换机，则绑定到默认交换机-AMQP default
//		The default exchange is implicitly bound to every queue, with a routing key equal to the queue name. It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.

        /* 关闭信道和连接 */
        channel.close();
        conn.close();
        System.out.println("声明队列成功 " + QUEUE_NAME);
    }
}
