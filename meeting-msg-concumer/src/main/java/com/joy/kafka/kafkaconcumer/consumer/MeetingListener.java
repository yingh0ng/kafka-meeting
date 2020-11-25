package com.joy.kafka.kafkaconcumer.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.joy.kafka.kafkaconcumer.dto.MeetingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MeetingListener {

    private static final Logger logger = LoggerFactory.getLogger(MeetingListener.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String TOPIC_ADD = "meeting_test";
    private static final String MSG_KEY_PRE = "msg_add_meeting_";

    @KafkaListener(topics = MeetingListener.TOPIC_ADD, groupId = "master")
    public void onMessage(String message) {
        MeetingDTO meetingDTO = null;
        try {
            meetingDTO = JSON.parseObject(message, MeetingDTO.class);
        } catch (JSONException e) {
            return;
        }

        if (stringRedisTemplate.hasKey(MSG_KEY_PRE + meetingDTO.getTitle())) {
            //已被消费
            logger.info("Meeting {} has been created", meetingDTO.getTitle());
            return;
        }
        logger.info("Add meeting: {}", message);

        stringRedisTemplate.opsForValue().set(MSG_KEY_PRE + meetingDTO.getTitle(), "1", 60L, TimeUnit.SECONDS);
    }

    @KafkaListener(topics = MeetingListener.TOPIC_ADD, groupId = "other")
    public void addMeetingMsg(String message) {
        logger.info("Add meeting other action : {}",  message);
    }

    @KafkaListener(topics = "meeting_update_test", groupId = "update")
    public void updateMeetingMsg(String message) {
        logger.info("Update meeting other action : {}",  message);
    }

    @KafkaListener(topics = "meeting_delete_test", groupId = "delete")
    public void deleteMeeting(String message) {
        logger.info("Delete meeting other action : {}",  message);
    }
}
