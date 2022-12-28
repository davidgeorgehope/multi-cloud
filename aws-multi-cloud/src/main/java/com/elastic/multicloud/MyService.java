package com.elastic.multicloud;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MyService {
    Mono<String> getById(String id);
    Flux<String> getAll();
}
