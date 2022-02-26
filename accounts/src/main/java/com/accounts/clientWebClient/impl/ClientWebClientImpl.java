package com.accounts.clientWebClient.impl;

import com.accounts.clientWebClient.IClientWebClientService;
import com.accounts.clientWebClient.dto.ClientWebClient;
import com.accounts.config.properties.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ClientWebClientImpl implements IClientWebClientService {

    private final AppConfig properties;
    private final WebClient webClient;

    public ClientWebClientImpl(AppConfig properties) {
        this.properties = properties;
        this.webClient = WebClient.create(properties.getUrl());
    }

    @Override
    public Mono<ClientWebClient> findById(String idClient) {
        log.info("Obteniendo id client");
        return webClient.get()
                .uri(properties.getPath().concat("/getById/").concat(idClient))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ClientWebClient.class);
    }

}
