package com.iquantex.phoenix.hotel.protocol;

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
public class OrderCancelCmd implements Serializable {

	private static final long serialVersionUID = 4708990945046294579L;

	private String hotelCode;

	private String subNumber;

}