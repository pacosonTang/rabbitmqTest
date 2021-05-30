package com.tr.rabbitmq.work.durable0520;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * 工作队列声明
 * @author tang rong
 */
public class WorkDurableDeclarer {
    public final static String EXCHANGE_NAME = "dtest-ex-0520-a"; // 交换机
    public final static String D_QUEUE_NAME = "work-queue-0520-a-durable";  // 持久队列
    public final static String UND_QUEUE_NAME = "work-queue-0520-a-undurable"; // 非持久队列
    public final static String ROUTE_KEY_DURABLE = "durable0520"; // 路由键-持久化
    public final static String ROUTE_KEY_UNDURABLE = "undurable0520";  // 路由键-非持久化

    public static void main(String[] args) throws Exception {
        /* 获取连接*/
        Connection conn = RabbitMQUtil.getConn();
        /*创建信道*/
        Channel channel = conn.createChannel();

        /*创建交换机*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true); // 声明交换机也是持久化的
        channel.exchangeDeclare(EXCHANGE_NAME+"-undurable", BuiltinExchangeType.DIRECT, false); // 声明交换机也是非持久化的

        /* 创建队列-队列名称， 是否持久化队列，是否独占本次连接，是否在不使用的时候自动删除队列， 队列其他参数； */
        channel.queueDeclare(WorkDurableDeclarer.D_QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(WorkDurableDeclarer.UND_QUEUE_NAME, false, false, false, null);
        /*绑定*/
        channel.queueBind(D_QUEUE_NAME, EXCHANGE_NAME, ROUTE_KEY_DURABLE); // 持久化队列绑定到持久化路由键
        channel.queueBind(UND_QUEUE_NAME, EXCHANGE_NAME, ROUTE_KEY_UNDURABLE);  // 非持久化队列绑定到非持久化路由键

        /* 关闭信道和连接 */
        channel.close();
        conn.close();
        System.out.println("声明交换机成功 " + EXCHANGE_NAME);
        System.out.println("声明队列成功 " + D_QUEUE_NAME);
        System.out.println("声明队列成功 " + UND_QUEUE_NAME);
    }
}
