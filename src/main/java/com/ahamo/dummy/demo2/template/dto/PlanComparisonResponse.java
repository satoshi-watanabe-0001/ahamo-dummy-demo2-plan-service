package com.ahamo.dummy.demo2.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanComparisonResponse {
    private List<DataPlanResponse> basicPlans;
    private List<PlanOptionResponse> availableOptions;
    private ComparisonMatrix comparisonMatrix;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComparisonMatrix {
        private List<String> features;
        private List<PlanFeatureComparison> planComparisons;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanFeatureComparison {
        private String planId;
        private String planName;
        private List<String> featureValues;
    }
}
