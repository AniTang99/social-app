package com.anitang99.SocialAppServer.WebSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class ChatSocketConfig {
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Bean
	public CustomConfigurator customSpringConfigurator() {
		return new CustomConfigurator();
	}

}
