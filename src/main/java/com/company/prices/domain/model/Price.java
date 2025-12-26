package com.company.prices.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Price {
    
    private Long id;
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;
    
}
