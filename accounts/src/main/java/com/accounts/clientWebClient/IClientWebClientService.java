package com.accounts.clientWebClient;

import com.accounts.clientWebClient.dto.ClientWebClient;
import reactor.core.publisher.Mono;

public interface IClientWebClientService {

    Mono<ClientWebClient> findById(String idClient);
}
