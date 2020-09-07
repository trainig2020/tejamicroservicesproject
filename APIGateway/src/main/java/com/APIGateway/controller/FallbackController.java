package com.APIGateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
	
	@RequestMapping("/deptFallBack")
	public Mono<String> getdeptFallback(){
		return Mono.just("Service goes down....Try again later");
		
	}

}
