package com.tq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@EnableScheduling
@SpringBootApplication
public class TqwebApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(TqwebApplication.class, args);
	}
	
	//Bean methods也可以定义在@Configuration类下面，效果一样。因为@SpringBootApplication包含了@Configuration
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
}

