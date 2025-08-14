package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.entity.DataPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DataPlanRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DataPlanRepository dataPlanRepository;

    @Test
    void findByIsActiveTrue_ShouldReturnOnlyActiveDataPlans() {
        DataPlan activePlan = DataPlan.builder()
                .id("active-plan")
                .name("Active Plan")
                .dataAmount("30GB")
                .monthlyPrice(new BigDecimal("2970.00"))
                .description("Active plan")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        DataPlan inactivePlan = DataPlan.builder()
                .id("inactive-plan")
                .name("Inactive Plan")
                .dataAmount("20GB")
                .monthlyPrice(new BigDecimal("2000.00"))
                .description("Inactive plan")
                .isActive(false)
                .createdAt(LocalDateTime.now())
                .build();

        entityManager.persist(activePlan);
        entityManager.persist(inactivePlan);
        entityManager.flush();

        List<DataPlan> activeDataPlans = dataPlanRepository.findByIsActiveTrue();

        assertThat(activeDataPlans).hasSize(3);
        assertThat(activeDataPlans).extracting(DataPlan::getId)
                .contains("active-plan", "30gb-plan", "110gb-plan");
        assertThat(activeDataPlans).allMatch(DataPlan::getIsActive);
    }
}
