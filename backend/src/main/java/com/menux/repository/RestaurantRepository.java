package com.menux.repository;

import com.menux.entity.Restaurant;
import com.menux.entity.SubscriptionType;
import com.menux.entity.User;
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
 * Repository interface for Restaurant entity operations
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    /**
     * Find restaurants by owner
     */
    List<Restaurant> findByOwner(User owner);

    /**
     * Find restaurants by owner ID
     */
    List<Restaurant> findByOwnerId(UUID ownerId);

    /**
     * Find active restaurants by owner
     */
    List<Restaurant> findByOwnerAndIsActive(User owner, Boolean isActive);

    /**
     * Find restaurants by subscription type
     */
    List<Restaurant> findBySubscriptionType(SubscriptionType subscriptionType);

    /**
     * Find restaurants by subscription type with pagination
     */
    Page<Restaurant> findBySubscriptionType(SubscriptionType subscriptionType, Pageable pageable);

    /**
     * Find active restaurants
     */
    List<Restaurant> findByIsActive(Boolean isActive);

    /**
     * Find restaurants by name containing (case insensitive)
     */
    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Restaurant> findByNameContaining(@Param("name") String name, Pageable pageable);

    /**
     * Find restaurants by location containing (case insensitive)
     */
    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.address) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Restaurant> findByLocationContaining(@Param("location") String location);

    /**
     * Find restaurant with menu items
     */
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menuItems WHERE r.id = :id")
    Optional<Restaurant> findByIdWithMenuItems(@Param("id") UUID id);

    /**
     * Find restaurant with categories and menu items
     */
    @Query("SELECT DISTINCT r FROM Restaurant r " +
           "LEFT JOIN FETCH r.menuCategories c " +
           "LEFT JOIN FETCH c.menuItems " +
           "WHERE r.id = :id")
    Optional<Restaurant> findByIdWithCategoriesAndItems(@Param("id") UUID id);

    /**
     * Count restaurants by subscription type
     */
    long countBySubscriptionType(SubscriptionType subscriptionType);

    /**
     * Count active restaurants
     */
    long countByIsActive(Boolean isActive);

    /**
     * Find Pro restaurants
     */
    @Query("SELECT r FROM Restaurant r WHERE r.subscriptionType = 'PRO' AND r.isActive = true")
    List<Restaurant> findProRestaurants();

    /**
     * Search restaurants by name or address
     */
    @Query("SELECT r FROM Restaurant r WHERE " +
           "LOWER(r.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(r.address) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Restaurant> searchRestaurants(@Param("searchTerm") String searchTerm, Pageable pageable);
}
