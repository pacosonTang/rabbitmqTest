package com.tr.rabbitmq.work.dispatch.fair;

import com.tr.rabbitmq.util.RabbitMQUtil;

/**
 * 公平分发（谁闲发给谁）
 * 谁有能力处理更多的任务，那么就交给谁处理，防止消息的挤压
 * @author tang rong
 */
public class WorkFairDeclarer {
    public final static String QUEUE_NAME = "queue-workfair-0527-d";

    public static void main(String[] args) throws Exception {
        RabbitMQUtil.queueDeclare(QUEUE_NAME, true, false, false, null );
    }
}
