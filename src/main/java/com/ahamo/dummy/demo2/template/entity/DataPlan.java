package com.ahamo.dummy.demo2.template.entity;

import com.ahamo.dummy.demo2.template.dto.DataPlanOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_plans", schema = "plan_schema")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPlan {
    @Id
    private String id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "data_amount", nullable = false, length = 50)
    private String dataAmount;
    
    @Column(name = "monthly_price", nullable = false, precision = 8, scale = 2)
    private BigDecimal monthlyPrice;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public DataPlanOption toDto() {
        return DataPlanOption.builder()
                .id(this.id)
                .title(this.name)
                .subtitle(this.dataAmount)
                .price(String.format("¥%,d", this.monthlyPrice.intValue()))
                .description(this.description)
                .build();
    }
}
