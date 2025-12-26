package com.company.prices.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceRequestDto {
    
    @NotNull(message = "Application date is required")
    private LocalDateTime applicationDate;
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @NotNull(message = "Brand ID is required")
    private Long brandId;
    
    public PriceRequestDto() {
    }
    
    public PriceRequestDto(LocalDateTime applicationDate, Long productId, Long brandId) {
        this.applicationDate = applicationDate;
        this.productId = productId;
        this.brandId = brandId;
    }

}
