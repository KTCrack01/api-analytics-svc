package com.kt.api_analytics_svc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusMonthlyCountResponse {
    private int year;
    private int month; // 1~12
    private long delivered;
    private long failed;
}
