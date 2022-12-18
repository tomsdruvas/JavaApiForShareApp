package com.rateMyPortfolio;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rateMyPortfolio.shareItem.ShareItem;
import com.rateMyPortfolio.shareItem.ShareItemRepository;
import com.rateMyPortfolio.utils.CurrencyEnum;

@SpringBootApplication
public class RateMyPortfolioApp {

	@Autowired
	private ShareItemRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(RateMyPortfolioApp.class, args);
	}

	@Bean
	public CommandLineRunner loadData() {
		ShareItem shareItem = new ShareItem("TestShare", "Test", new BigDecimal("10.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), false);
		ShareItem shareItem2 = new ShareItem("TestShare2", "Test2", new BigDecimal("30.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), false);
		return args -> repository.saveAllAndFlush(List.of(shareItem, shareItem2));
	}
}
