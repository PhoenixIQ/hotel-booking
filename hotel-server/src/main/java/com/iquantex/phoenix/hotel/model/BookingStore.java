package com.iquantex.phoenix.hotel.model;

import com.iquantex.phoenix.hotel.enumType.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author quail
 */
@Entity
@Data
@Builder
@Table(name = "BOOKING_STORE")
@AllArgsConstructor
@NoArgsConstructor
public class BookingStore implements Serializable {

	@Id
	private String roomType;

	private int bookingsCount;

}
