package org.bond.rocketmq.consumer;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

public class StringListener implements MessageListenerConcurrently {

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		for (MessageExt message : msgs) {
			try {
				System.out.println(message.getMsgId() + ";"
						+ message.toString());
			} catch (Exception ex) {
				// 再次放进队列
				ex.printStackTrace();
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		}

		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
