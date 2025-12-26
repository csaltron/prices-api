package com.company.prices.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceRequestDto {

    @Schema(description = "Fecha de aplicación del precio", example = "2020-06-14T10:00:00", required = true)
    @NotNull(message = "Application date is required")
    private LocalDateTime applicationDate;

    @Schema(description = "Identificador del producto", example = "35455", required = true)
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema(description = "Identificador de la marca", example = "1", required = true)
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
