package com.kt.api_analytics_svc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "전화번호별 메시지 수 응답")
public class PhoneCountResponse {
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNum;
    
    @Schema(description = "메시지 수", example = "125")
    private long count;
}
