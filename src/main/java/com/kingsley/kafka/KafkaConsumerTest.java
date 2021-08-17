package com.kingsley.kafka;

import com.kingsley.kafka.config.KafkaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Kafka消费者测试
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KafkaConfiguration.class)
public class KafkaConsumerTest {

    @Autowired
    private Properties consumerProp;

    @Test
    public void test() {
        // 创建消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProp);
        // 指定要消费的主题
        consumer.subscribe(Collections.singletonList("test"));
        while (true) {
            ConsumerRecords<String, String> messages = consumer.poll(Duration.ofSeconds(3));
            for (ConsumerRecord<String, String> message : messages) {
                log.info("topic: " + message.topic() + ", offset: " + message.offset() + ", key: " + message.key() + ", value: " + message.value());
            }
        }
        // consumer.close();
    }

}
