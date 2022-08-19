package com.iquantex.phoenix.hotel.domain;

import com.iquantex.phoenix.hotel.protocol.HotelCancelCmd;
import com.iquantex.phoenix.hotel.protocol.HotelCancelFailEvent;
import com.iquantex.phoenix.hotel.protocol.HotelCreateCmd;
import com.iquantex.phoenix.hotel.protocol.HotelCreateEvent;
import com.iquantex.phoenix.hotel.protocol.HotelQueryCmd;
import com.iquantex.phoenix.hotel.protocol.HotelQueryEvent;
import com.iquantex.phoenix.server.aggregate.ActReturn;
import com.iquantex.phoenix.server.testkit.EntityAggregateFixture;
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
		HotelCreateCmd hotelCreateCmd = new HotelCreateCmd("hotel-1", "1", "1@" + UUID.randomUUID().toString());
		fixture.when(hotelCreateCmd).printIdentify().expectMessage(HotelCreateEvent.class);
		HotelAggregate hotelAggregate = fixture.getAggregateRoot(HotelAggregate.class, "hotel-1");
		Assert.assertNotNull(hotelAggregate.getRestRoom());
	}

	/**
	 * 测试查询剩余房间
	 */
	@Test
	public void test_queryRestRoom() {
		EntityAggregateFixture fixture = getFixture();
		HotelAggregate hotelAggregate = fixture.getAggregateRoot(HotelAggregate.class, "hotel-1");
		HotelQueryCmd hotelQueryCmd = new HotelQueryCmd("hotel-1");
		ActReturn act = hotelAggregate.act(hotelQueryCmd);
		Assert.assertEquals(act.getEvent().getClass(), HotelQueryEvent.class);
	}

	/**
	 * 测试取消预订，预约号不存在
	 */
	@Test
	public void test_cancel() {
		EntityAggregateFixture fixture = getFixture();
		HotelAggregate hotelAggregate = fixture.getAggregateRoot(HotelAggregate.class, "hotel-1");
		HotelCancelCmd hotelCancelCmd = new HotelCancelCmd("hotel-1", "1@" + UUID.randomUUID().toString());
		ActReturn act = hotelAggregate.act(hotelCancelCmd);
		Assert.assertEquals(act.getEvent().getClass(), HotelCancelFailEvent.class);
	}

}
