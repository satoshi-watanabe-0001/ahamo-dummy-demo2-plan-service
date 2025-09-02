package com.ahamo.dummy.demo2.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanOptionResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal monthlyPrice;
    private String category;
    private Boolean isActive;
    private String formattedPrice;
}
