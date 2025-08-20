package com.kt.api_analytics_svc.service;

import com.kt.api_analytics_svc.dto.*;
import com.kt.api_analytics_svc.entity.MessageDashboardData;
import com.kt.api_analytics_svc.repository.MessageDashboardDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public StatusMonthlyCountResponse getStatusCounts(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime end   = start.plusMonths(1);

        List<MessageDashboardDataRepository.StatusCount> rows =
                messageDashboardDataRepository.countByStatusBetween(start, end);

        long delivered = 0L;
        long failed = 0L;

        for (var r : rows) {
            String s = r.getStatus();
            if ("delivered".equalsIgnoreCase(s)) delivered = r.getCnt();
            else if ("failed".equalsIgnoreCase(s)) failed = r.getCnt();
        }
        return new StatusMonthlyCountResponse(year, month, delivered, failed);
    }

    public List<PhoneCountResponse> getTopPhoneNums(String userEmail, int limit) {
        return messageDashboardDataRepository.countTopPhoneNumsByUser(userEmail, PageRequest.of(0, limit))
                .stream()
                .map(r -> new PhoneCountResponse(r.getPhoneNum(), r.getCnt()))
                .toList();
    }
}
