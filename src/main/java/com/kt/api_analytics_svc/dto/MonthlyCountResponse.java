package com.kt.api_analytics_svc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "월별 메시지 수 응답")
public class MonthlyCountResponse {
    @Schema(description = "사용자 이메일", example = "user@kt.com")
    private String userEmail;
    
    @Schema(description = "조회 연도", example = "2024")
    private int year;
    
    @Schema(description = "월별 메시지 수 (1월~12월)", example = "[120, 150, 180, 200, 175, 160, 190, 210, 185, 195, 205, 220]")
    private List<Long> counts;
}
