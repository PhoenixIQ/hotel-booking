package com.iquantex.phoenix.hotel.handle;

import com.iquantex.phoenix.core.message.Message;
import com.iquantex.phoenix.core.message.protobuf.Phoenix;
import com.iquantex.phoenix.eventpublish.core.CommittableEventBatchWrapper;
import com.iquantex.phoenix.eventpublish.core.EventDeserializer;
import com.iquantex.phoenix.eventpublish.core.EventHandler;
import com.iquantex.phoenix.eventpublish.deserializer.DefaultMessageDeserializer;
import com.iquantex.phoenix.hotel.enumType.RoomType;
import com.iquantex.phoenix.hotel.model.BookingStore;
import com.iquantex.phoenix.hotel.protocol.HotelCancelEvent;
import com.iquantex.phoenix.hotel.protocol.HotelCreateEvent;
import com.iquantex.phoenix.hotel.repository.BookingsStoreRepository;
import com.iquantex.phoenix.server.eventstore.EventStoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author quail
 */
@Component
public class PopPublishHandler implements EventHandler<Phoenix.Message, Phoenix.Message> {

	@Autowired
	private BookingsStoreRepository repository;

	/** 使用提供的默认反序列化器，反序列化MQ中的字节数组，得到以Message封装的领域事件 */
	private EventDeserializer<byte[], Message> deserializer = new DefaultMessageDeserializer();

	@Override
	public String getInfo() {
		return "PopPublish";
	}

	@Override
	public CommittableEventBatchWrapper handleBatch(CommittableEventBatchWrapper batchWrapper) {
		List<EventStoreRecord<Phoenix.Message>> events = batchWrapper.getEvents();
		Iterator<EventStoreRecord<Phoenix.Message>> iterator = events.iterator();
		while (iterator.hasNext()) {
			Message message = deserializer.deserialize(iterator.next().getContent().toByteArray());
			if (message.getPayload() instanceof HotelCreateEvent) {
				RoomType roomType = ((HotelCreateEvent) message.getPayload()).getRoomType();
				try {
					BookingStore bookingStore = repository.findById(roomType.getCode()).get();
					repository.save(BookingStore.builder().roomType(roomType.getCode())
							.bookingsCount(bookingStore.getBookingsCount() + 1).build());
				}
				catch (NoSuchElementException e) {
					// 获取不到数据时，get()抛出异常
					repository.save(BookingStore.builder().roomType(roomType.getCode()).bookingsCount(1).build());
				}
			}
			else if (message.getPayload() instanceof HotelCancelEvent) {
				String roomType = ((HotelCancelEvent) message.getPayload()).getSubNumber().split("@")[0];
				BookingStore bookingStore = repository.findById(roomType).get();
				if (bookingStore.getBookingsCount() == 1) {
					repository.delete(bookingStore);
				}
				else {
					repository.save(BookingStore.builder().roomType(roomType)
							.bookingsCount(bookingStore.getBookingsCount() - 1).build());
				}
			}
		}
		return batchWrapper;
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
