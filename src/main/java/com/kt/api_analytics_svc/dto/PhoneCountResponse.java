package com.kt.api_analytics_svc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhoneCountResponse {
    private String phoneNum;
    private long count;
}
