package com.joy.kafka.meetingassistant.meeting.controller;

import com.joy.kafka.meetingassistant.meeting.dto.MeetingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("add")
    public String add(@RequestParam("title") String title){
        MeetingDTO meeting = new MeetingDTO();
        meeting.setTitle(title);
        kafkaTemplate.send("meet", meeting.toString());
        return "Add meeting success!";
    }
}
