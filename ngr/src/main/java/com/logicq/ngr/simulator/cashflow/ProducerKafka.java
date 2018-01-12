package com.logicq.ngr.simulator.cashflow;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProducerKafka {

	private KafkaProducer<String, byte[]> producer = null;

	private String topicName = null;

	public void sendTokafka(String data) {
		producer = new KafkaProducer<String, byte[]>(PropertyUtils.loadProp("kafkaprop.properties"));
		topicName = PropertyUtils.loadProp("kafkaprop.properties").getProperty("topicName");
		producer.send(new ProducerRecord<String, byte[]>(topicName, data.getBytes()));
		System.out.println(data);
	}
}
