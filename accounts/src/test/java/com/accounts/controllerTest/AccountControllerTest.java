package com.accounts.controllerTest;

import com.accounts.DataAccount;
import com.accounts.controllers.AccountControllers;
import com.accounts.models.Account;
import com.accounts.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    private static WebTestClient webTestClient;
    @Mock
    private static AccountServiceImpl accountService;

    @BeforeAll
    public static void setUp(){
        accountService = mock(AccountServiceImpl.class);
        webTestClient = WebTestClient.bindToController(new AccountControllers(accountService))
                .configureClient()
                .baseUrl("/accounts")
                .build();
    }

    @Test
    void getAccountTest() {

        Flux<Account> account = Flux.just(DataAccount.getList());

        when(accountService.findAll()).thenReturn(account);

        Flux<Account> respBody = webTestClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk() //200
                .returnResult(Account.class)
                .getResponseBody();

        StepVerifier.create(respBody)
                .expectSubscription()
                .expectNext(DataAccount.getList())
                .verifyComplete();
    }

    @Test
    void saveAccountTest(){

        Account account = DataAccount.getList();

        when(accountService.save(account)).thenReturn(Mono.just(account));

        webTestClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(account), Account.class)
                .exchange()
                .expectStatus().isCreated(); //201
    }

    @Test
    void deleteAccountTest() throws Exception{

        when(accountService.delete("12233d")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/12233d")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void findByIdAccountTest() throws Exception{

        Mono<Account> account = Mono.just(DataAccount.getList());
        when(accountService.findById(any())).thenReturn(account);

        Flux<Account> respBody = webTestClient.get().uri("/getById/12233d")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Account.class)
                .getResponseBody();

        StepVerifier.create(respBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getId().equals("12233d"))
                .verifyComplete();
    }

    @Test
    void searchAccountTest() throws Exception{
        Mono<Account> account = Mono.just(DataAccount.getList());
        when(accountService.findByNumber(any())).thenReturn(account);

        Flux<Account> respBody = webTestClient.get().uri("/search/19123412345678")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Account.class)
                .getResponseBody();

        StepVerifier.create(respBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getNumber().equals("19123412345678"))
                .verifyComplete();
    }

    @Test
    void saveDepositAccount() throws Exception{

        Account ac = DataAccount.saveDepWit();

        when(accountService.saveDepositAccount(ac)).thenReturn(Mono.just(ac));

        webTestClient.post().uri("/saveDeposit")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(ac), Account.class)
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    void saveWithdrawalAccount() throws Exception{

        Account ac = DataAccount.saveDepWit();

        when(accountService.saveWithdrawalAccount(ac)).thenReturn(Mono.just(ac));

        webTestClient.post().uri("/saveWithdrawal")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(ac), Account.class)
                .exchange()
                .expectStatus().isCreated();
    }

}
