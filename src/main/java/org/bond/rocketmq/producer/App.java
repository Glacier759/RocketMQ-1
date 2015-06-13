package org.bond.rocketmq.producer;

import org.bond.rocketmq.common.Person;
import org.bond.rocketmq.common.SerializationUtils;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Hello world!
 *
 */
public class App {

	private static void producer1() {
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		producer.setNamesrvAddr("127.0.0.1:9876");
		try {
			producer.start();

			System.out.println("--------消息01---------");
			Message msg = new Message("PushTopic", "push", "1",
					"Just for test-01.".getBytes());

			SendResult result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:"
					+ result.getSendStatus());

			System.out.println("--------消息02---------");
			msg = new Message("PushTopic", "push", "2",
					"Just for test-02.".getBytes());

			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:"
					+ result.getSendStatus());

			System.out.println("--------消息03---------");
			msg = new Message("PullTopic", "pull", "3",
					"Just for test-03.".getBytes());

			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:"
					+ result.getSendStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}

	private static void producer2() {
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		producer.setNamesrvAddr("127.0.0.1:9876");
		try {
			producer.start();

			System.out.println("--------person01---------");
			Person person = new Person("张三", 22, "上海");
			Message msg = new Message("PushTopic", "Person", "1",
					SerializationUtils.H2Serialize(person));

			SendResult result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:"
					+ result.getSendStatus());

			System.out.println("--------person02---------");
			person = new Person("李四", 32, "北京");
			msg = new Message("PushTopic", "Person", "2",
					SerializationUtils.H2Serialize(person));

			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:"
					+ result.getSendStatus());

			System.out.println("--------person03---------");
			person = new Person("王五", 42, "天津");
			msg = new Message("PushTopic", "Person", "3",
					SerializationUtils.H2Serialize(person));

			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:"
					+ result.getSendStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}

	public static void main(String[] args) {
		producer1();
		producer2();
	}

}
