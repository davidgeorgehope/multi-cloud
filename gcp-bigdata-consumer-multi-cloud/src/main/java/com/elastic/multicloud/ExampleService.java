package com.elastic.multicloud;


import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
class ExampleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleService.class);

    private final KafkaMessageProcessor kafkaMessageProcessor;

    private final KafkaConsumer kafkaConsumer;
    private final TaskExecutor taskExecutor;
    public ExampleService(KafkaMessageProcessor kafkaMessageProcessor,
                        KafkaConsumer kafkaConsumer, TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
        this.kafkaMessageProcessor = kafkaMessageProcessor;
        this.kafkaConsumer=kafkaConsumer;
    }


    public void processInBackground() {
        taskExecutor.execute(() -> {
            Flux<String> flx = kafkaConsumer.subscribe();
            flx.subscribe(s -> {
                // handle each emitted item
                LOGGER.info("Received item: " + s);
            }, error -> {
                // handle error
                LOGGER.info("Error: " + error.getMessage());
            }, () -> {
                // handle completion
                LOGGER.info("Completed");
            });
        });
    }
}
