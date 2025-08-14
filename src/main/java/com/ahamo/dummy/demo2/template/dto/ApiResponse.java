package com.ahamo.dummy.demo2.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private String message;
    private String status;
    private Meta meta;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private String requestId;
        private LocalDateTime timestamp;
        private String version;
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .status("success")
                .meta(Meta.builder()
                        .timestamp(LocalDateTime.now())
                        .version("1.0")
                        .build())
                .build();
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .message(message)
                .status("error")
                .meta(Meta.builder()
                        .timestamp(LocalDateTime.now())
                        .version("1.0")
                        .build())
                .build();
    }
}
