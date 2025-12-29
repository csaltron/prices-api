package com.company.prices.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.*;

@Builder
@Getter
public class Price {
    
    private final Long id;
    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;

    private Price(
            Long id,
            Long brandId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer priceList,
            Long productId,
            Integer priority,
            BigDecimal price,
            String currency
    ) {
        this.id = id;
        this.brandId = Objects.requireNonNull(brandId, "brandId is required");
        this.startDate = Objects.requireNonNull(startDate, "startDate is required");
        this.endDate = Objects.requireNonNull(endDate, "endDate is required");
        this.priceList = Objects.requireNonNull(priceList, "priceList is required");
        this.productId = Objects.requireNonNull(productId, "productId is required");
        this.priority = Objects.requireNonNull(priority, "priority is required");
        this.price = Objects.requireNonNull(price, "price is required");
        this.currency = Objects.requireNonNull(currency, "currency is required");

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate must be after startDate");
        }
    }
    
}
