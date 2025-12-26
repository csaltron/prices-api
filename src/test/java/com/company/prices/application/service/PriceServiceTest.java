package com.company.prices.application.service;

import com.company.prices.application.service.PriceService;
import com.company.prices.domain.exception.PriceNotFoundException;
import com.company.prices.domain.model.Price;
import com.company.prices.domain.port.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {
    
    @Mock
    private PriceRepository priceRepository;
    private PriceService priceService;
    
    @BeforeEach
    void setUp() {
        priceService = new PriceService(priceRepository);
    }
    
    @Test
    @DisplayName("Should return price when found")
    void shouldReturnPriceWhenFound() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        Price expectedPrice = Price.builder()
                .id(1L)
                .brandId(brandId)
                .productId(productId)
                .priceList(1)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .currency("EUR")
                .build();
        
        when(priceRepository.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));
        
        Price result = priceService.findApplicablePrice(applicationDate, productId, brandId);
        
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getBrandId()).isEqualTo(brandId);
        assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(35.50));
        
        verify(priceRepository).findApplicablePrice(applicationDate, productId, brandId);
    }
    
    @Test
    @DisplayName("Should throw PriceNotFoundException when price not found")
    void shouldThrowExceptionWhenPriceNotFound() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 99999L;
        Long brandId = 1L;
        
        when(priceRepository.findApplicablePrice(any(), any(), any()))
                .thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> priceService.findApplicablePrice(applicationDate, productId, brandId))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("No price found");
        
        verify(priceRepository).findApplicablePrice(applicationDate, productId, brandId);
    }
}
