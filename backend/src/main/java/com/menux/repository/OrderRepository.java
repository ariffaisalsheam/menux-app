package com.menux.repository;

import com.menux.entity.Order;
import com.menux.entity.OrderStatus;
import com.menux.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Order entity operations
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    /**
     * Find orders by restaurant
     */
    List<Order> findByRestaurant(Restaurant restaurant);

    /**
     * Find orders by restaurant with pagination
     */
    Page<Order> findByRestaurant(Restaurant restaurant, Pageable pageable);

    /**
     * Find orders by restaurant ID
     */
    List<Order> findByRestaurantId(UUID restaurantId);

    /**
     * Find orders by restaurant and status
     */
    List<Order> findByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);

    /**
     * Find orders by restaurant and status with pagination
     */
    Page<Order> findByRestaurantAndStatus(Restaurant restaurant, OrderStatus status, Pageable pageable);

    /**
     * Find orders by status
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Find orders by table number
     */
    List<Order> findByRestaurantAndTableNumber(Restaurant restaurant, String tableNumber);

    /**
     * Find orders by customer phone
     */
    List<Order> findByRestaurantAndCustomerPhone(Restaurant restaurant, String customerPhone);

    /**
     * Find orders created between dates
     */
    @Query("SELECT o FROM Order o WHERE o.restaurant = :restaurant AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByRestaurantAndCreatedAtBetween(@Param("restaurant") Restaurant restaurant, 
                                                    @Param("startDate") LocalDateTime startDate, 
                                                    @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders with order items
     */
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :id")
    Order findByIdWithOrderItems(@Param("id") UUID id);

    /**
     * Find orders with order items by restaurant
     */
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.restaurant = :restaurant ORDER BY o.createdAt DESC")
    List<Order> findByRestaurantWithOrderItems(@Param("restaurant") Restaurant restaurant);

    /**
     * Find pending orders by restaurant
     */
    @Query("SELECT o FROM Order o WHERE o.restaurant = :restaurant AND (o.status = 'PENDING' OR o.status = 'CONFIRMED' OR o.status = 'PREPARING') ORDER BY o.createdAt")
    List<Order> findPendingOrdersByRestaurant(@Param("restaurant") Restaurant restaurant);

    /**
     * Find today's orders by restaurant
     */
    @Query("SELECT o FROM Order o WHERE o.restaurant = :restaurant AND o.createdAt >= CURRENT_DATE ORDER BY o.createdAt DESC")
    List<Order> findTodaysOrdersByRestaurant(@Param("restaurant") Restaurant restaurant);

    /**
     * Count orders by restaurant and status
     */
    long countByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);

    /**
     * Count orders by restaurant and date range
     */
    @Query("SELECT COUNT(o) FROM Order o WHERE o.restaurant = :restaurant AND o.createdAt BETWEEN :startDate AND :endDate")
    long countByRestaurantAndDateRange(@Param("restaurant") Restaurant restaurant, 
                                       @Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate);

    /**
     * Calculate total revenue by restaurant and date range
     */
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.restaurant = :restaurant AND o.status = 'COMPLETED' AND o.createdAt BETWEEN :startDate AND :endDate")
    Double calculateRevenueByRestaurantAndDateRange(@Param("restaurant") Restaurant restaurant,
                                                    @Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders by customer name
     */
    @Query("SELECT o FROM Order o WHERE o.restaurant = :restaurant AND LOWER(o.customerName) LIKE LOWER(CONCAT('%', :customerName, '%'))")
    List<Order> findByRestaurantAndCustomerNameContaining(@Param("restaurant") Restaurant restaurant, @Param("customerName") String customerName);
}
