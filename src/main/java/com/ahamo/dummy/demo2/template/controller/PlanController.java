package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.dto.ApiResponse;
import com.ahamo.dummy.demo2.template.dto.SmartphoneOptions;
import com.ahamo.dummy.demo2.template.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/smartphones")
@RequiredArgsConstructor
@Slf4j
public class PlanController {
    
    private final PlanService planService;
    
    @GetMapping("/{deviceId}/options")
    public ResponseEntity<ApiResponse<SmartphoneOptions>> getSmartphoneOptions(
            @PathVariable String deviceId) {
        log.info("Received request for smartphone options with deviceId: {}", deviceId);
        
        try {
            SmartphoneOptions options = planService.getSmartphoneOptions(deviceId);
            return ResponseEntity.ok(ApiResponse.success(options));
        } catch (Exception e) {
            log.error("Error getting smartphone options for deviceId: {}", deviceId, e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Internal server error"));
        }
    }
}
