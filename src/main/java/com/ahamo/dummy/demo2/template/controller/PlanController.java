package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.dto.*;
import com.ahamo.dummy.demo2.template.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PlanController {
    
    private final PlanService planService;
    
    @GetMapping("/api/v1/plans")
    public ResponseEntity<Map<String, Object>> getPlans(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        
        log.info("料金プラン一覧API呼び出し: page={}, limit={}", page, limit);
        
        Page<DataPlanResponse> plans = planService.getPlans(page, limit);
        
        Map<String, Object> response = Map.of(
            "plans", plans.getContent(),
            "total", plans.getTotalElements(),
            "page", page,
            "limit", limit
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/v1/plans/{id}")
    public ResponseEntity<DataPlanResponse> getPlanById(@PathVariable String id) {
        log.info("料金プラン詳細API呼び出し: id={}", id);
        
        DataPlanResponse plan = planService.getPlanById(id);
        
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(plan);
    }
    
    @GetMapping("/api/v1/plans/comparison")
    public ResponseEntity<PlanComparisonResponse> getPlanComparison() {
        log.info("料金プラン比較API呼び出し");
        
        PlanComparisonResponse comparison = planService.getPlanComparison();
        return ResponseEntity.ok(comparison);
    }
    
    @GetMapping("/api/v1/plans/options")
    public ResponseEntity<List<PlanOptionResponse>> getPlanOptions() {
        log.info("プランオプション一覧API呼び出し");
        
        List<PlanOptionResponse> options = planService.getPlanOptions();
        return ResponseEntity.ok(options);
    }
    
    @GetMapping("/api/v1/smartphones/{deviceId}/options")
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
