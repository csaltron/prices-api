package com.company.prices.infrastructure.adapter.rest;

import com.company.prices.application.dto.PriceResponseDto;
import com.company.prices.application.mapper.PriceMapper;
import com.company.prices.application.service.PriceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {
    
    private final PriceService priceService;
    private final PriceMapper priceMapper;
    
    public PriceController(PriceService priceService, PriceMapper priceMapper) {
        this.priceService = priceService;
        this.priceMapper = priceMapper;
    }
    
    @GetMapping
    public ResponseEntity<PriceResponseDto> getApplicablePrice(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime applicationDate,
            @RequestParam @NotNull Long productId,
            @RequestParam @NotNull Long brandId) {
        
        var price = priceService.findApplicablePrice(applicationDate, productId, brandId);
        var response = priceMapper.toResponseDto(price);
        
        return ResponseEntity.ok(response);
    }
}
