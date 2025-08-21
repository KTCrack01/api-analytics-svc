package com.kt.api_analytics_svc.service;

import com.kt.api_analytics_svc.dto.*;
import com.kt.api_analytics_svc.entity.MessageDashboardData;
import com.kt.api_analytics_svc.repository.MessageDashboardDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        ZoneId zone = ZoneId.of("Asia/Seoul");
        ZonedDateTime startZdt = LocalDate.of(year, 1, 1).atStartOfDay(zone);
        ZonedDateTime endZdt   = startZdt.plusYears(1);
        Instant from = startZdt.toInstant();
        Instant to   = endZdt.toInstant();

        var rows = messageDashboardDataRepository.countByMonth(userEmail, from, to, "Asia/Seoul");

        Long[] counts = new Long[12];
        Arrays.fill(counts, 0L);
        for (var row : rows) counts[row.getMonth() - 1] = row.getCount();

        return new MonthlyCountResponse(userEmail, year, Arrays.asList(counts));
    }

    public StatusMonthlyCountResponse getStatusCounts(String userEmail, int year, int month) {
        ZoneId zone = ZoneId.of("Asia/Seoul");
        ZonedDateTime startZdt = LocalDate.of(year, month, 1).atStartOfDay(zone);
        ZonedDateTime endZdt   = startZdt.plusMonths(1);
        Instant from = startZdt.toInstant();
        Instant to   = endZdt.toInstant();

        var rows = messageDashboardDataRepository.countByStatusBetween(userEmail, from, to);

        long delivered = 0L, failed = 0L;
        for (var r : rows) {
            String s = r.getStatus(); // 이미 lower(...)
            if ("delivered".equals(s)) delivered = r.getCnt();
            else if ("failed".equals(s)) failed = r.getCnt();
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
