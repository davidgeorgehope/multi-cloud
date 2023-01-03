package com.elastic.multicloud;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.Map;

@Setter
@Configuration
@ConfigurationProperties(prefix = "elastic.kafka")
public class KafkaConfig {

    private String securityProtocol;
    private String saslJaaSConfig;
    private String saslMechanism;
    private String clientDnsLookup;
    private String sessionTimeoutMs;

    private String acKs;

    private String bootstrapServers;

    private String topicName;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public ReceiverOptions<String, String> kafkaReceiverOptions(KafkaProperties kafkaProperties) {
        LOGGER.info(bootstrapServers);

        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        props.put("security.protocol",securityProtocol);
        props.put("sasl.jaas.config",saslJaaSConfig);
        props.put("sasl.mechanism",saslMechanism);
        props.put("client.dns.lookup",clientDnsLookup);
        props.put("session.timeout.ms",sessionTimeoutMs);
        props.put("acks",acKs);
        props.put("bootstrap.servers",bootstrapServers);

        ReceiverOptions<String, String> basicReceiverOptions = ReceiverOptions.create(props);
        return basicReceiverOptions.subscription(Collections.singletonList(topicName));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate(final ReceiverOptions<String, String> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }

    @PostConstruct
    public void init() {
        LOGGER.info(bootstrapServers);
    }
}