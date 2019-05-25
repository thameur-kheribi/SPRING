package com.spring.rest;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.rest.service.StorageService;


@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
public class SpringMain implements CommandLineRunner {

	@Autowired
	StorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args);

	}
	
	@Override
	public void run(String... arg) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}

}
