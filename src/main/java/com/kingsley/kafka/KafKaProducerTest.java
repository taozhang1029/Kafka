package com.kingsley.kafka;

import com.kingsley.kafka.config.KafkaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * KafKa生产者程序
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KafkaConfiguration.class)
public class KafKaProducerTest {

    @Autowired
    private Properties producerProp;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        // 创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProp);
        // 发送消息
        for (int i = 0; i < 100; i++) {
            // 构建一条消息
            ProducerRecord<String, String> message = new ProducerRecord<>(KafkaConfiguration.TOPIC, null, String.format("第%d条消息", i + 1));
            // 一、发送消息
            // Future<RecordMetadata> future = producer.send(message);
            // RecordMetadata metadata = future.get();
            // log.info(String.format("第%d条消息发送成功", i + 1));

            // 二、异步回调方式发送消息
            producer.send(message, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        // 发送失败
                        log.error(e.getMessage());
                        log.error(Arrays.toString(e.getStackTrace()));
                    } else {
                        // 发送成功
                        // 主题
                        String topic = recordMetadata.topic();
                        // 分区
                        int partition = recordMetadata.partition();
                        // 偏移量
                        long offset = recordMetadata.offset();
                        // 时间戳
                        long timestamp = recordMetadata.timestamp();
                        String s = String.format("主题: %s, 分区: %s, 偏移量: %s, 时间戳: %s", topic, partition, offset, timestamp);
                        log.info(s);
                    }
                }
            });
        }

        // 关闭生产者
        producer.close();
    }
}
