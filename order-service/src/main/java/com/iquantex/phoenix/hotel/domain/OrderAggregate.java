package com.iquantex.phoenix.hotel.domain;

import com.iquantex.phoenix.core.message.RetCode;

import com.iquantex.phoenix.hotel.protocol.OrderCancelCmd;
import com.iquantex.phoenix.hotel.protocol.OrderCancelEvent;
import com.iquantex.phoenix.hotel.protocol.OrderCreateCmd;
import com.iquantex.phoenix.hotel.protocol.OrderCreateEvent;
import com.iquantex.phoenix.hotel.protocol.OrderQueryAllCmd;
import com.iquantex.phoenix.hotel.protocol.OrderQueryAllEvent;
import com.iquantex.phoenix.hotel.protocol.OrderQueryByCmd;
import com.iquantex.phoenix.hotel.protocol.OrderQueryByEvent;
import com.iquantex.phoenix.hotel.protocol.OrderQueryFailEvent;
import com.iquantex.phoenix.server.aggregate.ActReturn;
import com.iquantex.phoenix.server.aggregate.CommandHandler;
import com.iquantex.phoenix.server.aggregate.EntityAggregateAnnotation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quail
 */
@EntityAggregateAnnotation(aggregateRootType = "OrderAggregate")
public class OrderAggregate implements Serializable {

	private static final long serialVersionUID = -4051924255577694209L;

	private List<OrderCreateEvent> orders = new ArrayList<>();

	@CommandHandler(aggregateRootId = "hotelCode")
	public ActReturn act(OrderQueryAllCmd cmd) {
		OrderQueryAllEvent orderCode = new OrderQueryAllEvent("hotelCode", orders);
		return ActReturn.builder().retCode(RetCode.SUCCESS).event(orderCode).build();
	}

	public void on(OrderQueryAllEvent event) {
	}

	@CommandHandler(aggregateRootId = "hotelCode")
	public ActReturn act(OrderQueryByCmd cmd) {
		boolean flag = false;
		OrderCreateEvent order = null;
		for (OrderCreateEvent event : orders) {
			if (event.getSubNumber().equals(cmd.getOrderNumber())) {
				flag = true;
				order = event;
			}
		}
		if (flag) {
			return ActReturn.builder().retCode(RetCode.SUCCESS).event(new OrderQueryByEvent("hotelCode", order))
					.build();
		}
		return ActReturn.builder().retCode(RetCode.FAIL)
				.event(new OrderQueryFailEvent("hotelCode", "There is no such order number")).build();
	}

	public void on(OrderQueryByEvent event) {
	}

	public void on(OrderQueryFailEvent event) {
	}

	@CommandHandler(aggregateRootId = "hotelCode")
	public ActReturn act(OrderCreateCmd createCmd) {
		return ActReturn.builder().retCode(RetCode.SUCCESS).event(
				new OrderCreateEvent(createCmd.getHotelCode(), createCmd.getRoomType(), createCmd.getSubNumber()))
				.build();
	}

	public void on(OrderCreateEvent event) {
		orders.add(event);
	}

	@CommandHandler(aggregateRootId = "hotelCode")
	public ActReturn act(OrderCancelCmd cmd) {
		return ActReturn.builder().retCode(RetCode.SUCCESS)
				.event(new OrderCancelEvent(cmd.getHotelCode(), cmd.getSubNumber())).build();
	}

	public void on(OrderCancelEvent event) {
		orders.removeIf(e -> {
			if (e.getSubNumber().equals(event.getSubNumber())) {
				return true;
			}
			return false;
		});
	}

}
