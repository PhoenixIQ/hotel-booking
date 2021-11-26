package com.iquantex.phoenix.hotel.config;

import com.iquantex.phoenix.core.connect.api.CollectMetaData;
import com.iquantex.phoenix.core.connect.api.CollectResult;
import com.iquantex.phoenix.core.connect.api.Records;
import com.iquantex.phoenix.core.connect.api.SourceCollect;
import com.iquantex.phoenix.core.connect.api.Subscribe;
import com.iquantex.phoenix.core.connect.kafka.KafkaSubscribe;
import com.iquantex.phoenix.core.message.Message;
import com.iquantex.phoenix.eventpublish.core.EventDeserializer;
import com.iquantex.phoenix.eventpublish.deserializer.DefaultMessageDeserializer;
import com.iquantex.phoenix.hotel.protocol.HotelCancelEvent;
import com.iquantex.phoenix.hotel.protocol.HotelCreateEvent;
import com.iquantex.phoenix.hotel.protocol.OrderCancelCmd;
import com.iquantex.phoenix.hotel.protocol.OrderCreateCmd;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author quail
 */
@Configuration
@ConditionalOnProperty(value = "quantex.phoenix.event-publish.event-task.enabled", havingValue = "true")
public class EventPublishTopicSubscribeConfig {

	@Value("${spring.application.name}")
	private String appName;

	@Value("${quantex.phoenix.server.mq.address}")
	private String mqAddress;

	@Value("${quantex.phoenix.event-publish.event-task.topic}")
	private String subscribeTopic;

	private EventDeserializer<byte[], Message> deserializer = new DefaultMessageDeserializer();

	@Bean("eventPublishTopicSubscribe")
	public Subscribe customSubscribe() {
		Properties properties = new Properties();
		properties.putIfAbsent(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		return new KafkaSubscribe(mqAddress, subscribeTopic, appName, properties, new SourceCollect() {
			@Override
			public List<CollectResult> collect(Records records, CollectMetaData collectMetaData) {
				List<CollectResult> collectResults = new ArrayList<>();
				Message message = deserializer.deserialize(records.getValue());
				if (message.getPayload() instanceof HotelCreateEvent) {
					// 反序列化上游事件
					HotelCreateEvent hotelCreateEvent = (HotelCreateEvent) message.getPayload();
					// 根据上游事件要素构造出聚合根的cmd
					OrderCreateCmd orderCreateCmd = OrderCreateCmd.builder().hotelCode(hotelCreateEvent.getHotelCode())
							.subNumber(hotelCreateEvent.getSubNumber()).roomType(hotelCreateEvent.getRoomType())
							.build();
					collectResults.add(new CollectResult(orderCreateCmd, true));
				}
				else if (message.getPayload() instanceof HotelCancelEvent) {
					HotelCancelEvent hotelCancelEvent = (HotelCancelEvent) message.getPayload();
					OrderCancelCmd orderCancelCmd = OrderCancelCmd.builder().hotelCode(hotelCancelEvent.getHotelCode())
							.subNumber(hotelCancelEvent.getSubNumber()).build();
					collectResults.add(new CollectResult(orderCancelCmd, true));
				}
				return collectResults;
			}
		});
	}

}
