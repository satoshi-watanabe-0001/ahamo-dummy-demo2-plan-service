package com.ahamo.dummy.demo2.template.service;

import com.ahamo.dummy.demo2.template.dto.*;
import com.ahamo.dummy.demo2.template.entity.DataPlan;
import com.ahamo.dummy.demo2.template.repository.DataPlanRepository;
import com.ahamo.dummy.demo2.template.repository.OverseaCallingOptionRepository;
import com.ahamo.dummy.demo2.template.repository.VoiceOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanService {
    
    private final DataPlanRepository dataPlanRepository;
    private final VoiceOptionRepository voiceOptionRepository;
    private final OverseaCallingOptionRepository overseaCallingOptionRepository;
    
    public Page<DataPlanResponse> getPlans(int page, int limit) {
        log.info("Getting plans: page={}, limit={}", page, limit);
        
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<DataPlan> planPage = dataPlanRepository.findByIsActiveTrue(pageable);
        
        return planPage.map(this::convertToDataPlanResponse);
    }
    
    public DataPlanResponse getPlanById(String id) {
        log.info("Getting plan by id: {}", id);
        
        return dataPlanRepository.findByIdAndIsActiveTrue(id)
                .map(this::convertToDataPlanResponse)
                .orElse(null);
    }
    
    public PlanComparisonResponse getPlanComparison() {
        log.info("Getting plan comparison");
        
        List<DataPlan> plans = dataPlanRepository.findByIsActiveTrue();
        List<DataPlanResponse> planResponses = plans.stream()
                .map(this::convertToDataPlanResponse)
                .toList();
        
        List<PlanOptionResponse> options = getPlanOptions();
        
        PlanComparisonResponse.ComparisonMatrix matrix = PlanComparisonResponse.ComparisonMatrix.builder()
                .features(Arrays.asList("データ容量", "月額料金", "通話", "海外ローミング", "5G対応"))
                .planComparisons(planResponses.stream()
                        .map(plan -> PlanComparisonResponse.PlanFeatureComparison.builder()
                                .planId(plan.getId())
                                .planName(plan.getName())
                                .featureValues(Arrays.asList(
                                        plan.getDataAmount(),
                                        plan.getFormattedPrice(),
                                        "5分無料",
                                        "対応",
                                        "対応"
                                ))
                                .build())
                        .toList())
                .build();
        
        return PlanComparisonResponse.builder()
                .basicPlans(planResponses)
                .availableOptions(options)
                .comparisonMatrix(matrix)
                .build();
    }
    
    public List<PlanOptionResponse> getPlanOptions() {
        log.info("Getting plan options");
        
        var voiceOptions = voiceOptionRepository.findByIsActiveTrue()
                .stream()
                .map(entity -> PlanOptionResponse.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .monthlyPrice(entity.getMonthlyPrice())
                        .category("voice")
                        .isActive(entity.getIsActive())
                        .formattedPrice(String.format("¥%,d", entity.getMonthlyPrice().intValue()))
                        .build())
                .toList();
        
        var overseaOptions = overseaCallingOptionRepository.findByIsActiveTrue()
                .stream()
                .map(entity -> PlanOptionResponse.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .monthlyPrice(entity.getMonthlyPrice())
                        .category("overseas")
                        .isActive(entity.getIsActive())
                        .formattedPrice(String.format("¥%,d", entity.getMonthlyPrice().intValue()))
                        .build())
                .toList();
        
        return List.of(voiceOptions, overseaOptions).stream()
                .flatMap(List::stream)
                .toList();
    }
    
    public SmartphoneOptions getSmartphoneOptions(String deviceId) {
        log.info("Getting smartphone options for deviceId: {}", deviceId);
        
        var dataPlans = dataPlanRepository.findByIsActiveTrue()
                .stream()
                .map(entity -> entity.toDto())
                .toList();
        
        var voiceOptions = voiceOptionRepository.findByIsActiveTrue()
                .stream()
                .map(entity -> entity.toDto())
                .toList();
        
        var overseaCallingOptions = overseaCallingOptionRepository.findByIsActiveTrue()
                .stream()
                .map(entity -> entity.toDto())
                .toList();
        
        return SmartphoneOptions.builder()
                .dataPlans(dataPlans)
                .voiceOptions(voiceOptions)
                .overseaCallingOptions(overseaCallingOptions)
                .build();
    }
    
    private DataPlanResponse convertToDataPlanResponse(DataPlan plan) {
        return DataPlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .dataAmount(plan.getDataAmount())
                .monthlyPrice(plan.getMonthlyPrice())
                .description(plan.getDescription())
                .isActive(plan.getIsActive())
                .createdAt(plan.getCreatedAt())
                .formattedPrice(String.format("¥%,d", plan.getMonthlyPrice().intValue()))
                .build();
    }
}
