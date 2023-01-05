package com.elastic.multicloud;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;
    private final KafkaMessageProcessor kafkaMessageProcessor;

    public KafkaConsumer(final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate,
                           final KafkaMessageProcessor kafkaMessageProcessor) {
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
        this.kafkaMessageProcessor = kafkaMessageProcessor;
    }

    public Flux<String> subscribe() {
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                .doOnNext(consumerRecord ->
                        LOGGER.info("received key={}, value={} from topic={}, offset={}",consumerRecord.key(), consumerRecord.value(), consumerRecord.topic(), consumerRecord.offset()))


                .map(kafkaMessageProcessor::processMessage);
    }

}