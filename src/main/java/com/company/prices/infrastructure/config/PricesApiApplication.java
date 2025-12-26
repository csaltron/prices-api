package com.company.prices.infrastructure.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.company.prices")
@EntityScan(basePackages = "com.company.prices.infrastructure.adapter.persistence.entity")
@EnableJpaRepositories(basePackages = "com.company.prices.infrastructure.adapter.persistence")
public class PricesApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PricesApiApplication.class, args);
    }
}
