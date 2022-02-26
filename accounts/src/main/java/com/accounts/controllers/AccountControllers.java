package com.accounts.controllers;

import com.accounts.models.Account;
import com.accounts.services.AccountServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountControllers {

    private final AccountServiceImpl accountService;

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

    //metodo crear
    @CircuitBreaker(name="accounts", fallbackMethod = "fallback")
    @TimeLimiter(name="accounts")
    @PostMapping
    public Mono<ResponseEntity<Account>> create(@RequestBody Account account){
        //ahora guardamos la cuenta, mediante map, cambiamos el flujo de tipo mono a un responseEntity
        return accountService.save(account)
                //mostramos el estado en el http, indicamos la uri de la cuenta se crea
                .map(p -> ResponseEntity.created(URI.create("/accounts/".concat(p.getId())))
                        //Modificamos la respuesta en el body con el contentType
                        .contentType(MediaType.APPLICATION_JSON)
                        //Y pasamos la cuenta creada
                        .body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //metodo buscar por id
    @CircuitBreaker(name="accounts", fallbackMethod = "fallback")
    @TimeLimiter(name="accounts")
    @GetMapping("/getById/{id}")
    public Mono<ResponseEntity<Account>> getById(@PathVariable String id){
        return accountService.findById(id)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //metodo buscar por number
    @CircuitBreaker(name="accounts", fallbackMethod = "fallback")
    @TimeLimiter(name="accounts")
    @GetMapping("/search/{number}")
    public Mono<ResponseEntity<Account>> search(@PathVariable String number){
        //buscamos el tipo de number
        return accountService.findByNumber(number)
                //mostramos la respuesta
                .map(p -> ResponseEntity.ok()
                        //Modificamos la respuesta en el body con el contentType
                        .contentType(MediaType.APPLICATION_JSON)
                        //devolvemos el objeto obtenido
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //method deposit
    @CircuitBreaker(name="accounts", fallbackMethod = "fallback")
    @TimeLimiter(name="accounts")
    @PostMapping("/saveDeposit")
    public Mono<ResponseEntity<Account>> saveDepositAccount(@RequestBody Account account){
        return accountService.saveDepositAccount(account)
                .map(p -> ResponseEntity.created(URI.create("/accounts/saveDeposit/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p));
    }

    //method deposit
    @CircuitBreaker(name="accounts", fallbackMethod = "fallback")
    @TimeLimiter(name="accounts")
    @PostMapping("/saveWithdrawal")
    public Mono<ResponseEntity<Account>> saveWithdrawalAccount(@RequestBody Account account){
        return accountService.saveWithdrawalAccount(account)
                .map(p -> ResponseEntity.created(URI.create("/accounts/saveWithdrawal/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @CircuitBreaker(name="accounts", fallbackMethod = "fallback")
    @TimeLimiter(name="accounts")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAccount (@PathVariable String id){

        return accountService.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    //metodo para manejar el error
    private String fallback(HttpServerErrorException ex) {
        return "Response 200, fallback method for error:  " + ex.getMessage();
    }

}
