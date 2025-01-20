package br.com.estudos.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("book-service")
public class FooBarController {

    private Logger logger = LoggerFactory.getLogger(FooBarController.class);

    // -> Retries com Resilience4j:
        // @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")

    // -> CircuitBreaker gerencia falhas temporárias controlando o fluxo de requisições: Closed (normal), Open (bloqueia após muitos erros) e Half-Open (teste para retorno ao normal)  com Resilience4j:
        // @CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")

    // -> Na aplicacao esta configurado para executar 2 requests a cada 10seg, se passa disso retorna uma exception
        // @RateLimiter(name = "default")

    // -> Determina quantas execuções concorrentes podem acontencer
        // @Bulkhead(name = "default")

    // -> PS: Todas as configuracoes do Resilience4j acontecem dentro do application.yml

    @GetMapping("/foo-bar")
    @Bulkhead(name = "default")
    public String fooBar() {
        logger.info("Request foo-bar is received!");
        //var response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
        return "Foo-bar!!!";
        //return response.getBody();
    }

    public String fallbackMethod(Exception e) {
        return "fallbackMethod foo-bar";
    }
}
