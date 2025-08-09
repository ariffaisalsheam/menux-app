package com.menux.repository;

import com.menux.entity.User;
import com.menux.entity.UserRole;
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
 * Repository interface for User entity operations
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find user by email address
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by email and active status
     */
    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);

    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Find users by role
     */
    List<User> findByRole(UserRole role);

    /**
     * Find users by role with pagination
     */
    Page<User> findByRole(UserRole role, Pageable pageable);

    /**
     * Find active users by role
     */
    List<User> findByRoleAndIsActive(UserRole role, Boolean isActive);

    /**
     * Find users by name containing (case insensitive)
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<User> findByNameOrEmailContaining(@Param("name") String name, Pageable pageable);

    /**
     * Find restaurant owners with their restaurants
     */
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.restaurants WHERE u.role = :role")
    List<User> findRestaurantOwnersWithRestaurants(@Param("role") UserRole role);

    /**
     * Count users by role
     */
    long countByRole(UserRole role);

    /**
     * Count active users
     */
    long countByIsActive(Boolean isActive);

    /**
     * Find users created in the last N days
     */
    @Query(value = "SELECT * FROM users WHERE created_at >= CURRENT_TIMESTAMP - INTERVAL '?1 days'", nativeQuery = true)
    List<User> findUsersCreatedInLastDays(int days);
}
