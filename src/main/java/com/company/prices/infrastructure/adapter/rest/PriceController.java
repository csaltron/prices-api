package com.company.prices.infrastructure.adapter.rest;

import com.company.prices.application.dto.PriceResponseDto;
import com.company.prices.application.mapper.PriceMapper;
import com.company.prices.application.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Prices", description = "API para consulta de precios aplicables")
@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {
    
    private final PriceService priceService;
    private final PriceMapper priceMapper;
    
    public PriceController(PriceService priceService, PriceMapper priceMapper) {
        this.priceService = priceService;
        this.priceMapper = priceMapper;
    }

    @Operation(
            summary = "Consultar precio aplicable",
            description = "Obtiene el precio aplicable para un producto y marca en una fecha determinada. Si existen múltiples tarifas, se aplica la de mayor prioridad."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Precio encontrado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parámetros inválidos o faltantes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró precio aplicable",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<PriceResponseDto> getApplicablePrice(
            @Parameter(description = "Fecha de aplicación en formato ISO 8601", example = "2020-06-14T10:00:00", required = true)
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate,

            @Parameter(description = "Identificador del producto", example = "35455", required = true)
            @RequestParam @NotNull Long productId,

            @Parameter(description = "Identificador de la marca/cadena", example = "1", required = true)
            @RequestParam @NotNull Long brandId) {

        var price = priceService.findApplicablePrice(applicationDate, productId, brandId);
        var response = priceMapper.toResponseDto(price);

        return ResponseEntity.ok(response);
    }
}
