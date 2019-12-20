package com.anitang99.SocialAppServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class SocialAppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialAppServerApplication.class, args);
	}

}
