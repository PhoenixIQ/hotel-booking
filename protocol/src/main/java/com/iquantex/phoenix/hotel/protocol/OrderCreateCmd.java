package com.iquantex.phoenix.hotel.protocol;

import com.iquantex.phoenix.hotel.enumType.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@Getter
@Builder
@AllArgsConstructor
public class OrderCreateCmd implements Serializable {

	private static final long serialVersionUID = -1282834235746256697L;

	private String hotelCode;

	private RoomType roomType;

	private String subNumber;

}