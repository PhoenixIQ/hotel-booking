package com.iquantex.phoenix.hotel.utils;

import com.iquantex.phoenix.hotel.enumType.RoomType;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author quail
 */
public class ConvertUtilTest {

	@Test
	public void test_convert() {
		Map<RoomType, Integer> map = new HashMap<>();
		map.put(RoomType.DOUBLE, 3);
		map.put(RoomType.STANDARD, 1);
		map.put(RoomType.COUPLES, 4);
		map.put(RoomType.LUXURIOUS, 2);
		Map<String, Integer> sortMap = ConvertUtil.Map2Map(map);
		Assert.assertEquals(sortMap.getClass(), LinkedHashMap.class);
	}

	@Test
	public void test_sort() {
		Map<String, Integer> map = new HashMap<>();
		map.put("1", 3);
		map.put("2", 1);
		map.put("3", 4);
		map.put("4", 2);
		Map sortMap = ConvertUtil.sortMap(map);
		Assert.assertEquals(sortMap.getClass(), LinkedHashMap.class);
	}

	@Test
	public void test_() {
		Assert.assertEquals(ConvertUtil.num2Enum("1"), RoomType.DOUBLE);
		Assert.assertEquals(ConvertUtil.num2Enum("2"), RoomType.STANDARD);
		Assert.assertEquals(ConvertUtil.num2Enum("3"), RoomType.COUPLES);
		Assert.assertEquals(ConvertUtil.num2Enum("4"), RoomType.LUXURIOUS);
	}

}
