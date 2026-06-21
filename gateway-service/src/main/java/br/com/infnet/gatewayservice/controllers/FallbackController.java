package br.com.infnet.gatewayservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fallback")
public class FallbackController {

    @RequestMapping("courts")
    public Mono<ResponseEntity<String>> courtsFallback(){
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de quadras indisponível no momento"));
    }

    @RequestMapping("/search")
    public Mono<ResponseEntity<String>> searchFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Serviço de busca indisponível no momento."));
    }
}
