package com.zerobase.cms;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@RequiredArgsConstructor
public class CmsUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(CmsUserApplication.class, args);
	}

}
