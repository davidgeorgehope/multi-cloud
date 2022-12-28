package com.elastic.multicloud;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class MyServiceImpl implements MyService {


    public MyServiceImpl() {
    }

    @Override
    public Mono<String> getById(String id) {
        Mono<String> helloWorld = Mono.just("Hello World !");
        return helloWorld;
    }

    @Override
    public Flux<String> getAll() {
        List<String> stringList = Arrays.asList("Hello", "foo", "bar");
        Flux<String> fluxFromList = Flux.fromIterable(stringList);
        return fluxFromList;
    }
}
