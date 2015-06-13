package org.bond.rocketmq.consumer;

import java.util.List;

import org.bond.rocketmq.common.Person;
import org.bond.rocketmq.common.SerializationUtils;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

public class PersonListener implements MessageListenerConcurrently {

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		for (MessageExt message : msgs) {
			try {
				Person person = (Person) SerializationUtils.H2Deserialize(
						message.getBody(), Person.class);
				if (person != null) {
					System.out.println(message.getMsgId() + ":"
							+ person.toString());
				} else {
					System.out.println(message.getMsgId() + ":无数据");
				}
			} catch (Exception ex) {
				// 再次放进队列
				ex.printStackTrace();
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		}

		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
