package com.kt.api_analytics_svc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "메시지 대시보드 데이터 생성 요청")
public class MessageDashboardDataCreateRequest {
    @Schema(description = "사용자 이메일", example = "user@kt.com", required = true)
    private String userEmail;
    
    @Schema(description = "전화번호", example = "010-1234-5678", required = true)
    private String phoneNum;
    
    @Schema(description = "발송 시간", example = "2024-12-30T10:30:00", required = true)
    private String sendAt;
    
    @Schema(description = "메시지 상태", example = "delivered", allowableValues = {"delivered", "failed", "pending"}, required = true)
    private String status;
    
    @Schema(description = "공급업체 SID", example = "SMXXXXXXXXXXXXXXXXXXXXXXXXXXXX", required = true)
    private String providerSid;

    public MessageDashboardDataCreateRequest(String userEmail, String phoneNum, String sendAt, String status, String providerSid) {
        this.userEmail = userEmail;
        this.phoneNum = phoneNum;
        this.sendAt = sendAt;
        this.status = status;
        this.providerSid = providerSid;
    }
}
