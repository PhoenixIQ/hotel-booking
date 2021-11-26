package com.iquantex.phoenix.hotel.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class OrderQueryByEvent implements Serializable {

	private String hotelCode;

	private OrderCreateEvent order;

}
