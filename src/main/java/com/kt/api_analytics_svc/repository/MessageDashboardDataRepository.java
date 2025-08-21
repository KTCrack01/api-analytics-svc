package com.kt.api_analytics_svc.repository;

import com.kt.api_analytics_svc.entity.MessageDashboardData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageDashboardDataRepository extends JpaRepository<MessageDashboardData, Long> {
    List<MessageDashboardData> findByUserEmailOrderBySendAtDesc(String userEmail);
    Optional<MessageDashboardData> findByProviderSid(String providerSid);

    interface MonthCount {
        Integer getMonth();
        Long getCount();
    }
    interface StatusCount {
        String getStatus();
        Long getCnt();
    }

    interface PhoneCount {
        String getPhoneNum();
        Long getCnt();
    }

    // 1) 월별 delivered (대소문자 무시 + 사용자 스코프)
    @Query(value = """
        select cast(extract(month from (m.send_at AT TIME ZONE :tz)) as int) as month,
               count(*) as count
        from message_dashboard_data m
        where m.user_email = :userEmail
          and lower(m.status) = 'delivered'      -- ★ case-insensitive
          and m.send_at >= :from and m.send_at < :to
        group by 1
        order by 1
        """, nativeQuery = true)
    List<MonthCount> countByMonth(@Param("userEmail") String userEmail,
                                  @Param("from") Instant from,
                                  @Param("to") Instant to,
                                  @Param("tz") String tz);

    // 2) 상태별 집계 (대소문자 통합 + 사용자 스코프 동일하게)
    @Query(value = """
        select lower(m.status) as status,        -- ★ case 통합
               count(*)        as cnt
        from message_dashboard_data m
        where m.user_email = :userEmail         -- ★ 스코프 일치
          and m.send_at >= :from and m.send_at < :to
        group by lower(m.status)
        """, nativeQuery = true)
    List<StatusCount> countByStatusBetween(@Param("userEmail") String userEmail,
                                           @Param("from") Instant from,
                                           @Param("to") Instant to);

    @Query("""
        select m.phoneNum as phoneNum, count(m) as cnt
        from MessageDashboardData m
        where m.userEmail = :userEmail
        group by m.phoneNum
        order by cnt desc
    """)
    List<PhoneCount> countTopPhoneNumsByUser(@Param("userEmail") String userEmail, Pageable pageable);
}
