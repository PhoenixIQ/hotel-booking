package com.iquantex.phoenix.hotel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iquantex.phoenix.client.PhoenixClient;
import com.iquantex.phoenix.client.RpcResult;
import com.iquantex.phoenix.hotel.protocol.OrderQueryAllCmd;
import com.iquantex.phoenix.hotel.protocol.OrderQueryAllEvent;
import com.iquantex.phoenix.hotel.protocol.OrderQueryByCmd;
import com.iquantex.phoenix.hotel.protocol.OrderQueryByEvent;
import com.iquantex.phoenix.hotel.protocol.OrderQueryFailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author quail
 */
@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	private PhoenixClient client;

	@Value("${spring.application.name}")
	private String appName;

	@GetMapping("/queryAll/{hotelCode}")
	public Object queryAll(@PathVariable String hotelCode) {
		OrderQueryAllCmd orderQueryAllCmd = new OrderQueryAllCmd(hotelCode);
		Future<RpcResult> future = client.send(orderQueryAllCmd, appName, UUID.randomUUID().toString());
		try {
			OrderQueryAllEvent event = (OrderQueryAllEvent) future.get(10, TimeUnit.SECONDS).getData();
			return event.getList();
		}
		catch (InterruptedException | ExecutionException | TimeoutException e) {
			return "rpc error: " + e.getMessage();
		}
	}

	@GetMapping("/query/{hotelCode}/{orderNumber}")
	public Object queryRestRoom(@PathVariable String hotelCode, @PathVariable String orderNumber) {
		OrderQueryByCmd orderQueryByCmd = new OrderQueryByCmd(hotelCode, orderNumber);
		Future<RpcResult> future = client.send(orderQueryByCmd, appName, UUID.randomUUID().toString());
		try {
			Object obj = future.get(10, TimeUnit.SECONDS).getData();
			if (obj instanceof OrderQueryByEvent) {
				return ((OrderQueryByEvent) obj).getOrder();
			}
			return ((OrderQueryFailEvent) obj).getMsg();
		}
		catch (InterruptedException | ExecutionException | TimeoutException e) {
			return "rpc error: " + e.getMessage();
		}
	}

}
