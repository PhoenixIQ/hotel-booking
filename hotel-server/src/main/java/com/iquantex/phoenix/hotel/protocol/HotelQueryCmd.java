package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class HotelQueryCmd implements Serializable {

	private static final long serialVersionUID = 5077299466591822621L;

	private String hotelCode;

}
