package com.ahamo.dummy.demo2.template.service;

import com.ahamo.dummy.demo2.template.dto.SmartphoneOptions;
import com.ahamo.dummy.demo2.template.entity.DataPlan;
import com.ahamo.dummy.demo2.template.entity.OverseaCallingOptionEntity;
import com.ahamo.dummy.demo2.template.entity.VoiceOptionEntity;
import com.ahamo.dummy.demo2.template.repository.DataPlanRepository;
import com.ahamo.dummy.demo2.template.repository.OverseaCallingOptionRepository;
import com.ahamo.dummy.demo2.template.repository.VoiceOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @Mock
    private DataPlanRepository dataPlanRepository;

    @Mock
    private VoiceOptionRepository voiceOptionRepository;

    @Mock
    private OverseaCallingOptionRepository overseaCallingOptionRepository;

    @InjectMocks
    private PlanService planService;

    private DataPlan dataPlan;
    private VoiceOptionEntity voiceOption;
    private OverseaCallingOptionEntity overseaCallingOption;

    @BeforeEach
    void setUp() {
        dataPlan = DataPlan.builder()
                .id("30gb-plan")
                .name("30GB")
                .dataAmount("30GB")
                .monthlyPrice(new BigDecimal("2970.00"))
                .description("データ通信量")
                .isActive(true)
                .build();

        voiceOption = VoiceOptionEntity.builder()
                .id("voice-none")
                .name("申し込まない")
                .callMinutes("")
                .monthlyPrice(BigDecimal.ZERO)
                .description("")
                .isActive(true)
                .build();

        overseaCallingOption = OverseaCallingOptionEntity.builder()
                .id("overseas-none")
                .name("申し込まない")
                .targetCountries("")
                .monthlyPrice(BigDecimal.ZERO)
                .description("1分/回無料")
                .isActive(true)
                .build();
    }

    @Test
    void getSmartphoneOptions_ShouldReturnAllOptions() {
        when(dataPlanRepository.findByIsActiveTrue()).thenReturn(List.of(dataPlan));
        when(voiceOptionRepository.findByIsActiveTrue()).thenReturn(List.of(voiceOption));
        when(overseaCallingOptionRepository.findByIsActiveTrue()).thenReturn(List.of(overseaCallingOption));

        SmartphoneOptions result = planService.getSmartphoneOptions("test-device-id");

        assertThat(result).isNotNull();
        assertThat(result.getDataPlans()).hasSize(1);
        assertThat(result.getVoiceOptions()).hasSize(1);
        assertThat(result.getOverseaCallingOptions()).hasSize(1);

        assertThat(result.getDataPlans().get(0).getId()).isEqualTo("30gb-plan");
        assertThat(result.getDataPlans().get(0).getTitle()).isEqualTo("30GB");
        assertThat(result.getDataPlans().get(0).getPrice()).isEqualTo("¥2,970");

        assertThat(result.getVoiceOptions().get(0).getId()).isEqualTo("voice-none");
        assertThat(result.getVoiceOptions().get(0).getTitle()).isEqualTo("申し込まない");
        assertThat(result.getVoiceOptions().get(0).getPrice()).isEqualTo("無料");

        assertThat(result.getOverseaCallingOptions().get(0).getId()).isEqualTo("overseas-none");
        assertThat(result.getOverseaCallingOptions().get(0).getTitle()).isEqualTo("申し込まない");
        assertThat(result.getOverseaCallingOptions().get(0).getPrice()).isEqualTo("無料");
    }
}
