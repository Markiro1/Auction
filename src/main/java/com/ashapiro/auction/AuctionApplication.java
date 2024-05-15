package com.ashapiro.auction;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@SpringBootApplication
public class AuctionApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}

}
