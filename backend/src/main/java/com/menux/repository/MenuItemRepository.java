package com.menux.repository;

import com.menux.entity.MenuItem;
import com.menux.entity.MenuCategory;
import com.menux.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for MenuItem entity operations
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    /**
     * Find menu items by restaurant
     */
    List<MenuItem> findByRestaurant(Restaurant restaurant);

    /**
     * Find available menu items by restaurant
     */
    List<MenuItem> findByRestaurantAndIsAvailable(Restaurant restaurant, Boolean isAvailable);

    /**
     * Find menu items by restaurant ID
     */
    List<MenuItem> findByRestaurantId(UUID restaurantId);

    /**
     * Find available menu items by restaurant ID ordered by display order
     */
    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId AND m.isAvailable = true ORDER BY m.displayOrder, m.name")
    List<MenuItem> findAvailableByRestaurantIdOrderByDisplayOrder(@Param("restaurantId") UUID restaurantId);

    /**
     * Find menu items by category
     */
    List<MenuItem> findByCategory(MenuCategory category);

    /**
     * Find available menu items by category ordered by display order
     */
    List<MenuItem> findByCategoryAndIsAvailableOrderByDisplayOrder(MenuCategory category, Boolean isAvailable);

    /**
     * Find menu items by restaurant and category
     */
    List<MenuItem> findByRestaurantAndCategory(Restaurant restaurant, MenuCategory category);

    /**
     * Find vegetarian menu items by restaurant
     */
    List<MenuItem> findByRestaurantAndIsVegetarian(Restaurant restaurant, Boolean isVegetarian);

    /**
     * Find vegan menu items by restaurant
     */
    List<MenuItem> findByRestaurantAndIsVegan(Restaurant restaurant, Boolean isVegan);

    /**
     * Find gluten-free menu items by restaurant
     */
    List<MenuItem> findByRestaurantAndIsGlutenFree(Restaurant restaurant, Boolean isGlutenFree);

    /**
     * Find menu items by price range
     */
    @Query("SELECT m FROM MenuItem m WHERE m.restaurant = :restaurant AND m.price BETWEEN :minPrice AND :maxPrice AND m.isAvailable = true")
    List<MenuItem> findByRestaurantAndPriceBetween(@Param("restaurant") Restaurant restaurant, 
                                                   @Param("minPrice") BigDecimal minPrice, 
                                                   @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Search menu items by name
     */
    @Query("SELECT m FROM MenuItem m WHERE m.restaurant = :restaurant AND LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')) AND m.isAvailable = true")
    List<MenuItem> searchByRestaurantAndName(@Param("restaurant") Restaurant restaurant, @Param("name") String name);

    /**
     * Find menu items with categories by restaurant
     */
    @Query("SELECT DISTINCT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE m.restaurant.id = :restaurantId AND m.isAvailable = true ORDER BY m.category.displayOrder, m.displayOrder")
    List<MenuItem> findByRestaurantIdWithCategoriesOrderByDisplayOrder(@Param("restaurantId") UUID restaurantId);

    /**
     * Count menu items by restaurant
     */
    long countByRestaurant(Restaurant restaurant);

    /**
     * Count available menu items by restaurant
     */
    long countByRestaurantAndIsAvailable(Restaurant restaurant, Boolean isAvailable);

    /**
     * Find popular menu items (most ordered)
     */
    @Query("SELECT m FROM MenuItem m JOIN m.orderItems oi GROUP BY m ORDER BY COUNT(oi) DESC")
    Page<MenuItem> findPopularItems(Pageable pageable);

    /**
     * Find popular menu items by restaurant
     */
    @Query("SELECT m FROM MenuItem m JOIN m.orderItems oi WHERE m.restaurant = :restaurant GROUP BY m ORDER BY COUNT(oi) DESC")
    List<MenuItem> findPopularItemsByRestaurant(@Param("restaurant") Restaurant restaurant, Pageable pageable);
}
