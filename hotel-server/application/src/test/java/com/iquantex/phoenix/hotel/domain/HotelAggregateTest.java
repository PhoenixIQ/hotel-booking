package com.iquantex.phoenix.hotel.domain;

import com.iquantex.phoenix.hotel.enumType.RoomType;
import com.iquantex.phoenix.hotel.message.HotelCancelCmd;
import com.iquantex.phoenix.hotel.message.HotelCancelFailEvent;
import com.iquantex.phoenix.hotel.message.HotelCreateCmd;
import com.iquantex.phoenix.hotel.message.HotelCreateEvent;
import com.iquantex.phoenix.hotel.message.HotelQueryCmd;
import com.iquantex.phoenix.hotel.message.HotelQueryEvent;
import com.iquantex.phoenix.server.aggregate.ActReturn;
import com.iquantex.phoenix.server.test.EntityAggregateFixture;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * @author quail
 */
public class HotelAggregateTest {

	private EntityAggregateFixture getFixture() {
		EntityAggregateFixture fixture = new EntityAggregateFixture(HotelAggregate.class.getPackage().getName());
		return fixture;
	}

	/**
	 * 测试预约流程
	 */
	@Test
	public void test_bookings() {
		EntityAggregateFixture fixture = getFixture();
		HotelCreateCmd hotelCreateCmd = new HotelCreateCmd("iHome", RoomType.DOUBLE,
				"1@" + UUID.randomUUID().toString());
		fixture.when(hotelCreateCmd).printIdentify().expectMessage(HotelCreateEvent.class);
		HotelAggregate hotelAggregate = fixture.getAggregateRoot(HotelAggregate.class, "iHome");
		Assert.assertNotNull(hotelAggregate.getRestRoom());
	}

	/**
	 * 测试查询剩余房间
	 */
	@Test
	public void test_queryRestRoom() {
		EntityAggregateFixture fixture = getFixture();
		HotelAggregate hotelAggregate = fixture.getAggregateRoot(HotelAggregate.class, "iHome");
		HotelQueryCmd hotelQueryCmd = new HotelQueryCmd("iHome");
		ActReturn act = hotelAggregate.act(hotelQueryCmd);
		Assert.assertEquals(act.getEvent().getClass(), HotelQueryEvent.class);
	}

	/**
	 * 测试取消预订，预约号不存在
	 */
	@Test
	public void test_cancel() {
		EntityAggregateFixture fixture = getFixture();
		HotelAggregate hotelAggregate = fixture.getAggregateRoot(HotelAggregate.class, "iHome");
		HotelCancelCmd hotelCancelCmd = new HotelCancelCmd("iHome", "1@" + UUID.randomUUID().toString());
		ActReturn act = hotelAggregate.act(hotelCancelCmd);
		Assert.assertEquals(act.getEvent().getClass(), HotelCancelFailEvent.class);
	}

}
