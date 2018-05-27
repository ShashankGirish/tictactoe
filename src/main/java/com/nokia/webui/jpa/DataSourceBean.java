package com.nokia.webui.jpa;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class DataSourceBean {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    @Primary
    public DataSource getDataSource() {
        final String hostname = 
//        		"localhost";
        		System.getenv("POSTGRES_SERVICE_HOST");
		final String port = 
//				"5432";
				System.getenv("POSTGRES_SERVICE_PORT");
		return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://"+hostname+ ":"+port+"/postgres")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
