package com.elastic.multicloud;

import co.elastic.apm.api.Scope;
import co.elastic.apm.api.Span;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;

@RestController
@RequestMapping("/api/my-objects")
public class MyController {


    private final MyService service;
    private final ExampleService exampleService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MyController(MyService service, ExampleService exampleService, KafkaTemplate<String, String> kafkaTemplate) {
        this.service = service;
        this.kafkaTemplate = kafkaTemplate;
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


    @PostMapping("/publish")
    public Mono<Void> sendMessage(@RequestBody String message) {
        ProducerRecord producerRecord = new ProducerRecord<>("topic_2", message);

        /*Transaction transaction = ElasticApm.currentTransaction();

        Span span = ElasticApm.currentSpan()
                .startSpan("external", "kafka", null)
                .setName("DAVID").setServiceTarget("kafka","gcp-elastic-apm-spring-boot-integration");

        try (final Scope scope = transaction.activate()) {
            span.injectTraceHeaders((name, value) -> producerRecord.headers().add(name,value.getBytes()));
            return Mono.fromRunnable(() -> {
                kafkaTemplate.send(producerRecord);
            });
        } catch (Exception e) {
            span.captureException(e);
            throw e;
        } finally {
            span.end();
        }*/
        return Mono.fromRunnable(() -> {
            kafkaTemplate.send(producerRecord);
        });


    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        exampleService.processInBackground();
    }


}
