package com.yuldashov.summarizationserver.config;

import com.yuldashov.summarizationserver.dto.summarization.SummarizationRequest;
import com.yuldashov.summarizationserver.dto.summarization.SummarizationResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${bootstrap.server}")
    private String bootstrapHost;
    @Value("${group.id}")
    private String groupId;
    @Value("${trusted.packages}")
    private String trustedPackages;
    @Value("${response.type}")
    private String responseType;


    @Bean
    public ProducerFactory<String, SummarizationResponse> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapHost);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ConsumerFactory<String, SummarizationRequest> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapHost);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE,
                responseType);
        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 20);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, SummarizationResponse> kafkaProducerTemplate(
            ProducerFactory<String, SummarizationResponse> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SummarizationRequest> kafkaListenerContainerFactory (
            ConsumerFactory<String, SummarizationRequest> consumerFactory,
            KafkaTemplate<String, SummarizationResponse> kafkaTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, SummarizationRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }
}
