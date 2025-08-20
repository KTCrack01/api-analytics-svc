package com.kt.api_analytics_svc.service;

import com.kt.api_analytics_svc.dto.MessageDashboardDataCreateRequest;
import com.kt.api_analytics_svc.dto.MonthlyCountResponse;
import com.kt.api_analytics_svc.dto.StatusUpdateRequest;
import com.kt.api_analytics_svc.entity.MessageDashboardData;
import com.kt.api_analytics_svc.repository.MessageDashboardDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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

    public MonthlyCountResponse getMonthlyCounts(String userEmail, int year) {
        var rows = messageDashboardDataRepository.countByMonth(userEmail, year);

        Long[] counts = new Long[12];
        Arrays.fill(counts, 0L);

        for (var row : rows) {
            int month = row.getMonth(); // 1~12
            counts[month - 1] = row.getCount();
        }

        return new MonthlyCountResponse(userEmail, year, Arrays.asList(counts));
    }

}
