package com.elastic.multicloud;

import lombok.Setter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Setter
@Configuration
@ConfigurationProperties(prefix = "elastic.kafka.producer")
public class KafkaProducerConfig {

    private String securityProtocol;
    private String saslJaaSConfig;
    private String saslMechanism;
    private String clientDnsLookup;
    private String sessionTimeoutMs;


    private String bootstrapServers;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerConfig.class);
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        LOGGER.info(bootstrapServers);

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put("security.protocol",(securityProtocol!=null?securityProtocol:""));
        props.put("sasl.jaas.config",(saslJaaSConfig!=null?saslJaaSConfig:""));
        props.put("sasl.mechanism",(saslMechanism!=null?saslMechanism:""));
        props.put("client.dns.lookup",(clientDnsLookup!=null?clientDnsLookup:""));
        props.put("session.timeout.ms",(sessionTimeoutMs!=null?sessionTimeoutMs:""));
        props.put("bootstrap.servers",(bootstrapServers!=null?bootstrapServers:"localhost:9092"));
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
