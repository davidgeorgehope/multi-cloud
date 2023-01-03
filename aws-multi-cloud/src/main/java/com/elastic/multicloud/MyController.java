package com.elastic.multicloud;

import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/my-objects")
public class MyController {


    private final MyService service;
    private final ExampleService exampleService;
    public MyController(MyService service,ExampleService exampleService) {
        this.service = service;

        this.exampleService=exampleService;
    }

    @GetMapping("/{id}")
    public Mono<String> getById(@PathVariable String id) {
        exampleService.processInBackground();

        return service.getById(id);
    }

    @GetMapping
    public Flux<String> getAll() {
        return service.getAll();
    }


}
