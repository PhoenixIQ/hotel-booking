package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class HotelCancelEvent implements Serializable {

	private static final long serialVersionUID = -1282834235746256697L;

	private String hotelCode;

	private String subNumber;

}
