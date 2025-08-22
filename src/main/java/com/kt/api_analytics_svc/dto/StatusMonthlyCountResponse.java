package com.kt.api_analytics_svc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "월별 상태별 메시지 수 응답")
public class StatusMonthlyCountResponse {
    @Schema(description = "연도", example = "2024")
    private int year;
    
    @Schema(description = "월 (1~12)", example = "12")
    private int month;
    
    @Schema(description = "전송 성공한 메시지 수", example = "850")
    private long delivered;
    
    @Schema(description = "전송 실패한 메시지 수", example = "15")
    private long failed;
}
