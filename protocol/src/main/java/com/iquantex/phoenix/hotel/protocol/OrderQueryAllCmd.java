package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class OrderQueryAllCmd implements Serializable {

	private String hotelCode;

}
