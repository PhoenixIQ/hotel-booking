package com.iquantex.phoenix.hotel.protocol;

import com.iquantex.phoenix.hotel.enumType.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@AllArgsConstructor
@Getter
public class OrderCreateEvent implements Serializable {

	private static final long serialVersionUID = -1666190644126020224L;

	private String hotelCode;

	private RoomType roomType;

	private String subNumber;

}
