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
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://"+System.getenv("POSTGRES_SERVICE_HOST") + ":"
        				+ System.getenv("POSTGRES_SERVICE_PORT") +"/postgres")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
