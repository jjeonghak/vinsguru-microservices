package com.vinsguru.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RPatternTopicReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.listener.PatternMessageListener;
import org.redisson.client.codec.StringCodec;

public class Lec12PubSubTest extends BaseTest {

	@Test
	public void subscriber1() {

		RTopicReactive topic = this.client.getTopic("channel-1", StringCodec.INSTANCE);
		topic.getMessages(String.class)
				.doOnError(System.out::println)
				.doOnNext(System.out::println)
				.subscribe();

		sleep(600000);

	}

	@Test
	public void subscriber2() {

		RPatternTopicReactive patternTopic = this.client.getPatternTopic("channel-*", StringCodec.INSTANCE);
		patternTopic.addListener(String.class, new PatternMessageListener<String>() {
						@Override
						public void onMessage(CharSequence pattern, CharSequence topic, String msg) {
							System.out.println(pattern + " : " + topic + " : " + msg);
						}
				})
				.subscribe();

		sleep(600000);

	}

}
