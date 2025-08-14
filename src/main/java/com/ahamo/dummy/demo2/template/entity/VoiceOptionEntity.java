package com.ahamo.dummy.demo2.template.entity;

import com.ahamo.dummy.demo2.template.dto.VoiceOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "voice_options", schema = "plan_schema")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceOptionEntity {
    @Id
    private String id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "call_minutes", length = 50)
    private String callMinutes;
    
    @Column(name = "monthly_price", nullable = false, precision = 8, scale = 2)
    private BigDecimal monthlyPrice;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public VoiceOption toDto() {
        return VoiceOption.builder()
                .id(this.id)
                .title(this.name)
                .description(this.description)
                .price(this.monthlyPrice.compareTo(BigDecimal.ZERO) == 0 ? 
                       "無料" : String.format("¥%,d", this.monthlyPrice.intValue()))
                .build();
    }
}
