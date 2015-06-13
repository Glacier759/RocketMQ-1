package org.bond.rocketmq.producer;

import java.util.List;

import org.bond.rocketmq.common.JsonUtil;
import org.bond.rocketmq.common.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class ProducerHelper {

	private static final Logger log = LoggerFactory
			.getLogger(ProducerHelper.class);

	private DefaultMQProducer producer = null;

	/**
	 * 随机消息
	 * 
	 * @param topic
	 * @param tags
	 * @param body
	 */
	// public void send(String topic, String tags, Object body) {
	// send(topic, tags, body, "test");//暂时都是顺序，都已test字符串来分割对列
	// }

	/**
	 * 顺序消息，按照参数queueId分配队列
	 * 
	 * @param topic
	 * @param tags
	 * @param body
	 * @param queueId
	 */
	public SendResult send(String topic, String tags, Object body, String keys,
			String queueId) throws Exception {
		Message msg = new Message(topic, tags,
				SerializationUtils.H2Serialize(body));
		SendResult result = null;
		if (null != queueId && !"".equals(queueId)) {
			result = producer.send(msg, new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg,
						Object arg) {
					Integer id = Math.abs(arg.hashCode());
					int index = id % mqs.size();
					return mqs.get(index);
				}
			}, queueId);
		} else {
			result = sendResult(msg);

			if (result.getSendStatus() == SendStatus.SEND_OK) {
				log.error("id:" + result.getMsgId() + " result:"
						+ result.getSendStatus() + JsonUtil.serialize(body));

			}
		}

		return result;
	}

	/**
	 * 延迟队列
	 * 
	 * @param topic
	 * @param tags
	 * @param body
	 * @param delayLevel
	 *            1：1 秒 2：5 秒 3：10秒 4：30秒 5：60秒 6：2分钟 7：3分钟 8：4分钟 9：5分钟 10：6分钟
	 *            11：7分钟 12：8分钟 13：9分钟 14：10分钟 15：20分钟 16：30分钟
	 */
	public SendResult sendByDelay(String topic, String tags, Object body,
			int delayLevel) {

		try {
			Message msg = new Message(topic, tags,
					SerializationUtils.H2Serialize(body));
			msg.setDelayTimeLevel(delayLevel);
			SendResult result = sendResult(msg);
			if (result.getSendStatus() == SendStatus.SEND_OK) {
				log.info("id:" + result.getMsgId() + " result:"
						+ result.getSendStatus() + JsonUtil.serialize(body));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ProducerHelper send is error", e);
			return null;
		}
	}

	private SendResult sendResult(Message msg) throws InterruptedException,
			RemotingException, MQClientException, MQBrokerException {
		return producer.send(msg);
	}
}