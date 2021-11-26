package com.iquantex.phoenix.hotel.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author quail
 */
public class ConvertUtil {

	public static Map<String, Integer> Map2Map(Map<String, Integer> map) {
		Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
		map.forEach((key, value) -> {
			if ("1".equals(key)) {
				linkedHashMap.put("大床房", value);
			}
			else if ("2".equals(key)) {
				linkedHashMap.put("标准间", value);
			}
			else if ("3".equals(key)) {
				linkedHashMap.put("情侣套房", value);
			}
			else if ("4".equals(key)) {
				linkedHashMap.put("总统套房", value);
			}
		});
		return linkedHashMap;
	}

}
