package com.iquantex.phoenix.hotel.protocol;

import com.iquantex.phoenix.hotel.enumType.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author quail
 */
@Getter
@AllArgsConstructor
public class HotelQueryEvent implements Serializable {

	private static final long serialVersionUID = -4428963449385271034L;

	private String hotelCode;

	private Map<RoomType, Integer> restRoom;

}
