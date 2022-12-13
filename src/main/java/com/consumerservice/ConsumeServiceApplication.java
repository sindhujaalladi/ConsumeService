package com.consumerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController
@Slf4j
@RequestMapping("consume")
public class ConsumeServiceApplication {
	
	private int count=1;
	
	@Autowired
	private RestTemplate resttemplate;
	
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
	
	
	@GetMapping("update")
	//@CircuitBreaker(name = "EmailNotificationService",fallbackMethod = "dummyconsumeservice")
	@Retry(name= "EmailNotificationService",fallbackMethod="dummyretrymethod")
	public String consumeservices() {
		//log.debug("retry attempts attempted" +count++ +"times");
		System.out.println("retry attempts attempted" +count++ +"times");
		String emailresponse = resttemplate.getForObject("http://localhost:9797/emailservice/sendmail", String.class);
		String notificationresponse = resttemplate.getForObject("http://localhost:9898/notificationservice/sendnotification", String.class);
		return emailresponse + "\n" + notificationresponse ;

	}
	
	public String dummyretrymethod(Exception e) {
		return "sorry service went down please retry using retry method !!";
	}
	
	public String dummyconsumeservice(Exception e) {
		return "sorry services are down please be wait and retry after sometime!!!";
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumeServiceApplication.class, args);
	}

}
