package org.gradle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import org.apache.activemq.broker.Connector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class KafkaConsumerTest {

	
	@Test
	public void testConsumer() {
		
		/*Properties props = new Properties();
	    props.put("metadata.broker.list", "localhost:9092");
	    props.put("serializer.class", "kafka.serializer.StringEncoder");
	    //props.put("partitioner.class", "example.producer.SimplePartitioner");
	    props.put("request.required.acks", "1");*/
	    
	    Properties consumerProps = new Properties();
	    consumerProps.put("metadata.broker.list", "10.10.120.142:2181");
	    consumerProps.put("serializer.class", "kafka.serializer.StringEncoder");
        consumerProps.put("bootstrap.servers", "10.10.120.142:2181");
        //consumerProps.put("group.id", "group0");
       // consumerProps.put("client.id", "consumer0");
        consumerProps.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //consumerProps.put("auto.offset.reset", "earliest");  // to make sure the consumer starts from the beginning of the topic
        consumerProps.put("partition.assignment.strategy", "range");
        //consumerProps.put("zookeeper.connect","localhost:2181");
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "consumer-tutorial");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("partition.assignment.strategy", "range");
        props.put("client.id", "0");
        props.put("metadata.broker.list", "localhost:9092");
        //props.put("auto.offset.reset", "largest");
        props.put("zookeeper.connect","localhost:2181");
        props.put("enable.auto.commit", "false");
       // props.put("auto.offset.reset", "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        ConsumerConnector  connector=Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        //KafkaConsumer<String, String> consumerConig=new KafkaConsumer<String, String>(consumerProps);
        consumer.subscribe("Uppi");
        Map<String,Integer> topicMap = new HashMap<String, Integer>();
        topicMap.put("Uppi",1);
        Map<String, List<KafkaStream<byte[],byte[]>>> k =connector.createMessageStreams(topicMap);
        for(KafkaStream<byte[],byte[]>ks:k.get("Uppi")) {
        	scala.collection.Iterator<MessageAndMetadata<byte[], byte[]>> itr =ks.toList().iterator();
        	while(itr.hasNext()) {
        		MessageAndMetadata<byte[], byte[]> mam=itr.next();
        		System.out.println(new String(mam.key())+">>> "+new String(mam.message()));
        	}
        	
        }
        /*try {
	        while(true) {
	        	Map<String,ConsumerRecords<String, String>>mapcsr=consumer.poll(1000);
	        	ConsumerRecords<String, String> cs=mapcsr.get("Uppi");
	        	for(ConsumerRecord<String, String> r:cs.records(1)) {
	        		
						System.out.println("Key="+r.key()+" Value="+r.value());
					
	        	}
	        }
        }catch (Exception e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}*/
        
	}
}
