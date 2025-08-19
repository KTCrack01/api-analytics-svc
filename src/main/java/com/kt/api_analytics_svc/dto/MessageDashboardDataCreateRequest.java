package com.kt.api_analytics_svc.dto;

import lombok.Getter;

@Getter
public class MessageDashboardDataCreateRequest {
    private String userEmail;
    private String phoneNum;
    private String sendAt;
    private String status;

    public MessageDashboardDataCreateRequest(String userEmail, String phoneNum, String sendAt, String status) {
        this.userEmail = userEmail;
        this.phoneNum = phoneNum;
        this.sendAt = sendAt;
        this.status = status;
    }
}
