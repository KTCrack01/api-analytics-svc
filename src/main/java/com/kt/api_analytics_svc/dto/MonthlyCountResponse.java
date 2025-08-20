package com.kt.api_analytics_svc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MonthlyCountResponse {
    private String userEmail;
    private int year;
    private List<Long> counts;
}
