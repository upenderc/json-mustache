package org.gradle;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class KafkaProducerTest {

@Test
public void testProducer() {
	
	
    Properties props = new Properties();
    props.put("metadata.broker.list", "10.10.120.142:9092");
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    //props.put("partitioner.class", "example.producer.SimplePartitioner");
    props.put("request.required.acks", "1");

    ProducerConfig config = new ProducerConfig(props);

    Producer<String, String> producer = new Producer<String, String>(config);
    KeyedMessage<String, String> data = new KeyedMessage<String, String>("NewMemberRegistrationDPCDev", "1","sample message from JUNIT");
    producer.send(data);
    producer.close();
	}

}
