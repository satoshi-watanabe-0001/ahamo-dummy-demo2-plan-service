package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.config.SecurityConfig;
import com.ahamo.dummy.demo2.template.dto.DataPlanOption;
import com.ahamo.dummy.demo2.template.dto.OverseaCallingOption;
import com.ahamo.dummy.demo2.template.dto.SmartphoneOptions;
import com.ahamo.dummy.demo2.template.dto.VoiceOption;
import com.ahamo.dummy.demo2.template.service.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanController.class)
@Import(SecurityConfig.class)
class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanService planService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getSmartphoneOptions_ShouldReturnSuccessResponse() throws Exception {
        DataPlanOption dataPlan = DataPlanOption.builder()
                .id("30gb-plan")
                .title("30GB")
                .subtitle("30GB")
                .price("¥2,970")
                .description("データ通信量")
                .build();

        VoiceOption voiceOption = VoiceOption.builder()
                .id("voice-none")
                .title("申し込まない")
                .description("")
                .price("無料")
                .build();

        OverseaCallingOption overseaOption = OverseaCallingOption.builder()
                .id("overseas-none")
                .title("申し込まない")
                .description("1分/回無料")
                .price("無料")
                .build();

        SmartphoneOptions options = SmartphoneOptions.builder()
                .dataPlans(List.of(dataPlan))
                .voiceOptions(List.of(voiceOption))
                .overseaCallingOptions(List.of(overseaOption))
                .build();

        when(planService.getSmartphoneOptions(anyString())).thenReturn(options);

        mockMvc.perform(get("/api/v1/smartphones/test-device/options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.dataPlans").isArray())
                .andExpect(jsonPath("$.data.dataPlans[0].id").value("30gb-plan"))
                .andExpect(jsonPath("$.data.dataPlans[0].title").value("30GB"))
                .andExpect(jsonPath("$.data.dataPlans[0].price").value("¥2,970"))
                .andExpect(jsonPath("$.data.voiceOptions").isArray())
                .andExpect(jsonPath("$.data.voiceOptions[0].id").value("voice-none"))
                .andExpect(jsonPath("$.data.voiceOptions[0].title").value("申し込まない"))
                .andExpect(jsonPath("$.data.overseaCallingOptions").isArray())
                .andExpect(jsonPath("$.data.overseaCallingOptions[0].id").value("overseas-none"))
                .andExpect(jsonPath("$.meta.timestamp").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0"));
    }

    @Test
    void getSmartphoneOptions_WhenServiceThrowsException_ShouldReturnErrorResponse() throws Exception {
        when(planService.getSmartphoneOptions(anyString())).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/v1/smartphones/test-device/options"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Internal server error"))
                .andExpect(jsonPath("$.meta.timestamp").exists())
                .andExpect(jsonPath("$.meta.version").value("1.0"));
    }
}
