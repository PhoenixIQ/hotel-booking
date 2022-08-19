package com.iquantex.phoenix.hotel.domain;

import com.iquantex.phoenix.hotel.enumType.RoomType;
import com.iquantex.phoenix.hotel.protocol.OrderCancelCmd;
import com.iquantex.phoenix.hotel.protocol.OrderCreateCmd;
import com.iquantex.phoenix.hotel.protocol.OrderQueryAllCmd;
import com.iquantex.phoenix.hotel.protocol.OrderQueryByCmd;
import com.iquantex.phoenix.server.testkit.EntityAggregateFixture;
import org.junit.Before;
import org.junit.Test;

/**
 * @author quail
 */
public class OrderAggregateTest {

	private EntityAggregateFixture fixture;

	@Before
	public void init() {
		fixture = new EntityAggregateFixture(OrderAggregate.class.getPackage().getName());
	}

	@Test
	public void test_queryAll() {
		OrderQueryAllCmd orderQueryAllCmd = new OrderQueryAllCmd("iHome");
		fixture.when(orderQueryAllCmd).printIdentify().expectRetSuccessCode();
	}

	@Test
	public void test_queryBy() {
		OrderQueryByCmd orderQueryByCmd = new OrderQueryByCmd("iHome", "order-1");
		fixture.when(orderQueryByCmd).printIdentify().expectRetFailCode();
	}

	@Test
	public void test_cancel() {
		OrderCancelCmd orderCancelCmd = new OrderCancelCmd("iHome", "order-1");
		fixture.when(orderCancelCmd).printIdentify().expectRetSuccessCode();
	}

	@Test
	public void test_create() {
		OrderCreateCmd orderCreateCmd = new OrderCreateCmd("iHome", RoomType.DOUBLE, "order-1");
		fixture.when(orderCreateCmd).printIdentify().expectRetSuccessCode();
	}

}
