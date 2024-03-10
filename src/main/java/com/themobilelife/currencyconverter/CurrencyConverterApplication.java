package com.themobilelife.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class CurrencyConverterApplication {

	public static void main(String[] args){ SpringApplication.run(CurrencyConverterApplication.class, args);}

}