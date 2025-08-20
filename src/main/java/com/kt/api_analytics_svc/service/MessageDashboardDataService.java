package com.kt.api_analytics_svc.service;

import com.kt.api_analytics_svc.dto.MessageDashboardDataCreateRequest;
import com.kt.api_analytics_svc.dto.StatusUpdateRequest;
import com.kt.api_analytics_svc.entity.MessageDashboardData;
import com.kt.api_analytics_svc.repository.MessageDashboardDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MessageDashboardDataService {
    private final MessageDashboardDataRepository messageDashboardDataRepository;

    @Transactional
    public void createData(MessageDashboardDataCreateRequest request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime sendAt = LocalDateTime.parse(request.getSendAt(), formatter);

        messageDashboardDataRepository.save(
                MessageDashboardData.builder()
                        .userEmail(request.getUserEmail())
                        .phoneNum(request.getPhoneNum())
                        .sendAt(sendAt)
                        .status(request.getStatus())
                        .providerSid(request.getProviderSid())
                        .build()
        );
    }

    @Transactional
    public void updateStatus(String providerSid, String status) {

        messageDashboardDataRepository.findByProviderSid(providerSid)
                .ifPresent(data -> {
                    data.updateStatus(status);
                });
    }
}
