package com.elastic.multicloud;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/my-objects")
public class MyController {

    private final MyService service;
    private final ExampleService exampleService;

    public MyController(MyService service, ExampleService exampleService) {
        this.service = service;
        this.exampleService = exampleService;
    }

    @GetMapping("/{id}")
    public Mono<String> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping
    public Flux<String> getAll() {
        return service.getAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        exampleService.processInBackground();
    }



}
