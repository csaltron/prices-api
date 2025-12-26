package com.company.prices.infrastructure.adapter.persistence;

import com.company.prices.infrastructure.adapter.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {
    
    @Query("SELECT p FROM PriceEntity p " +
           "WHERE p.productId = :productId " +
           "AND p.brandId = :brandId " +
           "AND :applicationDate BETWEEN p.startDate AND p.endDate " +
           "ORDER BY p.priority DESC " +
           "LIMIT 1")
    Optional<PriceEntity> findApplicablePrice(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );
}
