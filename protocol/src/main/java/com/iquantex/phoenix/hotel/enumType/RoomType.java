package com.iquantex.phoenix.hotel.enumType;

import lombok.Getter;

/**
 * @author quail
 */
public enum RoomType {

	DOUBLE("1", "双床房床"), STANDARD("2", "标准房"), COUPLES("3", "情侣房"), LUXURIOUS("4", "商务房");

	@Getter
	private String code;

	@Getter
	private String name;

	RoomType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static RoomType apply(String code) {
		RoomType type;
		switch (code) {
		case "1":
			type = DOUBLE;
			break;
		case "2":
			type = STANDARD;
			break;
		case "3":
			type = COUPLES;
			break;
		case "4":
			type = LUXURIOUS;
			break;
		default:
			type = STANDARD;
		}
		return type;
	}

}
