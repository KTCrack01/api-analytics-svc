package com.kt.api_analytics_svc.controller;


import com.kt.api_analytics_svc.dto.StatusUpdateRequest;
import com.kt.api_analytics_svc.dto.MessageDashboardDataCreateRequest;
import com.kt.api_analytics_svc.service.MessageDashboardDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard/data")
@RequiredArgsConstructor
public class MessageDashboardDataController {

    private final MessageDashboardDataService messageDashboardDataService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody MessageDashboardDataCreateRequest request
    ) {
        messageDashboardDataService.createData(request);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody StatusUpdateRequest request){
        messageDashboardDataService.updateStatus(
                request.getProviderSid(),
                request.getStatus()
        );
        return ResponseEntity.ok().build();

    }
}
