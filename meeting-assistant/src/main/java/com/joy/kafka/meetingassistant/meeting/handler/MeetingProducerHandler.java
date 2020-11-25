package com.joy.kafka.meetingassistant.meeting.handler;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
public class MeetingProducerHandler implements ProducerListener<String, Object> {

    private static final Logger logger = LoggerFactory.getLogger(MeetingProducerHandler.class);

    @Override
    public void onSuccess(ProducerRecord<String, Object> producerRecord, RecordMetadata recordMetadata) {
        logger.info("Send msg success: {}", producerRecord.value());
    }

    @Override
    public void onError(ProducerRecord<String, Object> producerRecord, Exception exception) {
        logger.info("Send msg error: {}", producerRecord.value());
    }
}
