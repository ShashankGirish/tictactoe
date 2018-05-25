package com.nokia.webui.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class NeoTicTacToeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NeoTicTacToeApplication.class, args);
	}
}
