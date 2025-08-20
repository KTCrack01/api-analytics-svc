package com.kt.api_analytics_svc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_dashboard_data")
@Getter
public class MessageDashboardData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_dashboard_data_id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "status")
    private String status;

    @Column(name = "provider_sid")
    private String providerSid;

    protected MessageDashboardData(){
    }

    public void updateStatus(String status) {
        this.status = status;
    }
    @Builder
    public MessageDashboardData(String userEmail, String phoneNum, LocalDateTime sendAt, String status, String providerSid) {
        this.userEmail = userEmail;
        this.phoneNum = phoneNum;
        this.sendAt = sendAt;
        this.status = status;
        this.providerSid = getProviderSid();
    }
}
