package com.kt.api_analytics_svc.repository;

import com.kt.api_analytics_svc.entity.MessageDashboardData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageDashboardDataRepository extends JpaRepository<MessageDashboardData, Long> {
    List<MessageDashboardData> findByUserEmailOrderBySendAtDesc(String userEmail);
    Optional<MessageDashboardData> findByProviderSid(String providerSid);
}
