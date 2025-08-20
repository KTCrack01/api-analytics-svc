package com.kt.api_analytics_svc.repository;

import com.kt.api_analytics_svc.entity.MessageDashboardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageDashboardDataRepository extends JpaRepository<MessageDashboardData, Long> {
    List<MessageDashboardData> findByUserEmailOrderBySendAtDesc(String userEmail);
    Optional<MessageDashboardData> findByProviderSid(String providerSid);

    interface MonthCount {
        Integer getMonth(); // 1~12
        Long getCount();
    }

    interface StatusCount {
        String getStatus();
        Long getCnt();
    }

    @Query("""
        select month(m.sendAt) as month, count(m) as count
        from MessageDashboardData m
        where m.userEmail = :userEmail and year(m.sendAt) = :year
        group by month(m.sendAt)
        order by month(m.sendAt)
    """)
    List<MonthCount> countByMonth(@Param("userEmail") String userEmail, @Param("year") int year);

    @Query("""
        select m.status as status, count(m) as cnt
        from MessageDashboardData m
        where m.sendAt >= :start and m.sendAt < :end
        group by m.status
    """)
    List<StatusCount> countByStatusBetween(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);
}
