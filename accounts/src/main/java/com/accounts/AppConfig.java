package com.accounts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient registryProducts(){
        return WebClient.create("http://localhost:8093/products");
    }

    @Bean
    public WebClient registryClients(){
        return WebClient.create("http://localhost:8094/clients");
    }


}
