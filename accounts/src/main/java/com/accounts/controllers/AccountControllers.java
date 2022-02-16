package com.accounts.controllers;

import com.accounts.models.Account;
import com.accounts.services.IAccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountControllers {

    private final IAccountService accountService;

    //@CircuitBreaker, name(va el nombre de la instancia usado en la configuracion yml, "items")
    //fallbackMethod, permite manejar el error, mediante un metodo definido
    //anotacion para el timeout - @TimeLimiter name(va el nombre de la instancia usado en la configuracion yml, "items")
    //ComplatebleFuture<"tipo">, es envolver una llamada asincrona, represeta futura que ocurre en el tiempo, maneja un generic
    //supplyAsync, permite envolver la llamada en una futura asincrona del
    //Metodo listar, usando response entity para manejar la respuesta del status y la respuesta del body
    @CircuitBreaker(name="accounts", fallbackMethod = "fallback")
    @TimeLimiter(name="accounts")
    @GetMapping
    public Mono<ResponseEntity<Flux<Account>>> getAccount(){
        log.info("iniciando lista");
        return Mono.just(
                //manejo de la respuesta http
                ResponseEntity.ok()
                        //mostrar en el body mediante json
                        .contentType(MediaType.APPLICATION_JSON)
                        //mostrando en el body la respuesta
                        .body(accountService.findAll()));
    }

    //metodo para manejar el error
    private String fallback(HttpServerErrorException ex) {
        return "Response 200, fallback method for error:  " + ex.getMessage();
    }

}
