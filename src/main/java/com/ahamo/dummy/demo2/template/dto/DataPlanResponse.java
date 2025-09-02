package com.ahamo.dummy.demo2.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPlanResponse {
    private String id;
    private String name;
    private String dataAmount;
    private BigDecimal monthlyPrice;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String formattedPrice;
}
