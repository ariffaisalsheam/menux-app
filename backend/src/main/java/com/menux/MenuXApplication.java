package com.menux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for Menu.X Digital Restaurant Communication System
 * 
 * This application provides a comprehensive platform for:
 * - QR code-based menu access
 * - Digital ordering system
 * - Restaurant management
 * - Customer feedback collection
 * - AI-powered analytics
 * 
 * @author Menu.X Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableTransactionManagement
public class MenuXApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenuXApplication.class, args);
    }
}
