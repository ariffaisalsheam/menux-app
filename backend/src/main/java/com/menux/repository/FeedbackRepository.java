package com.menux.repository;

import com.menux.entity.Feedback;
import com.menux.entity.Restaurant;
import com.menux.entity.SentimentType;
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
 * Repository interface for Feedback entity operations
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    /**
     * Find feedback by restaurant
     */
    List<Feedback> findByRestaurant(Restaurant restaurant);

    /**
     * Find feedback by restaurant with pagination
     */
    Page<Feedback> findByRestaurant(Restaurant restaurant, Pageable pageable);

    /**
     * Find feedback by restaurant ID
     */
    List<Feedback> findByRestaurantId(UUID restaurantId);

    /**
     * Find feedback by restaurant and rating
     */
    List<Feedback> findByRestaurantAndRating(Restaurant restaurant, Integer rating);

    /**
     * Find feedback by restaurant and sentiment
     */
    List<Feedback> findByRestaurantAndSentiment(Restaurant restaurant, SentimentType sentiment);

    /**
     * Find feedback by rating range
     */
    @Query("SELECT f FROM Feedback f WHERE f.restaurant = :restaurant AND f.rating BETWEEN :minRating AND :maxRating ORDER BY f.createdAt DESC")
    List<Feedback> findByRestaurantAndRatingBetween(@Param("restaurant") Restaurant restaurant, 
                                                    @Param("minRating") Integer minRating, 
                                                    @Param("maxRating") Integer maxRating);

    /**
     * Find feedback created between dates
     */
    @Query("SELECT f FROM Feedback f WHERE f.restaurant = :restaurant AND f.createdAt BETWEEN :startDate AND :endDate ORDER BY f.createdAt DESC")
    List<Feedback> findByRestaurantAndCreatedAtBetween(@Param("restaurant") Restaurant restaurant, 
                                                       @Param("startDate") LocalDateTime startDate, 
                                                       @Param("endDate") LocalDateTime endDate);

    /**
     * Calculate average rating by restaurant
     */
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.restaurant = :restaurant")
    Double calculateAverageRatingByRestaurant(@Param("restaurant") Restaurant restaurant);

    /**
     * Count feedback by restaurant and rating
     */
    long countByRestaurantAndRating(Restaurant restaurant, Integer rating);

    /**
     * Count feedback by restaurant and sentiment
     */
    long countByRestaurantAndSentiment(Restaurant restaurant, SentimentType sentiment);

    /**
     * Find recent feedback by restaurant
     */
    @Query("SELECT f FROM Feedback f WHERE f.restaurant = :restaurant ORDER BY f.createdAt DESC")
    Page<Feedback> findRecentFeedbackByRestaurant(@Param("restaurant") Restaurant restaurant, Pageable pageable);

    /**
     * Find feedback with comments by restaurant
     */
    @Query("SELECT f FROM Feedback f WHERE f.restaurant = :restaurant AND f.comment IS NOT NULL AND f.comment != '' ORDER BY f.createdAt DESC")
    List<Feedback> findByRestaurantWithComments(@Param("restaurant") Restaurant restaurant);

    /**
     * Find anonymous feedback by restaurant
     */
    List<Feedback> findByRestaurantAndIsAnonymous(Restaurant restaurant, Boolean isAnonymous);

    /**
     * Search feedback by comment content
     */
    @Query("SELECT f FROM Feedback f WHERE f.restaurant = :restaurant AND LOWER(f.comment) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Feedback> searchByRestaurantAndComment(@Param("restaurant") Restaurant restaurant, @Param("searchTerm") String searchTerm);
}
