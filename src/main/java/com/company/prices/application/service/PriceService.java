package com.company.prices.application.service;

import com.company.prices.domain.exception.PriceNotFoundException;
import com.company.prices.domain.model.Price;
import com.company.prices.domain.port.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PriceService {
    
    private final PriceRepository priceRepository;
    
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    
    public Price findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        log.info("Searching price for productId: {}, brandId: {} at {}", productId, brandId, applicationDate);
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("No price found for product %d, brand %d at %s",
                                productId, brandId, applicationDate)
                ));
    }
}
