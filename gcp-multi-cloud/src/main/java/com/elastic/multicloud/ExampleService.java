package com.elastic.multicloud;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
class ExampleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaMessageProcessor kafkaMessageProcessor;

    private final KafkaConsumer kafkaConsumer;
    private final TaskExecutor taskExecutor;
    public ExampleService(KafkaMessageProcessor kafkaMessageProcessor,
                          KafkaConsumer kafkaConsumer, TaskExecutor taskExecutor,KafkaTemplate<String, String> kafkaTemplate) {
        this.taskExecutor = taskExecutor;
        this.kafkaMessageProcessor = kafkaMessageProcessor;
        this.kafkaConsumer=kafkaConsumer;
        this.kafkaTemplate=kafkaTemplate;
    }



    public void processInBackground() {
        taskExecutor.execute(() -> {
            Flux<String> flx = kafkaConsumer.subscribe();
            flx.subscribe(s -> {
                // handle each emitted item
                LOGGER.info("Received item: " + s);

                ProducerRecord producerRecord = new ProducerRecord<>("gcpTopic", s);

                kafkaTemplate.send(producerRecord);



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
