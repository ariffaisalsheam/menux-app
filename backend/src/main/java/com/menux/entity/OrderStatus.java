package com.menux.entity;

/**
 * Enum representing different order statuses in the system
 */
public enum OrderStatus {
    /**
     * Order has been placed but not yet confirmed by restaurant
     */
    PENDING("Pending"),
    
    /**
     * Order has been confirmed by restaurant
     */
    CONFIRMED("Confirmed"),
    
    /**
     * Order is being prepared
     */
    PREPARING("Preparing"),
    
    /**
     * Order is ready for pickup/serving
     */
    READY("Ready"),
    
    /**
     * Order has been completed/served
     */
    COMPLETED("Completed"),
    
    /**
     * Order has been cancelled
     */
    CANCELLED("Cancelled");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
