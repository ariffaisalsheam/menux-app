package com.menux.service;

import com.menux.entity.Restaurant;
import com.menux.entity.SubscriptionType;
import com.menux.entity.User;
import com.menux.exception.ResourceNotFoundException;
import com.menux.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for Restaurant entity operations
 */
@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final QrCodeService qrCodeService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, QrCodeService qrCodeService) {
        this.restaurantRepository = restaurantRepository;
        this.qrCodeService = qrCodeService;
    }

    /**
     * Create a new restaurant
     */
    public Restaurant createRestaurant(Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        
        // Generate default QR code for the restaurant
        qrCodeService.generateQrCodeForRestaurant(savedRestaurant, "Main");
        
        return savedRestaurant;
    }

    /**
     * Get restaurant by ID
     */
    @Transactional(readOnly = true)
    public Restaurant getRestaurantById(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    /**
     * Get restaurant with menu items
     */
    @Transactional(readOnly = true)
    public Restaurant getRestaurantWithMenuItems(UUID id) {
        return restaurantRepository.findByIdWithMenuItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    /**
     * Get restaurant with categories and menu items
     */
    @Transactional(readOnly = true)
    public Restaurant getRestaurantWithCategoriesAndItems(UUID id) {
        return restaurantRepository.findByIdWithCategoriesAndItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    /**
     * Update restaurant
     */
    public Restaurant updateRestaurant(UUID id, Restaurant restaurantDetails) {
        Restaurant restaurant = getRestaurantById(id);

        restaurant.setName(restaurantDetails.getName());
        restaurant.setDescription(restaurantDetails.getDescription());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setPhone(restaurantDetails.getPhone());
        restaurant.setEmail(restaurantDetails.getEmail());

        return restaurantRepository.save(restaurant);
    }

    /**
     * Update restaurant subscription
     */
    public Restaurant updateSubscription(UUID id, SubscriptionType subscriptionType) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setSubscriptionType(subscriptionType);
        return restaurantRepository.save(restaurant);
    }

    /**
     * Activate/Deactivate restaurant
     */
    public Restaurant toggleRestaurantStatus(UUID id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setIsActive(!restaurant.getIsActive());
        return restaurantRepository.save(restaurant);
    }

    /**
     * Delete restaurant
     */
    public void deleteRestaurant(UUID id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    /**
     * Get restaurants by owner
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurantsByOwner(User owner) {
        return restaurantRepository.findByOwner(owner);
    }

    /**
     * Get restaurants by owner ID
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurantsByOwnerId(UUID ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    /**
     * Get active restaurants by owner
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getActiveRestaurantsByOwner(User owner) {
        return restaurantRepository.findByOwnerAndIsActive(owner, true);
    }

    /**
     * Get all restaurants with pagination
     */
    @Transactional(readOnly = true)
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    /**
     * Get restaurants by subscription type
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurantsBySubscription(SubscriptionType subscriptionType) {
        return restaurantRepository.findBySubscriptionType(subscriptionType);
    }

    /**
     * Get restaurants by subscription type with pagination
     */
    @Transactional(readOnly = true)
    public Page<Restaurant> getRestaurantsBySubscription(SubscriptionType subscriptionType, Pageable pageable) {
        return restaurantRepository.findBySubscriptionType(subscriptionType, pageable);
    }

    /**
     * Search restaurants by name
     */
    @Transactional(readOnly = true)
    public Page<Restaurant> searchRestaurantsByName(String name, Pageable pageable) {
        return restaurantRepository.findByNameContaining(name, pageable);
    }

    /**
     * Search restaurants by name or address
     */
    @Transactional(readOnly = true)
    public Page<Restaurant> searchRestaurants(String searchTerm, Pageable pageable) {
        return restaurantRepository.searchRestaurants(searchTerm, pageable);
    }

    /**
     * Get Pro restaurants
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getProRestaurants() {
        return restaurantRepository.findProRestaurants();
    }

    /**
     * Get restaurant statistics
     */
    @Transactional(readOnly = true)
    public RestaurantStatistics getRestaurantStatistics() {
        long totalRestaurants = restaurantRepository.count();
        long freeRestaurants = restaurantRepository.countBySubscriptionType(SubscriptionType.FREE);
        long proRestaurants = restaurantRepository.countBySubscriptionType(SubscriptionType.PRO);
        long activeRestaurants = restaurantRepository.countByIsActive(true);

        return new RestaurantStatistics(totalRestaurants, freeRestaurants, proRestaurants, activeRestaurants);
    }

    /**
     * Check if restaurant belongs to owner
     */
    @Transactional(readOnly = true)
    public boolean isRestaurantOwnedBy(UUID restaurantId, UUID ownerId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        return restaurant.getOwner().getId().equals(ownerId);
    }

    /**
     * Inner class for restaurant statistics
     */
    public static class RestaurantStatistics {
        private final long totalRestaurants;
        private final long freeRestaurants;
        private final long proRestaurants;
        private final long activeRestaurants;

        public RestaurantStatistics(long totalRestaurants, long freeRestaurants, long proRestaurants, long activeRestaurants) {
            this.totalRestaurants = totalRestaurants;
            this.freeRestaurants = freeRestaurants;
            this.proRestaurants = proRestaurants;
            this.activeRestaurants = activeRestaurants;
        }

        // Getters
        public long getTotalRestaurants() { return totalRestaurants; }
        public long getFreeRestaurants() { return freeRestaurants; }
        public long getProRestaurants() { return proRestaurants; }
        public long getActiveRestaurants() { return activeRestaurants; }
    }
}
