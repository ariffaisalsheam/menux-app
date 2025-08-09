package com.menux.repository;

import com.menux.entity.MenuCategory;
import com.menux.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for MenuCategory entity operations
 */
@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, UUID> {

    /**
     * Find menu categories by restaurant
     */
    List<MenuCategory> findByRestaurant(Restaurant restaurant);

    /**
     * Find menu categories by restaurant ID
     */
    List<MenuCategory> findByRestaurantId(UUID restaurantId);

    /**
     * Find active menu categories by restaurant
     */
    List<MenuCategory> findByRestaurantAndIsActive(Restaurant restaurant, Boolean isActive);

    /**
     * Find menu categories by restaurant ordered by display order
     */
    List<MenuCategory> findByRestaurantOrderByDisplayOrder(Restaurant restaurant);

    /**
     * Find active menu categories by restaurant ordered by display order
     */
    List<MenuCategory> findByRestaurantAndIsActiveOrderByDisplayOrder(Restaurant restaurant, Boolean isActive);

    /**
     * Find menu categories by restaurant with pagination
     */
    Page<MenuCategory> findByRestaurant(Restaurant restaurant, Pageable pageable);

    /**
     * Find menu category by restaurant and name
     */
    Optional<MenuCategory> findByRestaurantAndName(Restaurant restaurant, String name);

    /**
     * Check if menu category exists by restaurant and name
     */
    boolean existsByRestaurantAndName(Restaurant restaurant, String name);

    /**
     * Find menu categories with menu items by restaurant
     */
    @Query("SELECT DISTINCT c FROM MenuCategory c LEFT JOIN FETCH c.menuItems WHERE c.restaurant = :restaurant AND c.isActive = true ORDER BY c.displayOrder")
    List<MenuCategory> findByRestaurantWithMenuItems(@Param("restaurant") Restaurant restaurant);

    /**
     * Find menu categories with available menu items by restaurant
     */
    @Query("SELECT DISTINCT c FROM MenuCategory c LEFT JOIN FETCH c.menuItems m WHERE c.restaurant = :restaurant AND c.isActive = true AND (m IS NULL OR m.isAvailable = true) ORDER BY c.displayOrder")
    List<MenuCategory> findByRestaurantWithAvailableMenuItems(@Param("restaurant") Restaurant restaurant);

    /**
     * Count menu categories by restaurant
     */
    long countByRestaurant(Restaurant restaurant);

    /**
     * Count active menu categories by restaurant
     */
    long countByRestaurantAndIsActive(Restaurant restaurant, Boolean isActive);

    /**
     * Search menu categories by name
     */
    @Query("SELECT c FROM MenuCategory c WHERE c.restaurant = :restaurant AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MenuCategory> searchByRestaurantAndName(@Param("restaurant") Restaurant restaurant, @Param("name") String name);

    /**
     * Find menu categories by display order range
     */
    @Query("SELECT c FROM MenuCategory c WHERE c.restaurant = :restaurant AND c.displayOrder BETWEEN :minOrder AND :maxOrder ORDER BY c.displayOrder")
    List<MenuCategory> findByRestaurantAndDisplayOrderBetween(@Param("restaurant") Restaurant restaurant, 
                                                              @Param("minOrder") Integer minOrder, 
                                                              @Param("maxOrder") Integer maxOrder);

    /**
     * Get next display order for restaurant
     */
    @Query("SELECT COALESCE(MAX(c.displayOrder), 0) + 1 FROM MenuCategory c WHERE c.restaurant = :restaurant")
    Integer getNextDisplayOrder(@Param("restaurant") Restaurant restaurant);
}
