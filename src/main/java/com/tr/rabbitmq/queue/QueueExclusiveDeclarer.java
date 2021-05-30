package com.tr.rabbitmq.queue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.RabbitMQUtil;

import java.util.concurrent.TimeUnit;

/**
 * 排他队列声明-QueueExclusiveDeclarer-仅允许声明队列的线程使用
 * 一旦线程或连接关闭，则队列被删除
 * @author tang rong
 */
public class QueueExclusiveDeclarer {
    public final static String EXCHANGE_NAME = "dtest-ex-0520-a"; // 交换机
    public final static String EXCLUSIVE_QUEUE_NAME = "work-queue-0526-exclusive";  // 持久队列

    public static void main(String[] args) throws Exception {
        /* 获取连接*/
        Connection conn = RabbitMQUtil.getConn();
        /*创建信道*/
        Channel channel = conn.createChannel();

        /*创建交换机*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true); // 声明交换机也是持久化的

        /* 创建队列-队列名称， 是否持久化队列，是否独占本次连接，是否在不使用的时候自动删除队列， 队列其他参数； */
        channel.queueDeclare(QueueExclusiveDeclarer.EXCLUSIVE_QUEUE_NAME, true, true, false, null);
        /*绑定*/
        channel.queueBind(EXCLUSIVE_QUEUE_NAME, EXCHANGE_NAME, "exclusive"); // 持久化队列绑定到持久化路由键

        System.out.println("睡眠中......");
        TimeUnit.SECONDS.sleep(30);


        /* 关闭信道和连接 */
        channel.close();
        conn.close();
        System.out.println("声明交换机成功 " + EXCHANGE_NAME);
        System.out.println("声明队列成功 " + EXCLUSIVE_QUEUE_NAME);
    }
}
