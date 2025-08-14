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
public class SmartphoneOptions {
    private List<DataPlanOption> dataPlans;
    private List<VoiceOption> voiceOptions;
    private List<OverseaCallingOption> overseaCallingOptions;
}
