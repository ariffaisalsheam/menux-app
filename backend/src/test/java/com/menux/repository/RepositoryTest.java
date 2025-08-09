package com.menux.repository;

import com.menux.entity.User;
import com.menux.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserRepository() {
        // Create a test user
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPasswordHash("hashedPassword123"); // Add required password
        user.setRole(UserRole.RESTAURANT_OWNER);
        user.setIsActive(true);

        // Save the user
        User savedUser = userRepository.save(user);

        // Verify the user was saved
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");

        // Test finding by email
        var foundUser = userRepository.findByEmail("test@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getFirstName()).isEqualTo("Test");
    }
}
