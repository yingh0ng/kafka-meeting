package com.joy.kafka.meetingassistant.meeting.dto;

import java.io.Serializable;

public class MeetingDTO {

    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MeetingDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
