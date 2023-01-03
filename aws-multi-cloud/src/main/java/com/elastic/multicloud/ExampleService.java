package com.elastic.multicloud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
class ExampleService {

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
                System.out.println("Received item: " + s);
            }, error -> {
                // handle error
                System.err.println("Error: " + error.getMessage());
            }, () -> {
                // handle completion
                System.out.println("Completed");
            });
        });
    }
}
