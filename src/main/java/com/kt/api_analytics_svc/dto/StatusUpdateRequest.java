package com.kt.api_analytics_svc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "메시지 상태 업데이트 요청")
public class StatusUpdateRequest {
    @Schema(description = "업데이트할 상태", example = "delivered", allowableValues = {"delivered", "failed", "pending"}, required = true)
    private String status;
    
    @Schema(description = "공급업체 SID", example = "SMXXXXXXXXXXXXXXXXXXXXXXXXXXXX", required = true)
    private String providerSid;

    public StatusUpdateRequest(String status, String providerSid) {
        this.status = status;
        this.providerSid = providerSid;
    }
}
