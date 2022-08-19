package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class HotelCreateCmd implements Serializable {

	private static final long serialVersionUID = 719772692097810576L;

	private String hotelCode;

	private String restType;

	private String subNumber;

}
