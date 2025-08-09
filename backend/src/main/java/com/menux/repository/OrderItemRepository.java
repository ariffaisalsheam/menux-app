package com.menux.repository;

import com.menux.entity.MenuItem;
import com.menux.entity.Order;
import com.menux.entity.OrderItem;
import com.menux.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for OrderItem entity operations
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    /**
     * Find order items by order
     */
    List<OrderItem> findByOrder(Order order);

    /**
     * Find order items by order ID
     */
    List<OrderItem> findByOrderId(UUID orderId);

    /**
     * Find order items by menu item
     */
    List<OrderItem> findByMenuItem(MenuItem menuItem);

    /**
     * Find order items by menu item ID
     */
    List<OrderItem> findByMenuItemId(UUID menuItemId);

    /**
     * Find order items by order with pagination
     */
    Page<OrderItem> findByOrder(Order order, Pageable pageable);

    /**
     * Calculate total quantity by menu item
     */
    @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderItem oi WHERE oi.menuItem = :menuItem")
    Long calculateTotalQuantityByMenuItem(@Param("menuItem") MenuItem menuItem);

    /**
     * Calculate total revenue by menu item
     */
    @Query("SELECT COALESCE(SUM(oi.quantity * oi.unitPrice), 0) FROM OrderItem oi WHERE oi.menuItem = :menuItem")
    BigDecimal calculateTotalRevenueByMenuItem(@Param("menuItem") MenuItem menuItem);

    /**
     * Find order items by restaurant and date range
     */
    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE o.restaurant = :restaurant AND o.createdAt BETWEEN :startDate AND :endDate")
    List<OrderItem> findByRestaurantAndDateRange(@Param("restaurant") Restaurant restaurant, 
                                                 @Param("startDate") LocalDateTime startDate, 
                                                 @Param("endDate") LocalDateTime endDate);

    /**
     * Find most popular menu items by restaurant
     */
    @Query("SELECT oi.menuItem, SUM(oi.quantity) as totalQuantity FROM OrderItem oi JOIN oi.order o WHERE o.restaurant = :restaurant GROUP BY oi.menuItem ORDER BY totalQuantity DESC")
    List<Object[]> findMostPopularItemsByRestaurant(@Param("restaurant") Restaurant restaurant);

    /**
     * Find most popular menu items by restaurant with pagination
     */
    @Query("SELECT oi.menuItem, SUM(oi.quantity) as totalQuantity FROM OrderItem oi JOIN oi.order o WHERE o.restaurant = :restaurant GROUP BY oi.menuItem ORDER BY totalQuantity DESC")
    Page<Object[]> findMostPopularItemsByRestaurant(@Param("restaurant") Restaurant restaurant, Pageable pageable);

    /**
     * Calculate total revenue by restaurant and date range
     */
    @Query("SELECT COALESCE(SUM(oi.quantity * oi.unitPrice), 0) FROM OrderItem oi JOIN oi.order o WHERE o.restaurant = :restaurant AND o.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal calculateRevenueByRestaurantAndDateRange(@Param("restaurant") Restaurant restaurant, 
                                                        @Param("startDate") LocalDateTime startDate, 
                                                        @Param("endDate") LocalDateTime endDate);

    /**
     * Count order items by restaurant
     */
    @Query("SELECT COUNT(oi) FROM OrderItem oi JOIN oi.order o WHERE o.restaurant = :restaurant")
    long countByRestaurant(@Param("restaurant") Restaurant restaurant);

    /**
     * Find order items with special instructions
     */
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order = :order AND oi.specialInstructions IS NOT NULL AND oi.specialInstructions != ''")
    List<OrderItem> findByOrderWithSpecialInstructions(@Param("order") Order order);

    /**
     * Find order items by quantity range
     */
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order = :order AND oi.quantity BETWEEN :minQuantity AND :maxQuantity")
    List<OrderItem> findByOrderAndQuantityBetween(@Param("order") Order order, 
                                                  @Param("minQuantity") Integer minQuantity, 
                                                  @Param("maxQuantity") Integer maxQuantity);

    /**
     * Calculate average order item value by restaurant
     */
    @Query("SELECT AVG(oi.quantity * oi.unitPrice) FROM OrderItem oi JOIN oi.order o WHERE o.restaurant = :restaurant")
    Double calculateAverageOrderItemValueByRestaurant(@Param("restaurant") Restaurant restaurant);
}
