package com.menux.repository;

import com.menux.entity.QrCode;
import com.menux.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for QrCode entity operations
 */
@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    /**
     * Find QR code by code string
     */
    Optional<QrCode> findByCode(String code);

    /**
     * Check if QR code exists by code string
     */
    boolean existsByCode(String code);

    /**
     * Find QR codes by restaurant
     */
    List<QrCode> findByRestaurant(Restaurant restaurant);

    /**
     * Find QR codes by restaurant and active status
     */
    List<QrCode> findByRestaurantAndIsActive(Restaurant restaurant, Boolean isActive);

    /**
     * Find QR codes by restaurant and table number
     */
    List<QrCode> findByRestaurantAndTableNumber(Restaurant restaurant, String tableNumber);

    /**
     * Find active QR codes by restaurant and table number
     */
    Optional<QrCode> findByRestaurantAndTableNumberAndIsActive(Restaurant restaurant, String tableNumber, Boolean isActive);

    /**
     * Count QR codes by restaurant
     */
    long countByRestaurant(Restaurant restaurant);

    /**
     * Count active QR codes by restaurant
     */
    long countByRestaurantAndIsActive(Restaurant restaurant, Boolean isActive);
}
