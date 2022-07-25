package com.iquantex.phoenix.hotel.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author quail
 */
@AllArgsConstructor
@Getter
public class OrderCancelEvent implements Serializable {

	private static final long serialVersionUID = 1159857138091434812L;

	private String hotelCode;

	private String subNumber;

}