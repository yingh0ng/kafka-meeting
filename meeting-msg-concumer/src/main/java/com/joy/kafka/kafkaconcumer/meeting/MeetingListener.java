package com.joy.kafka.kafkaconcumer.meeting;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MeetingListener {

    @KafkaListener(topics = "meet", groupId = "master")
    public void onMessage(String message){
        System.out.println("Add meeting");
        System.out.println(message);
    }

    @KafkaListener(topics = "meet", groupId = "other")
    public void addMeetingMsg(String message){
        System.out.println("Add meeting other action");
        System.out.println(message);
    }
}
