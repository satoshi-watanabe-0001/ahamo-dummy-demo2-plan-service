package com.ahamo.dummy.demo2.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPlanOption {
    private String id;
    private String title;
    private String subtitle;
    private String price;
    private String description;
}
