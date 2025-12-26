package com.company.prices.application.mapper;

import com.company.prices.application.dto.PriceResponseDto;

import com.company.prices.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    
    public PriceResponseDto toResponseDto(Price price) {
        return PriceResponseDto.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }
}
