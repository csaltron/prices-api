package com.company.prices.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PriceTest {


    @Test
    @DisplayName("Test Price Object Creation")
    void testPriceCreation() {
        Price price = Price.builder()
                .id(1L)
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .priority(0)
                .price(java.math.BigDecimal.valueOf(35.50))
                .startDate(java.time.LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(java.time.LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .currency("EUR")
                .build();

        assertNotNull(price);
        assertEquals(1L, price.getId());
        assertEquals(1L, price.getBrandId());
        assertEquals(35455L, price.getProductId());
        assertEquals(1, price.getPriceList());
        assertEquals(0, price.getPriority());
        assertEquals(java.math.BigDecimal.valueOf(35.50), price.getPrice());
        assertEquals(java.time.LocalDateTime.of(2020, 6, 14, 0, 0), price.getStartDate());
        assertEquals(java.time.LocalDateTime.of(2020, 12, 31, 23, 59, 59), price.getEndDate());
        assertEquals("EUR", price.getCurrency());
    }
}