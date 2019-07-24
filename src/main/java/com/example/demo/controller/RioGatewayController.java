package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * @author zengxc
 * @since 2019/7/19
 */
@RestController
public class RioGatewayController {
	@Autowired
	private RestTemplate restTemplate;
	@GetMapping("/")
	public String index(){
		//
		String uri = "http://localhost:8080/test";
		String paasid = "cssg_tyyhzxcsyy";
		String paastoken = "hWeH8LNgoEfL7CfFjXuLG4jOGhr8xZN6";
		String signature= "";
		RequestEntity requestEntity = null;
		try {
			requestEntity = RequestEntity.get(new URI(uri))
					.header("x-tif-paasid",paasid)
					.header("x-tif-timestamp",System.currentTimeMillis()+"")
					.header("x-tif-nonce", UUID.randomUUID().toString())
					.header("x-tif-signature","qq")
					.header("Content-Type","application/x-www-form-urlencoded")
					.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String result = restTemplate.exchange(requestEntity,String.class).getBody();
		return "index:"+result;
	}
	@GetMapping("/test")
	public String index(@RequestHeader("x-tif-paasid") String x1,@RequestHeader("x-tif-timestamp") String x2){
		return "x1:"+x1+" x2:"+x2;
	}
}
