package com.iquantex.phoenix.hotel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iquantex.phoenix.client.PhoenixClient;
import com.iquantex.phoenix.client.RpcResult;
import com.iquantex.phoenix.hotel.enumType.RoomType;
import com.iquantex.phoenix.hotel.protocol.HotelCancelCmd;
import com.iquantex.phoenix.hotel.protocol.HotelCancelEvent;
import com.iquantex.phoenix.hotel.protocol.HotelCancelFailEvent;
import com.iquantex.phoenix.hotel.protocol.HotelCreateCmd;
import com.iquantex.phoenix.hotel.protocol.HotelCreateEvent;
import com.iquantex.phoenix.hotel.protocol.HotelCreateFailEvent;
import com.iquantex.phoenix.hotel.protocol.HotelQueryCmd;
import com.iquantex.phoenix.hotel.protocol.HotelQueryEvent;
import com.iquantex.phoenix.hotel.repository.BookingsStoreRepository;
import com.iquantex.phoenix.hotel.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author quail
 */
@RestController
@RequestMapping("hotel")
public class HotelController {

	@Autowired
	private PhoenixClient client;

	@Autowired
	private BookingsStoreRepository repository;

	@PutMapping("/bookings/{hotelCode}/{roomType}")
	public String bookings(@PathVariable String hotelCode, @PathVariable String roomType) {
		// 转换房间类型
		if (ConvertUtil.num2Enum(roomType) == null) {
			return "Wrong room choice ";
		}
		// 生成预约号: roomType@UUID
		String subNumber = roomType + "@" + UUID.randomUUID().toString();
		HotelCreateCmd cmd = new HotelCreateCmd(hotelCode, ConvertUtil.num2Enum(roomType), subNumber);
		Future<RpcResult> future = client.send(cmd, "hotel-bookings", UUID.randomUUID().toString());
		try {
			Object data = future.get(10, TimeUnit.SECONDS).getData();
			if (data instanceof HotelCreateEvent) {
				return ((HotelCreateEvent) data).getSubNumber();
			}
			return ((HotelCreateFailEvent) data).getMsg();
		}
		catch (InterruptedException | ExecutionException | TimeoutException e) {
			return "rpc error: " + e.getMessage();
		}
	}

	@GetMapping("/query/{hotelCode}")
	public String queryRestRoom(@PathVariable String hotelCode) {
		HotelQueryCmd hotelQueryCmd = new HotelQueryCmd(hotelCode);
		Future<RpcResult> future = client.send(hotelQueryCmd, "hotel-bookings", UUID.randomUUID().toString());
		try {
			HotelQueryEvent event = (HotelQueryEvent) future.get(10, TimeUnit.SECONDS).getData();
			return new ObjectMapper().writeValueAsString(ConvertUtil.Map2Map(event.getRestRoom()));
		}
		catch (InterruptedException | ExecutionException | TimeoutException | JsonProcessingException e) {
			return "rpc error: " + e.getMessage();
		}
	}

	@PutMapping("/cancel/{hotelCode}/{subNumber}")
	public String cancel(@PathVariable String hotelCode, @PathVariable String subNumber) {
		HotelCancelCmd hotelCancelCmd = new HotelCancelCmd(hotelCode, subNumber);
		Future<RpcResult> future = client.send(hotelCancelCmd, "hotel-bookings", UUID.randomUUID().toString());
		try {
			Object data = future.get(10, TimeUnit.SECONDS).getData();
			if (data instanceof HotelCancelEvent) {
				return "cancel ok";
			}
			return ((HotelCancelFailEvent) data).getMsg();
		}
		catch (InterruptedException | ExecutionException | TimeoutException e) {
			return "rpc error: " + e.getMessage();
		}
	}

	@GetMapping("/queryPop")
	public String queryRestRoom() {
		try {
			Map<RoomType, Integer> map = new HashMap<>();
			repository.findAll().forEach(bookingStore -> map.put(RoomType.apply(bookingStore.getRoomType()),
					bookingStore.getBookingsCount()));
			return new ObjectMapper().writeValueAsString(ConvertUtil.Map2Map(ConvertUtil.sortMap(map)));
		}
		catch (JsonProcessingException e) {
			return "query fail: " + e.getMessage();
		}
	}

}
