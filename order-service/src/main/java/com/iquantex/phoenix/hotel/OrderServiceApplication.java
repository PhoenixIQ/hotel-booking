package com.iquantex.phoenix.hotel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author quail
 */
@Slf4j
@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(OrderServiceApplication.class, args);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			System.exit(1);
		}
	}

}
