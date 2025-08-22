package com.kt.api_analytics_svc.controller;


import com.kt.api_analytics_svc.dto.*;
import com.kt.api_analytics_svc.service.MessageDashboardDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard/data")
@RequiredArgsConstructor
@Tag(name = "메시지 대시보드 데이터", description = "메시지 대시보드 데이터 관리 API")
public class MessageDashboardDataController {

    private final MessageDashboardDataService messageDashboardDataService;

    @PostMapping
    @Operation(summary = "메시지 대시보드 데이터 생성", description = "새로운 메시지 대시보드 데이터를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "데이터 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    public ResponseEntity<?> create(
            @RequestBody MessageDashboardDataCreateRequest request
    ) {
        messageDashboardDataService.createData(request);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/status")
    @Operation(summary = "메시지 상태 업데이트", description = "특정 메시지의 상태를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상태 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "메시지를 찾을 수 없음")
    })
    public ResponseEntity<?> updateStatus(@RequestBody StatusUpdateRequest request){
        messageDashboardDataService.updateStatus(
                request.getProviderSid(),
                request.getStatus()
        );
        return ResponseEntity.ok().build();

    }

    @GetMapping("/monthly-counts")
    @Operation(summary = "월별 메시지 수 조회", description = "특정 사용자의 연도별 월별 메시지 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    public MonthlyCountResponse getMonthlyCounts(
            @Parameter(description = "사용자 이메일", required = true, example = "user@kt.com")
            @RequestParam String userEmail,
            @Parameter(description = "조회할 연도", required = true, example = "2024")
            @RequestParam int year
    ) {
        return messageDashboardDataService.getMonthlyCounts(userEmail, year);
    }

    @GetMapping("/status-monthly-counts")
    @Operation(summary = "월별 상태별 메시지 수 조회", description = "특정 사용자의 특정 월의 상태별 메시지 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    public StatusMonthlyCountResponse getStatusMonthlyCounts(
            @Parameter(description = "사용자 이메일", required = true, example = "user@kt.com")
            @RequestParam String userEmail,
            @Parameter(description = "조회할 연도", required = true, example = "2024")
            @RequestParam int year,
            @Parameter(description = "조회할 월", required = true, example = "12")
            @RequestParam int month
    ) {
        return messageDashboardDataService.getStatusCounts(userEmail, year, month);
    }


    @GetMapping("/phone-num-ranking")
    @Operation(summary = "전화번호별 메시지 수 랭킹 조회", description = "특정 사용자의 전화번호별 메시지 수 랭킹을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    public List<PhoneCountResponse> getPhoneNumRanking(
            @Parameter(description = "사용자 이메일", required = true, example = "user@kt.com")
            @RequestParam String userEmail,
            @Parameter(description = "조회할 랭킹 개수", required = false, example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        return messageDashboardDataService.getTopPhoneNums(userEmail, limit);
    }

}
