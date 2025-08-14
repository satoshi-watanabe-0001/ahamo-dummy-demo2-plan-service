package com.ahamo.dummy.demo2.template.service;

import com.ahamo.dummy.demo2.template.dto.SmartphoneOptions;
import com.ahamo.dummy.demo2.template.repository.DataPlanRepository;
import com.ahamo.dummy.demo2.template.repository.OverseaCallingOptionRepository;
import com.ahamo.dummy.demo2.template.repository.VoiceOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanService {
    
    private final DataPlanRepository dataPlanRepository;
    private final VoiceOptionRepository voiceOptionRepository;
    private final OverseaCallingOptionRepository overseaCallingOptionRepository;
    
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
}
