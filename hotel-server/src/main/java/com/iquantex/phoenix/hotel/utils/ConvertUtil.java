package com.iquantex.phoenix.hotel.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author quail
 */
public class ConvertUtil {

	/**
	 * 转换对应房间类型的中文输出
	 * @param map
	 * @return
	 */
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

	/**
	 * 对map进行排序
	 * @param oldMap
	 * @return
	 */
	public static Map sortMap(Map oldMap) {
		ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(oldMap.entrySet());
		list.sort(Comparator.comparingInt(Map.Entry::getValue));
		Map newMap = new LinkedHashMap();
		for (int i = list.size() - 1; i >= 0; i--) {
			newMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		return newMap;
	}

}
