package com.kt.api_analytics_svc.repository;

import com.kt.api_analytics_svc.entity.MessageDashboardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageDashboardDataRepository extends JpaRepository<MessageDashboardData, Long> {
    List<MessageDashboardData> findByUserEmailOrderBySendAtDesc(String userEmail);
    Optional<MessageDashboardData> findByProviderSid(String providerSid);

    interface MonthCount {
        Integer getMonth(); // 1~12
        Long getCount();
    }

    @Query("""
        select month(m.sendAt) as month, count(m) as count
        from MessageDashboardData m
        where m.userEmail = :userEmail and year(m.sendAt) = :year
        group by month(m.sendAt)
        order by month(m.sendAt)
    """)
    List<MonthCount> countByMonth(@Param("userEmail") String userEmail, @Param("year") int year);
}
