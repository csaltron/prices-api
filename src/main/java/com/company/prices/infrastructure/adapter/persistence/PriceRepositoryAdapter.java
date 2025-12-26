package com.company.prices.infrastructure.adapter.persistence;


import com.company.prices.domain.model.Price;
import com.company.prices.domain.port.PriceRepository;
import com.company.prices.infrastructure.adapter.persistence.entity.PriceEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PriceRepositoryAdapter implements PriceRepository {
    
    private final JpaPriceRepository jpaPriceRepository;
    
    public PriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }
    
    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return jpaPriceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .map(this::toDomain);
    }
    
    private Price toDomain(PriceEntity entity) {
        return Price.builder()
                .id(entity.getId())
                .brandId(entity.getBrandId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .productId(entity.getProductId())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }
}
