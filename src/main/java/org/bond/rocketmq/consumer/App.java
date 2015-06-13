package org.bond.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * Hello world!
 *
 */
public class App {
	private static void consumer1() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
				"PushConsumer-String");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		consumer.setInstanceName("consumer-String");
		try {
			// 订阅PushTopic下Tag为push的消息
			// 与生产者的topic与 tags对应
			consumer.subscribe("PushTopic", "push");
			// 程序第一次启动从消息队列头取数据
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			// consumer.registerMessageListener(
			// // 匿名类,自定义的Listener需要实现该接口
			// new MessageListenerConcurrently() {
			// public ConsumeConcurrentlyStatus consumeMessage(
			// List<MessageExt> list,
			// ConsumeConcurrentlyContext Context) {
			// Message msg = list.get(0);
			// System.out.println(msg.toString());
			// return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			// }
			// });

			consumer.registerMessageListener(new StringListener());
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void consumer2() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
				"PushConsumer-Person");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		consumer.setInstanceName("consumer-Person");
		try {
			// 订阅PushTopic下Tag为push的消息
			// 与生产者的topic与 tags对应
			consumer.subscribe("PushTopic", "Person");
			// 程序第一次启动从消息队列头取数据
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

			consumer.registerMessageListener(new PersonListener());
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("消费者订阅");
		consumer1();
		consumer2();
	}
}
