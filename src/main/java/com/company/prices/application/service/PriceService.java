package com.company.prices.application.service;
import java.time.LocalDateTime;

import com.company.prices.domain.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PriceService {

    public Price findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
