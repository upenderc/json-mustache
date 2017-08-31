package org.gradle;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.common.TopicExistsException;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerConnector;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import kafka.serializer.Decoder;

import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class KafkaIntTest {

	private static final String TOPIC_NAME = "Uppi";
	static KafkaLocal kafkaLocal;
	static Properties kafkaProperties;
	 
	@BeforeClass
	public static void startKafka(){
		 kafkaProperties = new Properties();
		Properties zkProperties = new Properties();
		
		try {
			Resource server = new ClassPathResource("server.properties");
			Resource zookeeper = new ClassPathResource("zookeeper.properties");
			//load properties
			//kafkaProperties.load(Class.class.getResourceAsStream("server.properties"));
			//zkProperties.load(Class.class.getResourceAsStream("zookeeper.properties"));
			kafkaProperties.load(server.getInputStream());
			zkProperties.load(zookeeper.getInputStream());
			//start kafka
			kafkaLocal = new KafkaLocal(kafkaProperties, zkProperties);
			Thread.sleep(5000);
		} catch (Exception e){
			e.printStackTrace(System.out);
			//fail("Error running local Kafka broker");
			e.printStackTrace(System.out);
		}
		
		//do other things
	}
	
	@AfterClass
	public static void shutdown() {
		kafkaLocal.stop();
	}
	@Test
	public void testKafka() throws Exception {
		ZkClient zkClient = new ZkClient("localhost:2181", 
		        50000, 
		        50000); 
		        
		createTopic(1, TOPIC_NAME, zkClient);
	Thread t =new Thread(new Runnable() {
		
		@Override
		public void run() {
		 
		Properties consumerProps = new Properties();
        consumerProps.setProperty("bootstrap.servers", "localhost:9092");
        consumerProps.setProperty("group.id", "group0");
        consumerProps.setProperty("client.id", "consumer0");
        consumerProps.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        //consumerProps.put("auto.offset.reset", "earliest");  // to make sure the consumer starts from the beginning of the topic
        consumerProps.put("partition.assignment.strategy", "range");
        consumerProps.put("zookeeper.connect","localhost:2181");
        kafka.javaapi.consumer.ConsumerConnector connector = Consumer.createJavaConsumerConnector(new ConsumerConfig(consumerProps));
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(TOPIC_NAME, 5);

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = connector
				.createMessageStreams(topicCountMap);

		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(TOPIC_NAME
				);
		for(KafkaStream<byte[], byte[]> ks:streams) {
			ConsumerIterator<byte[], byte[]> ci = ks.iterator();
			while(ci.hasNext()) {
				MessageAndMetadata<byte[] , byte[]> mm=ci.next();
				System.out.println("Key ="+new String(mm.key())+" value ="+new String(mm.message()));
			}
		}
		}});
	t.start();
		/*Properties producerProps = new Properties();
        producerProps.setProperty("bootstrap.servers", "localhost:9092");
        producerProps.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        producerProps.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");*/
        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(kafkaProperties);
        ProducerRecord<String, byte[]> data = new ProducerRecord<>(TOPIC_NAME, "42", "test-message".getBytes());
        producer.send(data);
        producer.close();
        System.out.println("Message Published");
                //consumer.subscribe(TOPIC_NAME);
       /* Map<String,ConsumerRecords<String, byte[]>> records = consumer.poll(500000);
        System.out.println(records);
        //assertEquals(1, records.keySet().size());
        for(String key :records.keySet()) {
        ConsumerRecords<String,byte[]> record = records.get(key);
           for (ConsumerRecord< String, byte[]> r: record.records(1))
        	   System.out.printf("offset = %d, key = %s, value = %s", r.offset(), r.key(), r.value());
        }*/
        //Thread.sleep(60000);
        zkClient.close();
        
        
	}
	
	public void createTopic(int numPartitions, String topicName,ZkClient zkClient) { 
	    if (!AdminUtils.topicExists(zkClient, topicName)) { 
	      try { 
	        Integer replFactor = 1;
	        AdminUtils.createTopic(zkClient, topicName, numPartitions, replFactor, new Properties()); 
	        System.out.println(MessageFormat.format("Topic created. name: {0}, partitions: {1}, replFactor: {2}", topicName, 
	            numPartitions, replFactor)); 
	      } catch (TopicExistsException ignore) { 
	        System.out.println("Topic exists. name: "+topicName); 
	      } 
	    } else
	    {
	    	System.out.println("topic already is created");
	    }
	  } 
}
