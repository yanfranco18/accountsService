package com.accounts;

import com.accounts.controllers.AccountControllers;
import com.accounts.models.Account;
import com.accounts.services.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(AccountControllers.class)
class AccountsApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private AccountServiceImpl service;

	@Test
	void getClientTest() {

		Flux<Account> ac = Flux.just(DataAccount.getList());
		when(service.findAll()).thenReturn(ac);

		Flux<Account> respBody = webTestClient.get().uri("/accounts")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Account.class)
				.getResponseBody();

		StepVerifier.create(respBody)
				.expectSubscription()
				.expectNext(DataAccount.getList())
				.verifyComplete();
	}

	@Test
	public void saveAccountTest(){

		Account ac = DataAccount.getList();

		when(service.save(ac)).thenReturn(Mono.just(ac));

		webTestClient.post().uri("/accounts")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(ac), Account.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void findByIdAccountTest() throws Exception{

		Mono<Account> ac = Mono.just(DataAccount.getList());
		when(service.findById(any())).thenReturn(ac);

		Flux<Account> respBody = webTestClient.get().uri("/accounts/getById/12233d")
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
	public void searchNumberTest() throws Exception{
		Mono<Account> ac = Mono.just(DataAccount.getList());
		when(service.findByNumber(any())).thenReturn(ac);

		Flux<Account> respBody = webTestClient.get().uri("/accounts/19123412345678")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Account.class)
				.getResponseBody();

		StepVerifier.create(respBody)
				.expectSubscription()
				.expectNextMatches(c -> c.getNumber().equals("19123412345678"))
				.expectComplete();
	}
}
