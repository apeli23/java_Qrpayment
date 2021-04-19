package com.payment.qrcode.stkpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.modelmapper.ModelMapper;
import java.awt.image.BufferedImage;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;

@SpringBootApplication
public class StkpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(StkpayApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}

}
