package com.joy.kafka.meetingassistant.meeting.controller;

import com.alibaba.fastjson.JSON;
import com.joy.kafka.meetingassistant.meeting.dto.MeetingDTO;
import com.joy.kafka.meetingassistant.meeting.handler.MeetingProducerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private static final String TOPIC_ADD = "meeting_test";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private MeetingProducerHandler meetingMsgHandler;

    private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @GetMapping("add")
    public String add(@RequestParam("title") String title, @RequestParam(name = "part",required = false)Integer part) throws ExecutionException, InterruptedException {
        MeetingDTO meeting = new MeetingDTO();
        meeting.setTitle(title);

        //分区
        if (part == null) {
            part = 0;
        }

        //设置消息发送回调
        kafkaTemplate.setProducerListener(meetingMsgHandler);

        //发送异步消息
        kafkaTemplate.send(TOPIC_ADD, part, title, JSON.toJSONString(meeting));

        //发送同步消息
//        SendResult<String, > sendResult = kafkaTemplate.send(TOPIC_ADD, part, title, meeting.toString()).get();
//        logger.info(sendResulObjectt.toString());

        //测试幂等性
//        for (int i = 0; i < 5; i++) {
//            kafkaTemplate.send(TOPIC_ADD, part, title, JSON.toJSONString(meeting));
//        }

        logger.info("Add meeting: {}", meeting);
        return "Add meeting success!";
    }

    @GetMapping("update")
    public String update(@RequestParam("title") String title){
        MeetingDTO meeting = new MeetingDTO();
        meeting.setTitle(title);
        kafkaTemplate.send("meeting_update_test",meeting.toString());
        logger.info("Update meeting: {}", meeting);
        return "Update meeting success!";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("title") String title){
        MeetingDTO meeting = new MeetingDTO();
        meeting.setTitle(title);
        kafkaTemplate.send("meeting_delete_test",meeting.toString());
        logger.info("Delete meeting: {}", meeting);
        return "Update meeting success!";
    }
}
