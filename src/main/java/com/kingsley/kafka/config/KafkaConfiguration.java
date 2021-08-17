package com.kingsley.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * @author: zhangtao552
 * @time: 2021/8/17 14:58
 * @description
 */
@Configuration
@PropertySource(value = "classpath:kafka-config.properties")
public class KafkaConfiguration {

    @Value("${bootstrap.servers}")
    private String servers;

    @Value("${acks}")
    private String acks;

    @Value("${key.serializer}")
    private String keySerializer;

    @Value("${value.serializer}")
    private String valueSerializer;

    @Value("${group.id}")
    private String groupId;

    @Value("${enable.auto.commit}")
    private String autoCommit;

    @Value("${auto.commit.interval.ms}")
    private String autoCommitInterval;

    @Value("${key.deserializer}")
    private String keyDeserializer;

    @Value("${value.deserializer}")
    private String valueDeserializer;

    @Bean
    public Properties producerProp() {
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", servers);
        prop.setProperty("acks", acks);
        prop.setProperty("key.serializer", keySerializer);
        prop.setProperty("value.serializer", valueSerializer);
        return prop;
    }

    @Bean
    public Properties consumerProp() {
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", servers);
        prop.setProperty("group.id", groupId);
        prop.setProperty("enable.auto.commit",autoCommit);
        prop.setProperty("auto.commit.interval.ms",autoCommitInterval);
        prop.setProperty("key.deserializer", keyDeserializer);
        prop.setProperty("value.deserializer", valueDeserializer);
        return prop;
    }
}
