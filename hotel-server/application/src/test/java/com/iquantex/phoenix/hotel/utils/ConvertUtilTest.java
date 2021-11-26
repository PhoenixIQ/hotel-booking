package com.iquantex.phoenix.hotel.utils;

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
		Map<String, Integer> map = new HashMap<>();
		map.put("1", 3);
		map.put("2", 1);
		map.put("3", 4);
		map.put("4", 2);
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

}
