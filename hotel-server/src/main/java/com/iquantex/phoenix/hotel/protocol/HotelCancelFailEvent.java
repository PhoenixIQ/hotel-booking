package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class HotelCancelFailEvent implements Serializable {

	private static final long serialVersionUID = -1626983354873701406L;

	private String msg;

}
