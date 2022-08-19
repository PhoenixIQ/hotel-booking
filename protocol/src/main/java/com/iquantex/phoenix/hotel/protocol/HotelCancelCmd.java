package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class HotelCancelCmd implements Serializable {

	private static final long serialVersionUID = -2900383695028981211L;

	private String hotelCode;

	private String subNumber;

}
