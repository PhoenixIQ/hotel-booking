package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class HotelCreateFailEvent implements Serializable {

	private static final long serialVersionUID = -4501845839053489536L;

	private String msg;

}
