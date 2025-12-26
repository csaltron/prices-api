package com.company.prices.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Respuesta con los datos del precio aplicable")
public class PriceResponseDto {

    @Schema(description = "Identificador del producto", example = "35455")
    private Long productId;

    @Schema(description = "Identificador de la marca", example = "1")
    private Long brandId;

    @Schema(description = "Identificador de la tarifa de precios aplicable", example = "1")
    private Integer priceList;

    @Schema(description = "Fecha de inicio de aplicación del precio", example = "2020-06-14T00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Fecha de fin de aplicación del precio", example = "2020-12-31T23:59:59")
    private LocalDateTime endDate;

    @Schema(description = "Precio final de venta", example = "35.50")
    private BigDecimal price;

    @Schema(description = "Moneda del precio", example = "EUR")
    private String currency;

}
