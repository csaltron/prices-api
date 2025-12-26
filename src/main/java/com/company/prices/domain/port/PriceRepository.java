package com.company.prices.domain.port;

import com.company.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    
    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
