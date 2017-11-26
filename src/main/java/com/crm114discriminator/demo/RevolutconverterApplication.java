package com.crm114discriminator.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class RevolutconverterApplication implements ApplicationRunner {

	@Autowired
	private CsvConverter csvConverter;

	private static final Logger logger = LoggerFactory.getLogger(RevolutconverterApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(RevolutconverterApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		csvConverter.runConversion();
	}
}
