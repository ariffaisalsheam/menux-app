package com.menux.service;

import com.menux.entity.User;
import com.menux.entity.UserRole;
import com.menux.exception.ResourceNotFoundException;
import com.menux.exception.DuplicateResourceException;
import com.menux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for User entity operations
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a new user
     */
    public User createUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("User with email " + user.getEmail() + " already exists");
        }

        // Encode password
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        
        return userRepository.save(user);
    }

    /**
     * Get user by ID
     */
    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    /**
     * Get user by email
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get active user by email
     */
    @Transactional(readOnly = true)
    public Optional<User> getActiveUserByEmail(String email) {
        return userRepository.findByEmailAndIsActive(email, true);
    }

    /**
     * Update user
     */
    public User updateUser(UUID id, User userDetails) {
        User user = getUserById(id);

        // Update fields
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhone(userDetails.getPhone());

        // Check if email is being changed and if it's unique
        if (!user.getEmail().equals(userDetails.getEmail())) {
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new DuplicateResourceException("User with email " + userDetails.getEmail() + " already exists");
            }
            user.setEmail(userDetails.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * Update user password
     */
    public void updatePassword(UUID id, String newPassword) {
        User user = getUserById(id);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Activate/Deactivate user
     */
    public User toggleUserStatus(UUID id) {
        User user = getUserById(id);
        user.setIsActive(!user.getIsActive());
        return userRepository.save(user);
    }

    /**
     * Delete user
     */
    public void deleteUser(UUID id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    /**
     * Get all users with pagination
     */
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Get users by role
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    /**
     * Get users by role with pagination
     */
    @Transactional(readOnly = true)
    public Page<User> getUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    /**
     * Search users by name or email
     */
    @Transactional(readOnly = true)
    public Page<User> searchUsers(String searchTerm, Pageable pageable) {
        return userRepository.findByNameOrEmailContaining(searchTerm, pageable);
    }

    /**
     * Get restaurant owners with their restaurants
     */
    @Transactional(readOnly = true)
    public List<User> getRestaurantOwnersWithRestaurants() {
        return userRepository.findRestaurantOwnersWithRestaurants(UserRole.RESTAURANT_OWNER);
    }

    /**
     * Get user statistics
     */
    @Transactional(readOnly = true)
    public UserStatistics getUserStatistics() {
        long totalUsers = userRepository.count();
        long superAdmins = userRepository.countByRole(UserRole.SUPER_ADMIN);
        long restaurantOwners = userRepository.countByRole(UserRole.RESTAURANT_OWNER);
        long activeUsers = userRepository.countByIsActive(true);

        return new UserStatistics(totalUsers, superAdmins, restaurantOwners, activeUsers);
    }

    /**
     * Check if user exists by email
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Verify password
     */
    @Transactional(readOnly = true)
    public boolean verifyPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }

    /**
     * Inner class for user statistics
     */
    public static class UserStatistics {
        private final long totalUsers;
        private final long superAdmins;
        private final long restaurantOwners;
        private final long activeUsers;

        public UserStatistics(long totalUsers, long superAdmins, long restaurantOwners, long activeUsers) {
            this.totalUsers = totalUsers;
            this.superAdmins = superAdmins;
            this.restaurantOwners = restaurantOwners;
            this.activeUsers = activeUsers;
        }

        // Getters
        public long getTotalUsers() { return totalUsers; }
        public long getSuperAdmins() { return superAdmins; }
        public long getRestaurantOwners() { return restaurantOwners; }
        public long getActiveUsers() { return activeUsers; }
    }
}
