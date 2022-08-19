package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class OrderQueryAllEvent implements Serializable {

	private String hotelCode;

	private List<OrderCreateEvent> list;

}
